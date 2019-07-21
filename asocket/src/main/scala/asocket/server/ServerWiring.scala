package asocket.server

import akka.actor.ActorSystem
import akka.stream.{ActorMaterializer, Materializer}
import asocket.core.api.ASocket
import asocket.core.extensions.ARConverters
import asocket.core.server.{ASocketServer, ServiceHandler, SocketBinding}
import io.rsocket.Closeable
import io.rsocket.transport.ServerTransport

import scala.concurrent.{ExecutionContext, Future}

class ServerWiring {
  def start(socket: ASocket, serverTransport: ServerTransport[_ <: Closeable])(
      implicit system: ActorSystem
  ): Future[SocketBinding] = {
    implicit val mat: Materializer    = ActorMaterializer()
    implicit val ec: ExecutionContext = system.dispatcher
    val converters                    = new ARConverters()

    val serviceHandler = new ServiceHandler(socket, converters)
    val socketServer   = new ASocketServer(serverTransport, serviceHandler)
    socketServer.start(converters)
  }
}
