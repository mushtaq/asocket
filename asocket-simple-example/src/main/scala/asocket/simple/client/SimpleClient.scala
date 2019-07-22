package asocket.simple.client

import akka.stream.scaladsl.Source
import akka.{Done, NotUsed}
import asocket.borer.codecs.ASocketCodecs
import asocket.core.api.ASocket
import asocket.core.api.FromPayload.{PayloadFutureFrom, PayloadSourceFrom}
import asocket.core.api.ToPayload.{RichInputTo, RichSourceTo}
import csw.simple.api.Messages._
import csw.simple.api.{SimpleApi, Codecs}

import scala.concurrent.{ExecutionContext, Future}

class SimpleClient(socket: ASocket)(implicit ec: ExecutionContext) extends SimpleApi with Codecs with ASocketCodecs {
  override def hello(name: String): Future[String] = {
    socket.requestResponse(Hello(name).payload).as[String]
  }

  override def square(number: Int): Future[Int] = {
    socket.requestResponse(Square(number).payload).as[Int]
  }

  override def getNames(size: Int): Source[String, NotUsed] = {
    socket.requestStream(GetNames(size).payload).as[String]
  }

  override def getNumbers(divisibleBy: Int): Source[Int, NotUsed] = {
    socket.requestStream(GetNumbers(divisibleBy).payload).as[Int]
  }

  override def helloAll(names: Source[String, NotUsed]): Source[String, NotUsed] = {
    socket.requestChannel(names.map(Hello).payload).as[String]
  }

  override def squareAll(numbers: Source[Int, NotUsed]): Source[Int, NotUsed] = {
    socket.requestChannel(numbers.map(Square).payload).as[Int]
  }

  override def ping(msg: String): Future[Done] = {
    socket.fireAndForget(Ping(msg).payload)
  }

  override def publish(number: Int): Future[Done] = {
    socket.fireAndForget(Publish(number).payload)
  }
}
