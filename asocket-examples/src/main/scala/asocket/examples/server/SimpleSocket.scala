package asocket.examples.server

import asocket.codec.FromPayload.RichPayload
import asocket.codec.ToPayload.RichInput
import asocket.core.api.AbstractASocket
import asocket.examples.api.{HelloRequest, SimpleCodecs, SimpleRequest, SquareRequest}
import io.rsocket._

import scala.concurrent.Future

class SimpleSocket extends AbstractASocket with SimpleCodecs {
  override def requestResponse(payload: Payload): Future[Payload] = payload.to[SimpleRequest] match {
    case HelloRequest(name) => Future.successful(s"hello $name".toPayload)
    case SquareRequest(x)   => Future.successful((x * x).toPayload)
  }
}
