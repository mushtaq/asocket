package asocket.core.api

import akka.stream.scaladsl.Source
import io.rsocket.Payload

import scala.concurrent.{ExecutionContext, Future}

trait FromPayload[T] {
  def fromPayload(payload: Payload): T
}

object FromPayload {
  implicit class RichPayloadFrom(payload: Payload) {
    def as[T: FromPayload]: T = implicitly[FromPayload[T]].fromPayload(payload)
  }

  implicit class PayloadFutureFrom(payloadF: Future[Payload]) {
    def as[T: FromPayload](implicit ec: ExecutionContext): Future[T] = payloadF.map(_.as[T])
  }

  implicit class PayloadSourceFrom[Mat](payloads: Source[Payload, Mat]) {
    def as[T: FromPayload]: Source[T, Mat] = payloads.map(_.as[T])
  }
}
