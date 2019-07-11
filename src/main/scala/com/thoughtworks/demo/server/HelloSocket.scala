package com.thoughtworks.demo.server

import io.rsocket._
import io.rsocket.util.DefaultPayload
import reactor.core.publisher.Mono

class HelloSocket extends AbstractRSocket {
  override def requestResponse(payload: Payload): Mono[Payload] = {
    Mono.just(DefaultPayload.create(s"Hello ${payload.getDataUtf8}"))
  }
}
