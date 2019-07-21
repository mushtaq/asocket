package asocket.client

import akka.actor.ActorSystem
import akka.stream.{ActorMaterializer, Materializer}
import asocket.api.TransportType
import asocket.core.api.ASocket
import asocket.core.client.ASocketClientFactory
import asocket.core.extensions.ARConverters
import io.rsocket.transport.ClientTransport

import scala.concurrent.{ExecutionContext, Future}

class ClientWiring {
  def socket(clientTransport: ClientTransport)(implicit system: ActorSystem): Future[ASocket] = {
    implicit val mat: Materializer    = ActorMaterializer()
    implicit val ec: ExecutionContext = system.dispatcher
    val converters                    = new ARConverters()

    val socketClientFactory = new ASocketClientFactory
    socketClientFactory.client(clientTransport, converters)
  }
}
