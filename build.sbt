import Dependencies._

ThisBuild / scalaVersion := "2.12.8"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "com.thoughtworks"
ThisBuild / organizationName := "rsocket-demo"

lazy val root = (project in file("."))
  .settings(
    name := "rsocket-demo",
    resolvers += Resolver.jcenterRepo,
    libraryDependencies ++= Seq(
      `akka-http`,
      `akka-stream`,
      `rsocket-core`,
      `rsocket-transport-akka`,
      `rsocket-test`,
      `scalatest`    % Test,
    )
  )
