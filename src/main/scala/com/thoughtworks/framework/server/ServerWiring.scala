package com.thoughtworks.framework.server

import io.rsocket.SocketAcceptor

class ServerWiring {
  def start(socketAcceptor: SocketAcceptor): Unit = {
    val serverTransportFactory = new ServerTransportFactory
    val rSocketServer          = new RSocketServer(serverTransportFactory.Tcp, socketAcceptor)
    rSocketServer.start()
  }
}
