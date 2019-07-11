package com.thoughtworks.demo.client

import com.thoughtworks.framework.client.RSocketClientFactory
import io.rsocket.util.DefaultPayload

object ClientHello {
  def main(args: Array[String]): Unit = {
    val rSocketClientFactory = new RSocketClientFactory
    val socket = rSocketClientFactory.client.block()
    socket.requestResponse(DefaultPayload.create("mushtaq")).subscribe(x => println(x.getDataUtf8))
  }
}
