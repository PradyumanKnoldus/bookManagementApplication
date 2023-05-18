package com.knoldus
package routes

import akka.actor.ActorSystem
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import model.Book
import service.BookServicesImplementation
import scala.concurrent.ExecutionContextExecutor
import scala.util.{Failure, Success}
import spray.json.DefaultJsonProtocol._
import spray.json.RootJsonFormat

class BookOperationsRoutes(bookServices: BookServicesImplementation)(implicit system: ActorSystem) {
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher

  // Define JSON formats for Book serialization/deserialization
  implicit val bookFormat: RootJsonFormat[Book] = jsonFormat4(Book)

  val routes: Route =
    pathPrefix("bookStore") {
      pathEndOrSingleSlash {
        post {
          entity(as[Book]) { bookData =>
            onComplete(bookServices.createBook(bookData.bookTitle, bookData.authorName, bookData.publishingYear)) {
              case Success(Right(createdBook)) =>
                complete(StatusCodes.Created, s"Book Added Successfully!\n\n${bookToString(createdBook)}")
              case Success(Left(message)) =>
                complete(StatusCodes.Conflict, message)
              case Failure(exception) =>
                complete(StatusCodes.InternalServerError, s"An error occurred: ${exception.getMessage}")
            }
          }
        } ~
          get {
            onComplete(bookServices.retrieveAllBooks) {
              case Success(Right(books)) => complete(StatusCodes.OK, s" All Books are - \n\n${books.map(book => bookToString(book)).mkString("\n")}")
              case Success(Left(message)) => complete(StatusCodes.NoContent, message)
              case Failure(exception) => complete(StatusCodes.InternalServerError, s"An error occurred: ${exception.getMessage}")
            }
          }
      } ~
        path(Segment) { id =>
          get {
            onComplete(bookServices.retrieveBook(id)) {
              case Success(Right(Some(book))) => complete(StatusCodes.OK, s" Retrieved Book! \n\n${bookToString(book)}")
              case Success(Left(message)) => complete(StatusCodes.NotFound, message)
              case Failure(exception) => complete(StatusCodes.InternalServerError, s"An error occurred: ${exception.getMessage}")
            }
          } ~
            put {
              entity(as[Book]) { bookData =>
                onComplete(bookServices.updateBook(id, bookData.bookTitle, bookData.authorName, bookData.publishingYear)) {
                  case Success(Right(Some(book))) => complete(StatusCodes.OK, s" Book Updated Successfully! \n\n${bookToString(book)}")
                  case Success(Left(message)) => complete(StatusCodes.NotFound, message)
                  case Failure(exception) => complete(StatusCodes.InternalServerError, s"An error occurred: ${exception.getMessage}")
                }
              }
            } ~
            delete {
              onComplete(bookServices.deleteBook(id)) {
                case Success(Right(Some(book))) => complete(StatusCodes.OK, s" Book Deleted Successfully! \n\n${bookToString(book)}")
                case Success(Left(message)) => complete(StatusCodes.NotFound, message)
                case Failure(exception) => complete(StatusCodes.InternalServerError, s"An error occurred: ${exception.getMessage}")
              }
            }
        }
    }

  private def bookToString(book: Book): String = {
    s" ID: ${book.id}\n Book Name: ${book.bookTitle}\n Author's Name: ${book.authorName}\n Publishing Year: ${book.publishingYear}\n\n"
  }
}

