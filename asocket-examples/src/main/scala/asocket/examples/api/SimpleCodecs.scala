package asocket.examples.api

import asocket.core.api.{FromPayload, ToPayload}
import io.bullet.borer.derivation.ArrayBasedCodecs.deriveCodecForUnaryCaseClass
import io.bullet.borer.derivation.MapBasedCodecs._
import io.bullet.borer.{Cbor, Codec, Decoder, Encoder, Target}
import io.rsocket.util.DefaultPayload

trait SimpleCodecs {
  implicit val target: Target = Cbor

  implicit def payloadDecViaBorer[T: Decoder](implicit target: Target): FromPayload[T] = { payload =>
    target.decode(payload.getData).to[T].value
  }

  implicit def payloadEncViaBorer[T: Encoder](implicit target: Target): ToPayload[T] = { input =>
    DefaultPayload.create(target.encode(input).toByteBuffer)
  }

  implicit val helloCodec: Codec[HelloRequest]    = deriveCodecForUnaryCaseClass[HelloRequest]
  implicit val squareCodec: Codec[SquareRequest]  = deriveCodecForUnaryCaseClass[SquareRequest]
  implicit val requestCodec: Codec[SimpleRequest] = deriveCodec[SimpleRequest]
}
