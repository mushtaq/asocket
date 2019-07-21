package asocket.core.client

import asocket.core.api.ASocket
import asocket.core.extensions.ARConverters
import io.rsocket.RSocketFactory
import io.rsocket.transport.ClientTransport

import scala.concurrent.Future

class ASocketClientFactory {
  def client(clientTransport: ClientTransport, converters: ARConverters): Future[ASocket] = {
    import converters._
    RSocketFactory.connect
      .frameDecoder(_.retain)
      .transport(clientTransport)
      .start
      .mapS(rsocket => new RToASocket(rsocket, converters))
  }
}
