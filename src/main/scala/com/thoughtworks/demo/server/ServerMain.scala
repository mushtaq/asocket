package com.thoughtworks.demo.server

import com.thoughtworks.framework.server.ServerWiring

object ServerMain {

//  val handler = new PingHandler()
  val handler = new HelloHandler()

  def main(args: Array[String]): Unit = {
    new ServerWiring().start(handler)
  }

}
