package asocket.examples.client

import akka.actor.ActorSystem
import akka.stream.{ActorMaterializer, Materializer}
import asocket.codec.FromPayload.{RichFuturePayload, RichPayload}
import asocket.codec.ToPayload.RichInput
import asocket.core.client.ASocketClient
import asocket.examples.api.{HelloRequest, SimpleCodecs, SimpleRequest, SquareRequest}
import io.rsocket.transport.akka.client.TcpClientTransport

import scala.concurrent.ExecutionContext

object SimpleClient extends SimpleCodecs {
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
