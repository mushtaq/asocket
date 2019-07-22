package asocket.examples.server

import asocket.codec.FromPayload.RichPayload
import asocket.codec.ToPayload.RichFutureInput
import asocket.core.api.AbstractASocket
import asocket.examples.api.{HelloRequest, SimpleCodecs, SimpleRequest, SquareRequest}
import io.rsocket._

import scala.concurrent.{ExecutionContext, Future}

class SimpleSocket(implicit ec: ExecutionContext) extends AbstractASocket with SimpleCodecs {
  override def requestResponse(payload: Payload): Future[Payload] = payload.to[SimpleRequest] match {
    case HelloRequest(name) => Future.successful(s"hello $name").toPayloadF
    case SquareRequest(x)   => Future.successful(x * x).toPayloadF
  }
}
