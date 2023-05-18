package com.knoldus
package service

import dao.BookManagementOperations
import model.Book
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import scala.concurrent.Future

class BookServicesImplementationSpec extends AnyFlatSpec with Matchers with ScalaFutures {

  val bookManagementOperationsMock: BookManagementOperations = new BookManagementOperationsMock()
  val bookServices: BookServicesImplementation = new BookServicesImplementation(bookManagementOperationsMock)

  it should "retrieve an existing book" in {
    val bookID = "1"
    val existingBook = Book(bookID, "Title", "Author", 2022)
    val expectedResult = Future.successful(Right(Some(existingBook)))

    val result = bookServices.retrieveBook(bookID)

    result.futureValue shouldBe expectedResult.futureValue
  }

  it should "update an existing book's details" in {
    val bookID = "1"
    val updatedBook = Book(bookID, "New Title", "New Author", 2023)
    val expectedResult = Future.successful(Right(Some(updatedBook)))

    val result = bookServices.updateBook(bookID, updatedBook.bookTitle, updatedBook.authorName, updatedBook.publishingYear)

    result.futureValue shouldBe expectedResult.futureValue
  }

  it should "delete an existing book" in {
    val bookID = "1"
    val deletedBook = Book(bookID, "Title", "Author", 2022)
    val expectedResult = Future.successful(Right(Some(deletedBook)))

    val result = bookServices.deleteBook(bookID)

    result.futureValue shouldBe expectedResult.futureValue
  }

  it should "retrieve all books" in {
    val book1 = Book("1", "Title 1", "Author 1", 2022)
    val book2 = Book("2", "Title 2", "Author 2", 2023)
    val expectedBooks = List(book1, book2)
    val expectedResult = Future.successful(Right(expectedBooks))

    val result = bookServices.retrieveAllBooks

    result.futureValue shouldBe expectedResult.futureValue
  }

  // Mock implementation of BookManagementOperations for testing purposes
  class BookManagementOperationsMock extends BookManagementOperations(null) {
    override def createBook(newBook: Book): Future[Either[String, Book]] = {
      Future.successful(Right(newBook))
    }

    override def retrieveBook(bookID: String): Future[Either[String, Option[Book]]] = {
      val existingBook = Book("1", "Title", "Author", 2022)
      Future.successful(Right(Some(existingBook)))
    }

    override def updateBook(updatedBook: Book): Future[Either[String, Option[Book]]] = {
      Future.successful(Right(Some(updatedBook)))
    }

    override def deleteBook(bookID: String): Future[Either[String, Option[Book]]] = {
      val deletedBook = Book("1", "Title", "Author", 2022)
      Future.successful(Right(Some(deletedBook)))
    }

    override def retrieveAllBooks: Future[Either[String, List[Book]]] = {
      val book1 = Book("1", "Title 1", "Author 1", 2022)
      val book2 = Book("2", "Title 2", "Author 2", 2023)
      Future.successful(Right(List(book1, book2)))
    }
  }

}
