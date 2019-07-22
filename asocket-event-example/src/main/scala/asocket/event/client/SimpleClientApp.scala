package asocket.event.client

import akka.actor.ActorSystem
import akka.stream.{ActorMaterializer, Materializer}
import asocket.borer.codecs.ASocketCodecs
import asocket.core.client.ASocketClient
import csw.event.api.SimpleCodecs
import io.rsocket.transport.akka.client.TcpClientTransport

import scala.concurrent.ExecutionContext

object SimpleClientApp extends SimpleCodecs with ASocketCodecs {
  def main(args: Array[String]): Unit = {
    implicit val system: ActorSystem  = ActorSystem("server")
    implicit val mat: Materializer    = ActorMaterializer()
    implicit val ec: ExecutionContext = system.dispatcher

    val transport = {
      new TcpClientTransport("localhost", 6000)
//      new WebsocketClientTransport(WebSocketRequest.fromTargetUriString(s"ws://localhost:7000"))
    }

    val socketF = new ASocketClient().socket(transport)
    val clientF = socketF.map(new SimpleClient(_))

    clientF.foreach { client =>
      client.sayHello("mushtaq").foreach(println)
      client.square(9).foreach(println)
    }
  }
}
