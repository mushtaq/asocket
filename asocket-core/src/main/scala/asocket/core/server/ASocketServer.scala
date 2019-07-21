package asocket.core.server

import akka.actor.ActorSystem
import akka.stream.{ActorMaterializer, Materializer}
import asocket.core.api.ASocket
import asocket.core.extensions.ARConverters
import io.rsocket.Closeable
import io.rsocket.transport.ServerTransport

import scala.concurrent.{ExecutionContext, Future}

class ASocketServer {
  def start(socket: ASocket, serverTransport: ServerTransport[_ <: Closeable])(
      implicit system: ActorSystem
  ): Future[SocketBinding] = {
    implicit val mat: Materializer    = ActorMaterializer()
    implicit val ec: ExecutionContext = system.dispatcher
    val converters                    = new ARConverters()

    val serviceHandler = new ServiceHandler(socket, converters)
    new ASocketServerHelper(serverTransport, serviceHandler).start(converters)
  }
}
