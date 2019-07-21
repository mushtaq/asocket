import Dependencies._

inThisBuild(
  Seq(
    scalaVersion := "2.12.8",
    version := "0.1.0-SNAPSHOT",
    organization := "com.thoughtworks",
    organizationName := "rsocket-demo"
  )
)

lazy val `rsocket-demo` = project
  .in(file("."))
  .aggregate(`rsocket-core-akka`, demo)

lazy val demo = project
  .dependsOn(`rsocket-core-akka`)
  .settings(
    resolvers += Resolver.jcenterRepo,
    libraryDependencies ++= Seq(
      `rsocket-test`,
      `scalatest` % Test
    )
  )

lazy val `rsocket-core-akka` = project
  .settings(
    resolvers += Resolver.jcenterRepo,
    libraryDependencies ++= Seq(
      `akka-http`,
      `akka-stream`,
      `rsocket-core`,
      `rsocket-transport-akka`
    )
  )
