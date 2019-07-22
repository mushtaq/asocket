package csw.simple.api

import akka.Done
import com.github.ghik.silencer.silent
import csw.simple.api.SimpleRequest.{
  FireAndForget,
  GetNames,
  GetNumbers,
  Hello,
  Ping,
  Publish,
  RequestResponse,
  RequestStream,
  Square
}
import io.bullet.borer.derivation.ArrayBasedCodecs.deriveCodecForUnaryCaseClass
import io.bullet.borer.derivation.MapBasedCodecs._
import io.bullet.borer.{Cbor, Codec, Target}

trait SimpleCodecs {
  implicit val target: Target = Cbor

  implicit lazy val doneCodec: Codec[Done] = Codec.implicitly[String].bimap[Done](_ => "done", _ => Done)

  implicit val requestResponseCodec: Codec[RequestResponse] = {
    @silent implicit val helloCodec: Codec[Hello]   = deriveCodecForUnaryCaseClass[Hello]
    @silent implicit val squareCodec: Codec[Square] = deriveCodecForUnaryCaseClass[Square]
    deriveCodec[RequestResponse]
  }

  implicit val requestStreamCodec: Codec[RequestStream] = {
    @silent implicit val getNamesCodec: Codec[GetNames]     = deriveCodecForUnaryCaseClass[GetNames]
    @silent implicit val getNumbersCodec: Codec[GetNumbers] = deriveCodecForUnaryCaseClass[GetNumbers]
    deriveCodec[RequestStream]
  }

  implicit val fireAndForgetCodec: Codec[FireAndForget] = {
    @silent implicit val getNamesCodec: Codec[Ping]      = deriveCodecForUnaryCaseClass[Ping]
    @silent implicit val getNumbersCodec: Codec[Publish] = deriveCodecForUnaryCaseClass[Publish]
    deriveCodec[FireAndForget]
  }
}
