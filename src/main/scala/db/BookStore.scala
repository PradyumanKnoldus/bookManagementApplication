package com.knoldus
package db

import model.Book

import scala.collection.mutable

class BookStore {

  // In-Memory Data Structure to store Books
  val books = mutable.HashMap.empty[String, Book]

  books += ("910b8438-1ea7-43a9-b63c-f1130540c0be" -> Book("910b8438-1ea7-43a9-b63c-f1130540c0be","To Kill a Mockingbird", "Harper Lee", 1960))
  books += ("4c612c9c-1662-4534-935d-07d593ec6447" -> Book("4c612c9c-1662-4534-935d-07d593ec6447", "Pride and Prejudice", "Jane Austen", 1813))
  books += ("5ff18ca7-b634-4fbf-973f-95f3559241c5" -> Book("5ff18ca7-b634-4fbf-973f-95f3559241c5", "The Catcher in the Rye", "J.D. Salinger", 1951))
  books += ("fcb01746-9dbf-4785-a422-6d4c555049f9" -> Book("fcb01746-9dbf-4785-a422-6d4c555049f9", "The White Tiger", "Aravind Adiga", 2008))
  books += ("6b0cfdc9-544d-4819-a16b-dd00a58ec99a" -> Book("6b0cfdc9-544d-4819-a16b-dd00a58ec99a", "Train to Pakistan", "Khushwant Singh", 1956))
}
