package com.thoughtworks.demo.client

import java.time.Duration

import io.rsocket.test.PingClient

object ClientPing {
  def main(args: Array[String]): Unit = {
    val clientTransportFactory = new ClientTransportFactory
    val rSocketClientFactory   = new RSocketClientFactory(clientTransportFactory.Tcp)

    val pingClient = new PingClient(rSocketClientFactory.client)

    val recorder = pingClient.startTracker(Duration.ofSeconds(1))
    val count    = 1000000000

    pingClient
      .startPingPong(count, recorder)
      .doOnTerminate(() => System.out.println("Sent " + count + " messages."))
      .blockLast
  }
}
