package com.thoughtworks.framework.client

import io.rsocket.{RSocket, RSocketFactory}
import reactor.core.publisher.Mono

class RSocketClientFactory {
  val clientTransportFactory = new ClientTransportFactory
  val client: Mono[RSocket] = RSocketFactory.connect
    .frameDecoder(_.retain)
    .transport(clientTransportFactory.Tcp)
    .start
}
