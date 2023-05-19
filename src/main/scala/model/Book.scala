package com.knoldus
package model

// Properties of a Book
case class Book(id: String, bookTitle: String, authorName: String, publishingYear: Int)

// Case class to use as an entity in Creation of class
case class BookCreation(bookTitle: String, authorName: String, publishingYear: Int)
