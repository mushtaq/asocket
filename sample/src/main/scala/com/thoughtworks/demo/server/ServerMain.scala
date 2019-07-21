package com.thoughtworks.demo.server

import io.rsocket.akka.server.ServerWiring

object ServerMain {
//  val socket = new PingSocket()
  val socket = new HelloSocket()

  def main(args: Array[String]): Unit = {
    new ServerWiring().start(socket)
  }
}
