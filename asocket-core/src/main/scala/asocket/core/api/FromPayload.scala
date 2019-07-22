package asocket.core.api

import io.rsocket.Payload

import scala.concurrent.{ExecutionContext, Future}

trait FromPayload[T] {
  def fromPayload(payload: Payload): T
}

object FromPayload {

  implicit class RichPayload(payload: Payload) {
    def to[T: FromPayload]: T = implicitly[FromPayload[T]].fromPayload(payload)
  }

  implicit class RichFuturePayload(payload: Future[Payload]) {
    def toF[T: FromPayload](implicit ec: ExecutionContext): Future[T] = {
      payload.map(implicitly[FromPayload[T]].fromPayload)
    }
  }
}
