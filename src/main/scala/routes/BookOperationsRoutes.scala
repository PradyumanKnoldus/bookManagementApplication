package com.knoldus
package routes

import akka.actor.ActorSystem
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import model.{Book, BookCreation}
import service.BookServicesImplementation

import scala.concurrent.ExecutionContextExecutor
import scala.util.{Failure, Success}
import spray.json.DefaultJsonProtocol._
import spray.json.RootJsonFormat

class BookOperationsRoutes(bookServices: BookServicesImplementation)(implicit system: ActorSystem) {
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher

  // Define JSON formats for Book serialization/deserialization
  implicit val bookFormat: RootJsonFormat[Book] = jsonFormat4(Book)
  implicit val bookCreationFormat: RootJsonFormat[BookCreation] = jsonFormat3(BookCreation)

  val routes: Route =
    pathPrefix("bookStore") {
      pathEndOrSingleSlash {
        post {
          entity(as[BookCreation]) { bookData =>
            onComplete(bookServices.createBook(bookData.bookTitle, bookData.authorName, bookData.publishingYear)) {
              case Success(Right(createdBook)) =>
                complete(StatusCodes.Created, createdBook)
              case Success(Left(message)) =>
                complete(StatusCodes.Conflict, message)
              case Failure(exception) =>
                complete(StatusCodes.InternalServerError, s"An error occurred: ${exception.getMessage}")
            }
          }
        } ~
          get {
            onComplete(bookServices.retrieveAllBooks) {
              case Success(Right(books)) => complete(StatusCodes.OK, books)
              case Success(Left(message)) => complete(StatusCodes.NoContent, message)
              case Failure(exception) => complete(StatusCodes.InternalServerError, s"An error occurred: ${exception.getMessage}")
            }
          }
      } ~
        path(Segment) { id =>
          get {
            onComplete(bookServices.retrieveBook(id)) {
              case Success(Right(Some(book))) => complete(StatusCodes.OK, book)
              case Success(Left(message)) => complete(StatusCodes.NotFound, message)
              case Failure(exception) => complete(StatusCodes.InternalServerError, s"An error occurred: ${exception.getMessage}")
            }
          } ~
            put {
              entity(as[Book]) { bookData =>
                onComplete(bookServices.updateBook(id, bookData.bookTitle, bookData.authorName, bookData.publishingYear)) {
                  case Success(Right(Some(book))) => complete(StatusCodes.OK, book)
                  case Success(Left(message)) => complete(StatusCodes.NotFound, message)
                  case Failure(exception) => complete(StatusCodes.InternalServerError, s"An error occurred: ${exception.getMessage}")
                }
              }
            } ~
            delete {
              onComplete(bookServices.deleteBook(id)) {
                case Success(Right(Some(book))) => complete(StatusCodes.OK, book)
                case Success(Left(message)) => complete(StatusCodes.NotFound, message)
                case Failure(exception) => complete(StatusCodes.InternalServerError, s"An error occurred: ${exception.getMessage}")
              }
            }
        }
    }
}

