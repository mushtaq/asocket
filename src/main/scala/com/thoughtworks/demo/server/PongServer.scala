package com.thoughtworks.demo.server

import com.thoughtworks.framework.server.ServerWiring

object PongServer {

  def main(args: Array[String]): Unit = {
    new ServerWiring().start(new PingHandler())
  }

}
