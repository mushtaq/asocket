package asocket.examples.server

import asocket.core.api.AbstractASocket
import asocket.core.api.FromPayload.RichPayload
import asocket.core.api.ToPayload.RichFutureInput
import asocket.examples.api.{HelloRequest, SimpleCodecs, SimpleRequest, SquareRequest}
import asocket.examples.codecs.ASocketCodecs
import io.rsocket._

import scala.concurrent.{ExecutionContext, Future}

class SimpleSocket(implicit ec: ExecutionContext) extends AbstractASocket with SimpleCodecs with ASocketCodecs {
  override def requestResponse(payload: Payload): Future[Payload] = payload.to[SimpleRequest] match {
    case HelloRequest(name) => Future.successful(s"hello $name").toPayloadF
    case SquareRequest(x)   => Future.successful(x * x).toPayloadF
  }
}
