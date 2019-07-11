package com.thoughtworks

import io.rsocket._
import io.rsocket.util.ByteBufPayload
import reactor.core.publisher.Mono
import java.util.concurrent.ThreadLocalRandom

class PingHandler(data: Array[Byte]) extends SocketAcceptor {
  ThreadLocalRandom.current.nextBytes(data)
  val pong: Payload = ByteBufPayload.create(data)

  def this() = this(new Array[Byte](1024))

  override def accept(setup: ConnectionSetupPayload, sendingSocket: RSocket): Mono[RSocket] = {
    val socket = new AbstractRSocket() {
      override def requestResponse(payload: Payload): Mono[Payload] = {
        payload.release
        Mono.just(pong.retain)
      }
    }
    Mono.just(socket)
  }
}
