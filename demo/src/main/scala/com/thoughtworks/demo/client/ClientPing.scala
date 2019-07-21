package com.thoughtworks.demo.client

import java.time.Duration

import io.rsocket.akka.client.ClientWiring
import io.rsocket.akka.server.AkkaToRSocket
import io.rsocket.test.PingClient
import reactor.core.publisher.Mono

import scala.compat.java8.FutureConverters.FutureOps

object ClientPing {
  def main(args: Array[String]): Unit = {
    val wiring = new ClientWiring()
    import wiring._
    val pingClient = new PingClient(
      Mono.fromFuture(wiring.socket().toJava.toCompletableFuture).map(new AkkaToRSocket(_))
    )

    val recorder = pingClient.startTracker(Duration.ofSeconds(1))
    val count    = 1000000000

    pingClient
      .startPingPong(count, recorder)
      .doOnTerminate(() => System.out.println("Sent " + count + " messages."))
      .blockLast
  }
}
