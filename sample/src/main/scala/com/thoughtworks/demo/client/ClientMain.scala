package com.thoughtworks.demo.client

import java.time.Duration

import io.rsocket.Payload
import io.rsocket.akka.client.ClientWiring
import io.rsocket.akka.server.AkkaToRSocket
import io.rsocket.test.PingClient
import io.rsocket.util.DefaultPayload
import reactor.core.publisher.Mono

import scala.compat.java8.FutureConverters.FutureOps

object ClientMain {
  def main(args: Array[String]): Unit = {
    val wiring = new ClientWiring()
    import wiring._
    val socketF = wiring.socket()

    def helloClient(): Unit = {
      socketF.foreach { socket =>
        socket.requestResponse(DefaultPayload.create("mushtaq")).foreach(x => println(x.getDataUtf8))
      }
    }

    def pingClient(): Payload = {
      val pingClient = new PingClient(Mono.fromFuture(socketF.toJava.toCompletableFuture).map(new AkkaToRSocket(_)))

      val recorder = pingClient.startTracker(Duration.ofSeconds(1))
      val count    = 1000000000

      pingClient
        .startPingPong(count, recorder)
        .doOnTerminate(() => System.out.println("Sent " + count + " messages."))
        .blockLast
    }

    helloClient()
//    pingClient()
  }
}
