package asocket.ping.example

import akka.actor.ActorSystem
import akka.stream.{ActorMaterializer, Materializer}
import asocket.core.server.ASocketServer
import io.rsocket.transport.akka.server.TcpServerTransport

object PingServerApp {
  def main(args: Array[String]): Unit = {

    implicit val system: ActorSystem = ActorSystem("server")
    implicit val mat: Materializer   = ActorMaterializer()

    val transport = {
      new TcpServerTransport("0.0.0.0", 6000)
//      new WebsocketServerTransport("0.0.0.0", 7000)
    }

    new ASocketServer().start(new PingServer(), transport)
  }
}
