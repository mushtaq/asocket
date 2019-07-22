package asocket.core.api

import io.rsocket.Payload

import scala.concurrent.{ExecutionContext, Future}

trait ToPayload[T] {
  def toPayload(input: T): Payload
}

object ToPayload {

  implicit class RichInput[T](input: T) {
    def toPayload[S >: T: ToPayload]: Payload = implicitly[ToPayload[S]].toPayload(input)
  }

  implicit class RichFutureInput[T](input: Future[T]) {
    def toPayloadF[S >: T: ToPayload](implicit ec: ExecutionContext): Future[Payload] = {
      input.map(implicitly[ToPayload[S]].toPayload)
    }
  }
}
