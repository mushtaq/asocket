package asocket.codec

import io.bullet.borer.{Encoder, Target}
import io.rsocket.Payload
import io.rsocket.util.DefaultPayload

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
}
