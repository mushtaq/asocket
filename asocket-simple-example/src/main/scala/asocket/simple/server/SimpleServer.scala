package asocket.simple.server

import akka.stream.scaladsl.Source
import akka.{Done, NotUsed}
import asocket.borer.codecs.ASocketCodecs
import asocket.core.api.AbstractASocket
import asocket.core.api.FromPayload.{PayloadSourceFrom, RichPayloadFrom}
import asocket.core.api.ToPayload.{RichFutureTo, RichSourceTo}
import csw.simple.api.Codecs
import csw.simple.api.Messages._
import csw.simple.impl.SimpleImpl
import io.rsocket._

import scala.concurrent.{ExecutionContext, Future}

class SimpleServer(simpleService: SimpleImpl)(implicit ec: ExecutionContext)
    extends AbstractASocket
    with Codecs
    with ASocketCodecs {

  override def requestResponse(payload: Payload): Future[Payload] = payload.to[RequestResponse] match {
    case Hello(name)    => simpleService.hello(name).toPayloadF
    case Square(number) => simpleService.square(number).toPayloadF
  }

  override def fireAndForget(payload: Payload): Future[Done] = payload.to[FireAndForget] match {
    case Ping(msg)       => simpleService.ping(msg)
    case Publish(number) => simpleService.publish(number)
  }

  override def requestStream(payload: Payload): Source[Payload, NotUsed] = payload.to[RequestStream] match {
    case GetNames(size)          => simpleService.getNames(size).toPayloadS
    case GetNumbers(divisibleBy) => simpleService.getNumbers(divisibleBy).toPayloadS
  }

  override def requestChannel(payloads: Source[Payload, NotUsed]): Source[Payload, NotUsed] = {
    payloads.toS[RequestResponse].prefixAndTail(1).flatMapConcat {
      case (xs, s) =>
        xs.headOption match {
          case Some(Hello(name)) =>
            simpleService.helloAll(s.map(_.asInstanceOf[Hello].name).prepend(Source.single(name))).toPayloadS[String]
          case Some(Square(number)) =>
            simpleService.squareAll(s.map(_.asInstanceOf[Square].number).prepend(Source.single(number))).toPayloadS[Int]
          case None => Source.empty
        }
    }
  }
}
