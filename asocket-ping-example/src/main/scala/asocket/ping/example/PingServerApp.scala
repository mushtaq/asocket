package asocket.ping.example

import akka.actor.ActorSystem
import akka.stream.{ActorMaterializer, Materializer}
import asocket.core.server.ASocketServer
import io.rsocket.transport.akka.server.TcpServerTransport

import scala.concurrent.ExecutionContext

object PingServerApp {
  def main(args: Array[String]): Unit = {

    implicit val system: ActorSystem  = ActorSystem("server")
    implicit val mat: Materializer    = ActorMaterializer()
    implicit val ec: ExecutionContext = system.dispatcher

    val transport = {
      new TcpServerTransport("0.0.0.0", 6000)
//      new WebsocketServerTransport("0.0.0.0", 7000)
    }

    new ASocketServer().start(new PingServer(), transport)
  }
}
