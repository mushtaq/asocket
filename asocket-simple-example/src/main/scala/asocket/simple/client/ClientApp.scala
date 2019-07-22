package asocket.simple.client

import akka.actor.ActorSystem
import akka.stream.scaladsl.Source
import akka.stream.{ActorMaterializer, Materializer}
import asocket.borer.codecs.ASocketCodecs
import asocket.core.client.ASocketClient
import csw.simple.api.Codecs
import io.rsocket.transport.akka.client.TcpClientTransport

import scala.concurrent.ExecutionContext

object ClientApp extends Codecs with ASocketCodecs {
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
      client.hello("mushtaq").foreach(println)
      client.square(9).foreach(println)
      client.helloAll(Source(List("a", "b", "c"))).runForeach(println)
      client.squareAll(Source(List(8, 4, 12))).runForeach(println)
      client.ping("I am here").foreach(println)
      client.publish(100).foreach(println)
      client.getNames(10).runForeach(println)
      client.getNumbers(7).runForeach(println)
    }
  }
}
