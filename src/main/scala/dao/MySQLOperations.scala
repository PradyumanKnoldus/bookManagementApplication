package com.knoldus
package dao
import model.Book

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import db.MySQLBookStore

class MySQLOperations(mySQLBookStore: MySQLBookStore) extends BookManagementDAO {
  override def createBook(newBook: Book): Future[Either[String, Book]] = Future {
    val query = "INSERT INTO Books (id, bookTitle, authorName, publishingYear) VALUES (?, ?, ?, ?)"
    val preparedStatement = mySQLBookStore.connection.prepareStatement(query)
    preparedStatement.setString(1, newBook.id)
    preparedStatement.setString(2, newBook.bookTitle)
    preparedStatement.setString(3, newBook.authorName)
    preparedStatement.setInt(4, newBook.publishingYear)

    val idExistance = preparedStatement.executeQuery(s"SELECT id FROM Books WHERE id = '$newBook.id'")

    if (idExistance.next()){
      Left("Book with the same ID already exist!")
    } else {
      preparedStatement.executeUpdate()
      Right(newBook)
    }
  }.recoverWith{
    case exception: Exception => Future.successful(Left(s"Unexpected Exception Occurred! Unable to create book! ${exception.getMessage}"))
  }

  override def retrieveBook(bookID: String): Future[Either[String, Option[Book]]] = Future {
    val query = s"SELECT * FROM Books WHERE id = $bookID"
    val preparedStatement = mySQLBookStore.connection.prepareStatement(query)
    val retrievedBook = preparedStatement.executeQuery()

    if (retrievedBook.next()){
      val id = retrievedBook.getString("id")
      val bookTitle = retrievedBook.getString("bookTitle")
      val authorName = retrievedBook.getString("authorName")
      val publishingYear = retrievedBook.getInt("publishingYear")
      val book = Book(id, bookTitle, authorName, publishingYear)

      Right(Some(book))
    } else {
      Left("Book you are trying to retrieve doesn't exist!")
    }
  }.recoverWith {
    case exception: Exception => Future.successful(Left(s"Unexpected Error Occurred! Unable to retrieve book! ${exception.getMessage}"))
  }

  override def updateBook(updatedBook: Book): Future[Either[String, Option[Book]]] = Future {
    val query = s"UPDATE Books SET bookTitle = ?, authorName = ?, publishingYear = ? WHERE id = ?"
    val preparedStatement = mySQLBookStore.connection.prepareStatement(query)
    preparedStatement.setString(4, updatedBook.id)
    preparedStatement.setString(1, updatedBook.bookTitle)
    preparedStatement.setString(2, updatedBook.authorName)
    preparedStatement.setInt(3, updatedBook.publishingYear)

    val bookExistance = preparedStatement.executeQuery(s"SELECT id FROM Books WHERE id = '$updatedBook.id'")

    if (bookExistance.next()) {
      preparedStatement.executeUpdate()
      Right(Some(updatedBook))
    } else {
      Left("Book you are trying to update doesn't exist!")
    }
  }.recoverWith {
    case exception: Exception => Future.successful(Left(s"Unexpected Error Occurred! Unable to update book! ${exception.getMessage}"))
  }

  override def deleteBook(bookID: String): Future[Either[String, Option[Book]]] = Future {
    val query = s"DELETE FROM Books WHERE id = $bookID"
    val preparedStatement = mySQLBookStore.connection.prepareStatement(query)

    val bookExistance = preparedStatement.executeQuery(s"SELECT id FROM Books WHERE id = $bookID")

    if (bookExistance.next()) {
      val id = bookExistance.getString("id")
      val bookTitle = bookExistance.getString("bookTitle")
      val authorName = bookExistance.getString("authorName")
      val publishingYear = bookExistance.getInt("publishingYear")
      val book = Book(id, bookTitle, authorName, publishingYear)

      preparedStatement.executeUpdate()

      Right(Some(book))
    } else {
      Left("Book you are trying to delete doesn't exist")
    }
  }.recoverWith {
    case exception: Exception => Future.successful(Left(s"Unexpected Error Occurred! Unable to delete book! ${exception.getMessage}"))
  }

  override def retrieveAllBooks: Future[Either[String, List[Book]]] = Future {
    val query = "SELECT * FROM Books"
    val preparedStatement = mySQLBookStore.connection.prepareStatement(query)

    val allBooks = preparedStatement.executeQuery()
    val booksIterator = new Iterator[Book] {
      override def hasNext: Boolean = allBooks.next()

      override def next(): Book = {
        val id = allBooks.getString("id")
        val bookTitle = allBooks.getString("bookTitle")
        val authorName = allBooks.getString("authorName")
        val publishingYear = allBooks.getInt("publishingYear")
        Book(id, bookTitle, authorName, publishingYear)
      }
    }

    val bookList = booksIterator.toList

    if (bookList.isEmpty) {
      Left("No Books Available!")
    } else {
      Right(bookList)
    }
  }.recoverWith {
    case exception: Exception => Future.successful(Left(s"Unexpected Error Occurred! Unable to retrieve books! ${exception.getMessage}"))
  }

}
