package com.thoughtworks

import java.time.Duration

import akka.actor.ActorSystem
import akka.stream.{ActorMaterializer, Materializer}
import io.rsocket.RSocketFactory
import io.rsocket.test.PingClient
import io.rsocket.transport.akka.client.TcpClientTransport

object TcpPing {
  def main(args: Array[String]): Unit = {
    implicit val system: ActorSystem = ActorSystem.create
    implicit val mat: Materializer   = ActorMaterializer()

    val transport  = new TcpClientTransport("localhost", 7878)
    val client     = RSocketFactory.connect.frameDecoder(_.retain).transport(transport).start
    val pingClient = new PingClient(client)

    val recorder = pingClient.startTracker(Duration.ofSeconds(1))
    val count    = 1000000000

    pingClient
      .startPingPong(count, recorder)
      .doOnTerminate(() => System.out.println("Sent " + count + " messages."))
      .blockLast
  }
}
