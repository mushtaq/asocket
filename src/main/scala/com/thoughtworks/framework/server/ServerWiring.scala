package com.thoughtworks.framework.server

import io.rsocket.RSocket

class ServerWiring {
  def start(rSocket: RSocket): Unit = {
    val serverTransportFactory = new ServerTransportFactory
    val serviceHandler         = new ServiceHandler(rSocket)
    val rSocketServer          = new RSocketServer(serverTransportFactory.Tcp, serviceHandler)
    rSocketServer.start()
  }
}
