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
    `asocket-core`,
    `asocket-examples`
  )

lazy val `asocket-core` = project
  .settings(
    libraryDependencies ++= Seq(
      `rsocket-core`,
      `akka-stream`
    )
  )

lazy val `asocket-examples` = project
  .dependsOn(`asocket-core`)
  .settings(
    libraryDependencies ++= Seq(
      `rsocket-transport-akka`,
      `borer-core`,
      `borer-derivation`,
      `rsocket-test`,
      `scalatest` % Test
    )
  )
