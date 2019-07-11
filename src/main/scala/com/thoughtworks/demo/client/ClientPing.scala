package com.thoughtworks.demo.client

import java.time.Duration

import io.rsocket.RSocketFactory
import io.rsocket.test.PingClient

object ClientPing {
  def main(args: Array[String]): Unit = {
    val clientTransportFactory = new ClientTransportFactory

    val client = RSocketFactory.connect
      .frameDecoder(_.retain)
      .transport(clientTransportFactory.Tcp)
      .start

    val pingClient = new PingClient(client)

    val recorder = pingClient.startTracker(Duration.ofSeconds(1))
    val count    = 1000000000

    pingClient
      .startPingPong(count, recorder)
      .doOnTerminate(() => System.out.println("Sent " + count + " messages."))
      .blockLast
  }
}
