import Dependencies._
import Borer._

inThisBuild(
  Seq(
    scalaVersion := "2.12.8",
    version := "0.1.0-SNAPSHOT",
    organization := "com.github.mushtaq.asocket",
    organizationName := "ThoughtWorks",
    resolvers += Resolver.jcenterRepo,
    scalafmtOnCompile := true
  )
)

lazy val `root` = project
  .in(file("."))
  .aggregate(
    `asocket-borer`,
    `asocket-core`,
    `asocket-event-example`,
    `asocket-ping-example`,
    `event-service`
  )

lazy val `asocket-borer` = project
  .dependsOn(`asocket-core`)
  .settings(
    libraryDependencies ++= Seq(
      `borer-core`,
      `borer-derivation`
    )
  )

lazy val `asocket-core` = project
  .settings(
    libraryDependencies ++= Seq(
      `rsocket-core`,
      `akka-stream`
    )
  )

lazy val `asocket-event-example` = project
  .dependsOn(`event-service`, `asocket-core`, `asocket-borer`)
  .settings(
    libraryDependencies ++= Seq(
      `rsocket-transport-akka`
    )
  )

lazy val `asocket-ping-example` = project
  .dependsOn(`asocket-core`)
  .settings(
    libraryDependencies ++= Seq(
      `rsocket-transport-akka`,
      `rsocket-test`,
      `scalatest` % Test
    )
  )

lazy val `event-service` = project
  .settings(
    libraryDependencies ++= Seq(
      `borer-core`,
      `borer-derivation`
    )
  )
