package com.thoughtworks

import akka.actor.ActorSystem
import akka.stream.{ActorMaterializer, Materializer}
import io.rsocket.RSocketFactory
import io.rsocket.transport.akka.server.TcpServerTransport

object TcpPongServer {
  def main(args: Array[String]): Unit = {
    implicit val system: ActorSystem = ActorSystem.create
    implicit val mat: Materializer   = ActorMaterializer()
    val transport                    = new TcpServerTransport("0.0.0.0", 7878)
    RSocketFactory.receive
      .frameDecoder(_.retain)
      .acceptor(new PingHandler())
      .transport(transport)
      .start
      .block
      .onClose
      .block
  }
}
