package com.knoldus
package dao

import model.Book
import db.BookStore
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import org.slf4j.{Logger, LoggerFactory}

class BookManagementOperations(bookStore: BookStore) extends BookManagementDAO {
  private val logger: Logger = LoggerFactory.getLogger(classOf[BookManagementOperations])
  // Method to create a new Book
  override def createBook(newBook: Book): Future[Either[String, Book]] = Future {
    if (bookStore.books.contains(newBook.id)) {
      Left("Book with the same ID already exist!")
    } else {
      bookStore.books += (newBook.id -> newBook)
      Right(newBook)
    }
  }.recoverWith {
    case exception: Exception => Future.successful(Left(s"Unexpected Exception Occurred! Unable to create book! ${exception.getMessage}"))
  }

  // Method to retrieve an existing book
  override def retrieveBook(bookID: String): Future[Either[String, Option[Book]]] = Future {
    if (bookStore.books.contains(bookID)) {
      Right(bookStore.books.get(bookID))
    } else {
      Left("Book you are trying to retrieve doesn't exist!")
    }
  }.recoverWith {
    case exception: Exception => Future.successful(Left(s"Unexpected Error Occurred! Unable to retrieve book! ${exception.getMessage}"))
  }

  // Method to Update a book's details
  override def updateBook(updatedBook: Book): Future[Either[String, Option[Book]]] = Future {
    if (bookStore.books.contains(updatedBook.id)) {
      bookStore.books.update(updatedBook.id, updatedBook)
      Right(bookStore.books.get(updatedBook.id))
    } else {
      Left("Book you are trying to update doesn't exist!")
    }
  }.recoverWith {
    case exception: Exception => Future.successful(Left(s"Unexpected Error Occurred! Unable to update book! ${exception.getMessage}"))
  }

  // Method to delete a book
  override def deleteBook(bookID: String): Future[Either[String, Option[Book]]] = Future {
    if (bookStore.books.contains(bookID)) {
      Right(bookStore.books.remove(bookID))
    } else {
      Left("Book you are trying to delete doesn't exist")
    }
  }.recoverWith {
    case exception: Exception => Future.successful(Left(s"Unexpected Error Occurred! Unable to delete book! ${exception.getMessage}"))
  }

  // Method to retrieve all books
  override def retrieveAllBooks: Future[Either[String, List[Book]]] = Future {
    if (bookStore.books.isEmpty) {
      Left("No Books Available!")
    } else {
      Right(bookStore.books.values.toList)
    }
  }.recoverWith {
    case exception: Exception => Future.successful(Left(s"Unexpected Error Occurred! Unable to retrieve books! ${exception.getMessage}"))
  }

}
