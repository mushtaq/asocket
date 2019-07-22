package asocket.codec

import io.bullet.borer.{Encoder, Target}
import io.rsocket.Payload
import io.rsocket.util.DefaultPayload

import scala.concurrent.{ExecutionContext, Future}

trait ToPayload[T] {
  def toPayload(input: T): Payload
}

object ToPayload {
  implicit def viaBorer[T: Encoder](implicit target: Target): ToPayload[T] = { input =>
    DefaultPayload.create(target.encode(input).toByteBuffer)
  }

  implicit class RichInput[T](input: T) {
    def toPayload[S >: T: ToPayload]: Payload = implicitly[ToPayload[S]].toPayload(input)
  }

  implicit class RichFutureInput[T](input: Future[T]) {
    def toPayloadF[S >: T: ToPayload](implicit ec: ExecutionContext): Future[Payload] = {
      input.map(implicitly[ToPayload[S]].toPayload)
    }
  }
}
