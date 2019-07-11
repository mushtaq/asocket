package com.thoughtworks.demo.server

import io.rsocket.{Closeable, RSocketFactory, SocketAcceptor}
import io.rsocket.transport.ServerTransport

class RSocketServer(serverTransport: ServerTransport[_ <: Closeable], socketAcceptor: SocketAcceptor) {
  def start(): Unit = {
    RSocketFactory.receive
      .frameDecoder(_.retain)
      .acceptor(socketAcceptor)
      .transport(serverTransport)
      .start
      .block
      .onClose
      .block
  }
}
