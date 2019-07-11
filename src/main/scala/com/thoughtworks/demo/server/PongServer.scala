package com.thoughtworks.demo.server

import io.rsocket.RSocketFactory

object PongServer {
  def main(args: Array[String]): Unit = {
    val serverTransportFactory = new ServerTransportFactory

    RSocketFactory.receive
      .frameDecoder(_.retain)
      .acceptor(new PingHandler())
      .transport(serverTransportFactory.Tcp)
      .start
      .block
      .onClose
      .block
  }
}
