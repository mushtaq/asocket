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
    `asocket-examples`,
    `asocket`,
    `asocket-core`
  )

lazy val `asocket-examples` = project
  .dependsOn(`asocket`)
  .settings(
    libraryDependencies ++= Seq(
      `rsocket-test`,
      `scalatest` % Test
    )
  )

lazy val `asocket` = project
  .dependsOn(`asocket-core`)
  .settings(
    libraryDependencies ++= Seq(
      `rsocket-transport-akka`,
      `akka-http`,
      `borer-core`,
      `borer-derivation`,
      `borer-compat-akka`
    )
  )

lazy val `asocket-core` = project
  .settings(
    libraryDependencies ++= Seq(
      `rsocket-core`,
      `akka-stream`
    )
  )
