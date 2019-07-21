package com.thoughtworks.demo.server

import io.rsocket._
import io.rsocket.akka.api.AbstractAkkaRSocket
import io.rsocket.util.DefaultPayload

import scala.concurrent.Future

class HelloSocket extends AbstractAkkaRSocket {
  override def requestResponse(payload: Payload): Future[Payload] = {
    Future.successful(DefaultPayload.create(s"Hello ${payload.getDataUtf8}"))
  }
}
