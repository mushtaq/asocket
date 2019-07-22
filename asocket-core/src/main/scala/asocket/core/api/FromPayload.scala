package asocket.core.api

import akka.stream.scaladsl.Source
import io.rsocket.Payload

import scala.concurrent.{ExecutionContext, Future}

trait FromPayload[T] {
  def fromPayload(payload: Payload): T
}

object FromPayload {
  implicit class RichPayloadFrom(payload: Payload) {
    def to[T: FromPayload]: T = implicitly[FromPayload[T]].fromPayload(payload)
  }

  implicit class PayloadFutureFrom(payloadF: Future[Payload]) {
    def toF[T: FromPayload](implicit ec: ExecutionContext): Future[T] = payloadF.map(_.to[T])
  }

  implicit class PayloadSourceFrom[Mat](payloads: Source[Payload, Mat]) {
    def toS[T: FromPayload]: Source[T, Mat] = payloads.map(_.to[T])
  }
}
