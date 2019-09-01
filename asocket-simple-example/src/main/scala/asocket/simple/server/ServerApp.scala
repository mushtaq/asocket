package asocket.simple.server

import akka.actor.ActorSystem
import akka.stream.{ActorMaterializer, Materializer}
import asocket.core.server.ASocketServer
import csw.simple.impl.SimpleImpl
import io.rsocket.transport.akka.server.{TcpServerTransport, WebsocketServerTransport}

import scala.concurrent.ExecutionContext

object ServerApp {
  def main(args: Array[String]): Unit = {

    implicit val system: ActorSystem  = ActorSystem("server")
    implicit val mat: Materializer    = ActorMaterializer()
    implicit val ec: ExecutionContext = system.dispatcher

    val transport = {
//      new TcpServerTransport("0.0.0.0", 6000)
      new WebsocketServerTransport("0.0.0.0", 7000)
    }

    val simpleService = new SimpleImpl
    new ASocketServer().start(new SimpleServer(simpleService), transport)
  }
}
