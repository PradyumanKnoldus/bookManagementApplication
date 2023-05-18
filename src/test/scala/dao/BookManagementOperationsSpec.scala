package com.knoldus
package dao

import db.BookStore
import model.Book
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class BookManagementOperationsSpec extends AnyFlatSpec with Matchers with ScalaFutures {

  val bookStore: BookStore = new BookStore()
  val bookManagementOperations: BookManagementOperations = new BookManagementOperations(bookStore)

  "BookManagementOperations" should "create a new book" in {
    val newBook = Book("1", "Title", "Author", 2023)
    val result = bookManagementOperations.createBook(newBook).futureValue

    result shouldBe Right(newBook)
    bookStore.books should contain (newBook.id -> newBook)
  }

  it should "not create a book with the same ID" in {
    val existingBook = Book("1", "Title", "Author", 2023)
    bookStore.books += (existingBook.id -> existingBook)

    val newBook = Book("1", "New Title", "New Author", 2023)
    val result = bookManagementOperations.createBook(newBook).futureValue

    result shouldBe Left("Book with the same ID already exist!")
    bookStore.books should contain (existingBook.id -> existingBook)
  }

  it should "retrieve an existing book" in {
    val book = Book("1", "Title", "Author", 2023)
    bookStore.books += (book.id -> book)

    val result = bookManagementOperations.retrieveBook(book.id).futureValue

    result shouldBe Right(Some(book))
  }

  it should "return an error when retrieving a non-existing book" in {
    val bookID = "non-existing-id"

    val result = bookManagementOperations.retrieveBook(bookID).futureValue

    result shouldBe Left("Book you are trying to retrieve doesn't exist!")
  }

  it should "update an existing book's details" in {
    val book = Book("1", "Title", "Author", 2023)
    bookStore.books += (book.id -> book)
    val updatedBook = Book("1", "New Title", "New Author", 2023)

    val result = bookManagementOperations.updateBook(updatedBook).futureValue

    result shouldBe Right(Some(updatedBook))
    bookStore.books should contain (book.id -> updatedBook)
  }

  it should "delete an existing book" in {
    val book = Book("1", "Title", "Author", 2023)
    bookStore.books += (book.id -> book)

    val result = bookManagementOperations.deleteBook(book.id).futureValue

    result shouldBe Right(Some(book))
    bookStore.books should not contain key(book.id)
  }

  it should "return an error when deleting a non-existing book" in {
    val bookID = "non-existing-id"

    val result = bookManagementOperations.deleteBook(bookID).futureValue

    result shouldBe Left("Book you are trying to delete doesn't exist")
  }

  it should "return an error when no books are available" in {
    bookStore.books.clear()

    val result = bookManagementOperations.retrieveAllBooks.futureValue

    result shouldBe Left("No Books Available!")
  }
}
