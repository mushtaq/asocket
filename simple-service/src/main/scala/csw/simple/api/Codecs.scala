package csw.simple.api

import akka.Done
import com.github.ghik.silencer.silent
import csw.simple.api.Messages._
import io.bullet.borer.derivation.ArrayBasedCodecs.deriveUnaryCodec
import io.bullet.borer.derivation.MapBasedCodecs._
import io.bullet.borer.{Cbor, Codec, Target}

trait Codecs {
  implicit val target: Target = Cbor

  implicit lazy val doneCodec: Codec[Done] = Codec.implicitly[String].bimap[Done](_ => "done", _ => Done)

  implicit def requestResponseCodec[T <: RequestResponse]: Codec[T] = {
    @silent implicit val helloCodec: Codec[Hello]   = deriveUnaryCodec[Hello]
    @silent implicit val squareCodec: Codec[Square] = deriveUnaryCodec[Square]
    deriveCodec[RequestResponse].asInstanceOf[Codec[T]]
  }

  implicit def requestStreamCodec[T <: RequestStream]: Codec[T] = {
    @silent implicit val getNamesCodec: Codec[GetNames]     = deriveUnaryCodec[GetNames]
    @silent implicit val getNumbersCodec: Codec[GetNumbers] = deriveUnaryCodec[GetNumbers]
    deriveCodec[RequestStream].asInstanceOf[Codec[T]]
  }

  implicit def fireAndForgetCodec[T <: FireAndForget]: Codec[T] = {
    @silent implicit val getNamesCodec: Codec[Ping]      = deriveUnaryCodec[Ping]
    @silent implicit val getNumbersCodec: Codec[Publish] = deriveUnaryCodec[Publish]
    deriveCodec[FireAndForget].asInstanceOf[Codec[T]]
  }

  implicit def requestChannelCodec[T <: RequestChannel]: Codec[T] = {
    @silent implicit val helloAllCodec: Codec[HelloAll]   = deriveUnaryCodec[HelloAll]
    @silent implicit val squareAllCodec: Codec[SquareAll] = deriveUnaryCodec[SquareAll]
    deriveCodec[RequestChannel].asInstanceOf[Codec[T]]
  }
}
