package asocket.server

import akka.actor.ActorSystem
import akka.stream.{ActorMaterializer, Materializer}
import asocket.core.api.ASocket
import asocket.core.extensions.ARConverters
import asocket.core.server.{ASocketServer, ServiceHandler, SocketBinding}
import io.rsocket.transport.akka.server.TcpServerTransport

import scala.concurrent.{ExecutionContext, Future}

class ServerWiring {
  def start(socket: ASocket): Future[SocketBinding] = {
    implicit val system: ActorSystem  = ActorSystem.create
    implicit val mat: Materializer    = ActorMaterializer()
    implicit val ec: ExecutionContext = system.dispatcher
    val converters                    = new ARConverters()

    val Tcp = new TcpServerTransport("0.0.0.0", 7878)
//    val WebSocket                    = new WebsocketServerTransport("0.0.0.0", 7879)
    val serviceHandler = new ServiceHandler(socket, converters)
    val socketServer   = new ASocketServer(Tcp, serviceHandler)
    socketServer.start(converters)
  }
}
