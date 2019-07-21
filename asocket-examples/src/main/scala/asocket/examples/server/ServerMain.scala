package asocket.examples.server

import akka.actor.ActorSystem
import akka.stream.{ActorMaterializer, Materializer}
import asocket.server.ServerWiring
import io.rsocket.transport.akka.server.TcpServerTransport

import scala.concurrent.ExecutionContext

object ServerMain {
  def main(args: Array[String]): Unit = {
    //  val socket = new PingSocket()
    val socket = new HelloSocket()

    implicit val system: ActorSystem  = ActorSystem("server")
    implicit val mat: Materializer    = ActorMaterializer()
    implicit val ec: ExecutionContext = system.dispatcher

    val tcpTransport = new TcpServerTransport("0.0.0.0", 6000)
//    val wsTransport                   = new WebsocketServerTransport("0.0.0.0", 7000)

    new ServerWiring().start(socket, tcpTransport)
  }
}
