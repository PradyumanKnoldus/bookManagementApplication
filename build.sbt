ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.10"

lazy val root = (project in file("."))
  .settings(
    name := "BookManagementApplication",
    idePackagePrefix := Some("com.knoldus")
  )

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.8.0",
  "com.typesafe.akka" %% "akka-stream" % "2.8.0",
  "com.typesafe.akka" %% "akka-http" % "10.5.0",
  "ch.qos.logback" % "logback-classic" % "1.4.6",
  "com.typesafe.akka" %% "akka-http-spray-json" % "10.5.0",
  "io.spray" %% "spray-json" % "1.3.6",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.5",
  "org.scalatest" %% "scalatest" % "3.2.15" % Test,
  "com.typesafe.akka" %% "akka-http-testkit" % "10.5.0" % Test,
  "mysql" % "mysql-connector-java" % "8.0.32"
)

libraryDependencies ++= Seq(
  "org.scalafx" %% "scalafx" % "16.0.0-R25"
)

libraryDependencies ++= Seq(
  "org.openjfx" % "javafx-controls" % "19.0.2.1",
  "org.openjfx" % "javafx-fxml" % "19.0.2.1",
  "org.openjfx" % "javafx-media" % "19.0.2.1"
)

//enablePlugins(ScoverageSbtPlugin)
