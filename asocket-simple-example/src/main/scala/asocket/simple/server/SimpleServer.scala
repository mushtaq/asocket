package asocket.simple.server

import asocket.borer.codecs.ASocketCodecs
import asocket.core.api.AbstractASocket
import asocket.core.api.FromPayload.RichPayload
import asocket.core.api.ToPayload.RichFutureInput
import csw.simple.api.{HelloRequest, SimpleCodecs, SimpleRequest, SquareRequest}
import csw.simple.impl.SimpleService
import io.rsocket._

import scala.concurrent.{ExecutionContext, Future}

class SimpleServer(simpleService: SimpleService)(implicit ec: ExecutionContext)
    extends AbstractASocket
    with SimpleCodecs
    with ASocketCodecs {

  override def requestResponse(payload: Payload): Future[Payload] = payload.to[SimpleRequest] match {
    case HelloRequest(name) => simpleService.sayHello(name).toPayloadF
    case SquareRequest(x)   => simpleService.square(x).toPayloadF
  }
}
