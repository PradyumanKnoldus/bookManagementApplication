package com.knoldus
package bootstrap

import akka.actor.ActorSystem
import akka.stream.Materializer
import dao.BookManagementOperations
import routes.BookOperationsRoutes
import service.BookServicesImplementation
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import db.BookStore

import scala.concurrent.ExecutionContextExecutor

object BookManagementApplication extends App {
  // Set up necessary components
  implicit val system: ActorSystem = ActorSystem("bookStoreApp")
  implicit val materializer: Materializer = Materializer.matFromSystem(system)
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher

  // Create instances of DAO and Services
  val bookStore = new BookStore
  val bookManagementOperations = new BookManagementOperations(bookStore)
  val bookServices = new BookServicesImplementation(bookManagementOperations)

  // Create routes
  val routes: Route = new BookOperationsRoutes(bookServices).routes

  // Start the server
  val bindingFuture = Http().newServerAt("localhost", 8080).bind(routes)
  bindingFuture.foreach { binding =>
    println(s"Server online at http://${binding.localAddress.getHostString}:${binding.localAddress.getPort}/")
  }
}
