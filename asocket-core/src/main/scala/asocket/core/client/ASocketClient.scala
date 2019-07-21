package asocket.core.client

import akka.actor.ActorSystem
import akka.stream.{ActorMaterializer, Materializer}
import asocket.core.api.ASocket
import asocket.core.extensions.ARConverters
import io.rsocket.transport.ClientTransport

import scala.concurrent.{ExecutionContext, Future}

class ASocketClient {
  def socket(clientTransport: ClientTransport)(implicit system: ActorSystem): Future[ASocket] = {
    implicit val mat: Materializer    = ActorMaterializer()
    implicit val ec: ExecutionContext = system.dispatcher
    val converters                    = new ARConverters()

    val socketClientFactory = new ASocketClientFactory
    socketClientFactory.client(clientTransport, converters)
  }
}
