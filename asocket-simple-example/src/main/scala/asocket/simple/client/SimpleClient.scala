package asocket.simple.client

import akka.stream.scaladsl.Source
import akka.{Done, NotUsed}
import asocket.borer.codecs.ASocketCodecs
import asocket.core.api.ASocket
import asocket.core.api.FromPayload.{PayloadFutureFrom, PayloadSourceFrom}
import asocket.core.api.ToPayload.{RichInputTo, RichSourceTo}
import csw.simple.api.SimpleRequest._
import csw.simple.api.{SimpleApi, SimpleCodecs}

import scala.concurrent.{ExecutionContext, Future}

class SimpleClient(socket: ASocket)(implicit ec: ExecutionContext) extends SimpleApi with SimpleCodecs with ASocketCodecs {
  override def hello(name: String): Future[String] = {
    socket.requestResponse(Hello(name).toPayload[RequestResponse]).toF[String]
  }

  override def square(number: Int): Future[Int] = {
    socket.requestResponse(Square(number).toPayload[RequestResponse]).toF[Int]
  }

  override def getNames(size: Int): Source[String, NotUsed] = {
    socket.requestStream(GetNames(size).toPayload[RequestStream]).toS[String]
  }

  override def getNumbers(divisibleBy: Int): Source[Int, NotUsed] = {
    socket.requestStream(GetNumbers(divisibleBy).toPayload[RequestStream]).toS[Int]
  }

  override def helloAll(names: Source[String, NotUsed]): Source[String, NotUsed] = {
    socket.requestChannel(names.map(Hello).toPayloadS[RequestResponse]).toS[String]
  }

  override def squareAll(numbers: Source[Int, NotUsed]): Source[Int, NotUsed] = {
    socket.requestChannel(numbers.map(Square).toPayloadS[RequestResponse]).toS[Int]
  }

  override def ping(msg: String): Future[Done] = {
    socket.fireAndForget(Ping(msg).toPayload[FireAndForget])
  }

  override def publish(number: Int): Future[Done] = {
    socket.fireAndForget(Publish(number).toPayload[FireAndForget])
  }
}
