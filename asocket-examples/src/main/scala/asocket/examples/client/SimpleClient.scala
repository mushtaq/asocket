package asocket.examples.client

import akka.actor.ActorSystem
import akka.stream.{ActorMaterializer, Materializer}
import asocket.core.api.FromPayload.RichFuturePayload
import asocket.core.api.ToPayload.RichInput
import asocket.core.client.ASocketClient
import asocket.examples.api.{HelloRequest, SimpleCodecs, SimpleRequest, SquareRequest}
import asocket.examples.codecs.ASocketCodecs
import io.rsocket.transport.akka.client.TcpClientTransport

import scala.concurrent.ExecutionContext

object SimpleClient extends SimpleCodecs with ASocketCodecs {
  def main(args: Array[String]): Unit = {
    implicit val system: ActorSystem  = ActorSystem("server")
    implicit val mat: Materializer    = ActorMaterializer()
    implicit val ec: ExecutionContext = system.dispatcher

    val tcpTransport = new TcpClientTransport("localhost", 6000)
//    val wsTransport  = new WebsocketClientTransport(WebSocketRequest.fromTargetUriString(s"ws://localhost:7000"))

    new ASocketClient().socket(tcpTransport).foreach { socket =>
      socket.requestResponse(HelloRequest("mushtaq").toPayload[SimpleRequest]).toF[String].foreach(println)
      socket.requestResponse(SquareRequest(9).toPayload[SimpleRequest]).toF[Int].foreach(println)
    }
  }
}
