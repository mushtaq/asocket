package com.thoughtworks.demo.server

import io.rsocket._
import io.rsocket.util.DefaultPayload
import reactor.core.publisher.Mono

class HelloHandler extends SocketAcceptor {
  override def accept(setup: ConnectionSetupPayload, sendingSocket: RSocket): Mono[RSocket] = {
    val socket = new AbstractRSocket() {
      override def requestResponse(payload: Payload): Mono[Payload] = {
        Mono.just(DefaultPayload.create(s"Hello ${payload.getDataUtf8}"))
      }
    }
    Mono.just(socket)
  }
}
