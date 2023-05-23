package com.knoldus
package db

class MySQLBookStore {
  import java.sql.{Connection, DriverManager}

  // Set up connection parameters
  val url = "jdbc:mysql://localhost:3306/BookStore"
  val username = "root"
  val password = "Pradyuman@123"

  // Register the MySQL driver
  Class forName("com.mysql.cj.jdbc.Driver")

  // Establish the connection
  val connection: Connection = DriverManager.getConnection(url, username, password)
}
