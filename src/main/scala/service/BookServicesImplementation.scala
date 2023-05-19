package com.knoldus
package service

import model.Book
import dao.{BookManagementDAO, BookManagementOperations}

import java.util.UUID
import scala.concurrent.Future

class BookServicesImplementation(bookManagementOperations: BookManagementDAO) extends BookServices {

  // Method to create a new Book
  override def createBook(bookTitle: String, authorName: String, publishingYear: Int): Future[Either[String, Book]] = {
    val uniqueID = UUID.randomUUID().toString
    val newBook = Book(uniqueID, bookTitle, authorName, publishingYear)
    bookManagementOperations.createBook(newBook)
  }

  // Method to retrieve an existing Book
  override def retrieveBook(bookID: String): Future[Either[String, Option[Book]]] = {
    bookManagementOperations.retrieveBook(bookID)
  }

  // Method to Update details of an existing Book
  override def updateBook(bookID: String, bookTitle: String, authorName: String, publishingYear: Int): Future[Either[String, Option[Book]]] = {
    val updatedBook = Book(bookID, bookTitle, authorName, publishingYear)
    bookManagementOperations.updateBook(updatedBook)
  }

  // Method to delete a Book
  override def deleteBook(bookID: String): Future[Either[String, Option[Book]]] = {
    bookManagementOperations.deleteBook(bookID)
  }

  // Method to retrieve all Books
  override def retrieveAllBooks: Future[Either[String, List[Book]]] = {
    bookManagementOperations.retrieveAllBooks
  }

}
