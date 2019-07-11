package com.thoughtworks.demo.server

import java.util.concurrent.ThreadLocalRandom

import io.rsocket._
import io.rsocket.util.ByteBufPayload
import reactor.core.publisher.Mono

class PingSocket(data: Array[Byte]) extends AbstractRSocket {
  ThreadLocalRandom.current.nextBytes(data)
  val pong: Payload = ByteBufPayload.create(data)

  def this() = this(new Array[Byte](1024))

  override def requestResponse(payload: Payload): Mono[Payload] = {
    payload.release
    Mono.just(pong.retain)
  }
}
