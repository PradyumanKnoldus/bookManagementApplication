package com.knoldus
package service

import model.Book

import scala.concurrent.Future

trait BookServices {
  def createBook(bookTitle: String, authorName: String, publishingYear: Int): Future[Either[String, Book]]
  def retrieveBook(bookID: String): Future[Either[String, Option[Book]]]
  def updateBook(bookID: String, bookTitle: String, authorName: String, publishingYear: Int): Future[Either[String, Option[Book]]]
  def deleteBook(bookID: String): Future[Either[String, Option[Book]]]
  def retrieveAllBooks: Future[Either[String, List[Book]]]
}
