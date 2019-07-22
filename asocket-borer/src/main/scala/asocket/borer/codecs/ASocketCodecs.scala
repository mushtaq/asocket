package asocket.borer.codecs

import asocket.core.api.{FromPayload, ToPayload}
import io.bullet.borer.{Decoder, Encoder, Target}
import io.rsocket.util.DefaultPayload

trait ASocketCodecs {
  implicit def payloadDecViaBorer[T: Decoder](implicit target: Target): FromPayload[T] = { payload =>
    target.decode(payload.getData).to[T].value
  }

  implicit def payloadEncViaBorer[T: Encoder](implicit target: Target): ToPayload[T] = { input =>
    DefaultPayload.create(target.encode(input).toByteBuffer)
  }
}
