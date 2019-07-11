package com.thoughtworks.demo.client

import io.rsocket.transport.ClientTransport
import io.rsocket.{RSocket, RSocketFactory}
import reactor.core.publisher.Mono

class RSocketClientFactory(clientTransport: ClientTransport) {
  val client: Mono[RSocket] = RSocketFactory.connect
    .frameDecoder(_.retain)
    .transport(clientTransport)
    .start
}
