package com.knoldus
package bootstrap

import akka.actor.ActorSystem
import akka.stream.Materializer
import dao.{BookManagementOperations, MySQLOperations}
import routes.BookOperationsRoutes
import service.BookServicesImplementation

import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import db.{BookStore, MySQLBookStore}

import scala.concurrent.ExecutionContextExecutor

object BookManagementApplication extends App {
  // Set up necessary components
  implicit val system: ActorSystem = ActorSystem("bookStoreApp")
  implicit val materializer: Materializer = Materializer.matFromSystem(system)
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher

  // HashMap As DB
  val bookStore = new BookStore
  val bookManagementOperations = new BookManagementOperations(bookStore)

  // MySQL
  val mySQLBookStore = new MySQLBookStore
  val mysqlServices = new MySQLOperations(mySQLBookStore)

  // Calling Services
  val bookServices = new BookServicesImplementation(mysqlServices)

  // Create routes
  val routes: Route = new BookOperationsRoutes(bookServices).routes

  // Start the server
  val bindingFuture = Http().newServerAt("localhost", 8800).bind(routes)
  bindingFuture.foreach { binding =>
    println(s"Server online at http://${binding.localAddress.getHostString}:${binding.localAddress.getPort}/")
  }
}
