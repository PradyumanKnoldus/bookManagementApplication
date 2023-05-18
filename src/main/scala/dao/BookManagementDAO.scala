package com.knoldus
package dao

import model.Book
import scala.concurrent.Future

trait BookManagementDAO {
  def createBook(newBook: Book): Future[Either[String, Book]]
  def retrieveBook(bookID: String): Future[Either[String, Option[Book]]]
  def updateBook(updatedBook: Book): Future[Either[String, Option[Book]]]
  def deleteBook(bookID: String): Future[Either[String, Option[Book]]]
  def retrieveAllBooks: Future[Either[String, List[Book]]]
}
