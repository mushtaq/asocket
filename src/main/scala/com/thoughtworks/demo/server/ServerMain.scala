package com.thoughtworks.demo.server

object ServerMain {
  def main(args: Array[String]): Unit = {
    val serverTransportFactory = new ServerTransportFactory
    val pingHandler            = new PingHandler()
    val rSocketServer          = new RSocketServer(serverTransportFactory.Tcp, pingHandler)
    rSocketServer.start()
  }
}
