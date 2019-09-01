import sbt._

object Dependencies {
  val `scalatest`              = "org.scalatest"     %% "scalatest"              % "3.0.8"
  val `rsocket-transport-akka` = "com.github.mushtaq" % "rsocket-transport-akka" % "740a0a7"
  val `akka-http`              = "com.typesafe.akka" %% "akka-http"              % "10.1.9"
  val `akka-stream`            = "com.typesafe.akka" %% "akka-stream"            % "2.5.25"
  val `rsocket-core`           = "io.rsocket"        % "rsocket-core"            % "0.11.18"
  val `rsocket-test`           = "io.rsocket"        % "rsocket-test"            % "0.11.18"
  val `silencer-lib`           = "com.github.ghik"   %% "silencer-lib"           % "1.4.1"
  val `silencer-plugin`        = "com.github.ghik"   %% "silencer-plugin"        % "1.4.1"
}

object Borer {
  val Version = "0.11.1"
  val Org     = "io.bullet"

  val `borer-core`        = Org %% "borer-core"        % Version
  val `borer-derivation`  = Org %% "borer-derivation"  % Version
  val `borer-compat-akka` = Org %% "borer-compat-akka" % Version
}
