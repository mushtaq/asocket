package asocket.examples.client

import akka.actor.ActorSystem
import akka.stream.{ActorMaterializer, Materializer}
import asocket.client.ClientWiring
import io.rsocket.transport.akka.client.TcpClientTransport
import io.rsocket.util.DefaultPayload

import scala.concurrent.ExecutionContext

object ClientHello {
  def main(args: Array[String]): Unit = {
    implicit val system: ActorSystem  = ActorSystem("server")
    implicit val mat: Materializer    = ActorMaterializer()
    implicit val ec: ExecutionContext = system.dispatcher

    val tcpTransport = new TcpClientTransport("localhost", 6000)
//    val wsTransport  = new WebsocketClientTransport(WebSocketRequest.fromTargetUriString(s"ws://localhost:7000"))

    new ClientWiring().socket(tcpTransport).foreach { socket =>
      socket.requestResponse(DefaultPayload.create("mushtaq")).foreach(x => println(x.getDataUtf8))
    }
  }
}
