package csw.simple.api

import io.bullet.borer.derivation.ArrayBasedCodecs.deriveCodecForUnaryCaseClass
import io.bullet.borer.derivation.MapBasedCodecs._
import io.bullet.borer.{Cbor, Codec, Target}

trait SimpleCodecs {
  implicit val target: Target = Cbor

  implicit val helloCodec: Codec[HelloRequest]    = deriveCodecForUnaryCaseClass[HelloRequest]
  implicit val squareCodec: Codec[SquareRequest]  = deriveCodecForUnaryCaseClass[SquareRequest]
  implicit val requestCodec: Codec[SimpleRequest] = deriveCodec[SimpleRequest]
}
