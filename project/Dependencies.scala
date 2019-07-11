import sbt._

object Dependencies {
  val `scalatest`              = "org.scalatest"     %% "scalatest"              % "3.0.8"
  val `rsocket-transport-akka` = "io.rsocket"        %% "rsocket-transport-akka" % "0.1.0"
  val `akka-http`              = "com.typesafe.akka" %% "akka-http"              % "10.1.8"
  val `akka-stream`            = "com.typesafe.akka" %% "akka-stream"            % "2.5.23"
  val `rsocket-core`           = "io.rsocket"        % "rsocket-core"            % "0.11.18"
  val `rsocket-test`           = "io.rsocket"        % "rsocket-test"            % "0.11.18"
}
