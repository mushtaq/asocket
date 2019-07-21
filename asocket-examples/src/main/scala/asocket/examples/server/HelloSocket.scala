package asocket.examples.server

import asocket.core.api.AbstractASocket
import io.rsocket._
import io.rsocket.util.DefaultPayload

import scala.concurrent.Future

class HelloSocket extends AbstractASocket {
  override def requestResponse(payload: Payload): Future[Payload] = {
    Future.successful(DefaultPayload.create(s"Hello ${payload.getDataUtf8}"))
  }
}
