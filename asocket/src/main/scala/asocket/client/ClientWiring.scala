package asocket.client

import akka.actor.ActorSystem
import akka.stream.{ActorMaterializer, Materializer}
import asocket.core.api.ASocket
import asocket.core.client.ASocketClientFactory
import io.rsocket.transport.akka.client.TcpClientTransport

import scala.concurrent.{ExecutionContext, Future}

class ClientWiring {
  implicit val system: ActorSystem  = ActorSystem("client")
  implicit val mat: Materializer    = ActorMaterializer()
  implicit val ec: ExecutionContext = system.dispatcher

  def socket(): Future[ASocket] = {
    val Tcp = new TcpClientTransport("localhost", 7878)
    //    val WebSocket = new WebsocketClientTransport(WebSocketRequest.fromTargetUriString(s"ws://localhost:7879"))
    val socketClientFactory = new ASocketClientFactory
    socketClientFactory.client(Tcp)
  }
}
