package asocket.examples.client

import java.time.Duration

import akka.actor.ActorSystem
import akka.stream.{ActorMaterializer, Materializer}
import asocket.core.client.ASocketClient
import asocket.core.extensions.ARConverters
import asocket.core.server.AToRSocket
import io.rsocket.test.PingClient
import io.rsocket.transport.akka.client.TcpClientTransport
import reactor.core.publisher.Mono

import scala.compat.java8.FutureConverters.FutureOps
import scala.concurrent.ExecutionContext

object ClientPing {
  def main(args: Array[String]): Unit = {

    implicit val system: ActorSystem  = ActorSystem("server")
    implicit val mat: Materializer    = ActorMaterializer()
    implicit val ec: ExecutionContext = system.dispatcher
    val converters                    = new ARConverters()

    val tcpTransport = new TcpClientTransport("localhost", 6000)
    //    val wsTransport  = new WebsocketClientTransport(WebSocketRequest.fromTargetUriString(s"ws://localhost:7000"))

    val pingClient = new PingClient(
      Mono.fromFuture(new ASocketClient().socket(tcpTransport).toJava.toCompletableFuture).map(new AToRSocket(_, converters))
    )

    val recorder = pingClient.startTracker(Duration.ofSeconds(1))
    val count    = 1000000000

    pingClient
      .startPingPong(count, recorder)
      .doOnTerminate(() => System.out.println("Sent " + count + " messages."))
      .blockLast
  }
}
