package csw.simple.api

object SimpleRequest {
  sealed trait RequestResponse
  case class Hello(name: String) extends RequestResponse
  case class Square(number: Int) extends RequestResponse

  sealed trait RequestStream
  case class GetNames(size: Int)          extends RequestStream
  case class GetNumbers(divisibleBy: Int) extends RequestStream

  sealed trait FireAndForget
  case class Ping(msg: String)    extends FireAndForget
  case class Publish(number: Int) extends FireAndForget
}
