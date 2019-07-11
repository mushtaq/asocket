package com.thoughtworks.framework.server

import io.rsocket._
import reactor.core.publisher.Mono

class ServiceHandler(rSocket: RSocket) extends SocketAcceptor {
  override def accept(setup: ConnectionSetupPayload, sendingSocket: RSocket): Mono[RSocket] = {
    Mono.just(rSocket)
  }
}
