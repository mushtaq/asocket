package com.thoughtworks.framework.server

import akka.stream.Materializer
import com.thoughtworks.framework.api.AkkaRSocket
import io.rsocket._
import reactor.core.publisher.Mono

import scala.concurrent.ExecutionContext

class ServiceHandler(socket: AkkaRSocket)(implicit mat: Materializer, ec: ExecutionContext) extends SocketAcceptor {
  override def accept(setup: ConnectionSetupPayload, sendingSocket: RSocket): Mono[RSocket] = {
    Mono.just(new AkkaToRSocket(socket))
  }
}
