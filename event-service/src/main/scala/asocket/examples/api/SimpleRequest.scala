package asocket.examples.api

sealed trait SimpleRequest
case class HelloRequest(name: String) extends SimpleRequest
case class SquareRequest(x: Int)      extends SimpleRequest
