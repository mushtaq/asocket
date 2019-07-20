package com.thoughtworks.demo.server

import java.util.concurrent.ThreadLocalRandom

import com.thoughtworks.framework.api.AbstractAkkaRSocket
import io.rsocket._
import io.rsocket.util.ByteBufPayload

import scala.concurrent.Future

class PingSocket(data: Array[Byte]) extends AbstractAkkaRSocket {
  ThreadLocalRandom.current.nextBytes(data)
  val pong: Payload = ByteBufPayload.create(data)

  def this() = this(new Array[Byte](1024))

  override def requestResponse(payload: Payload): Future[Payload] = {
    payload.release
    Future.successful(pong.retain)
  }
}
