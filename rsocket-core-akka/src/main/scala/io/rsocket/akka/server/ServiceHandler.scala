package io.rsocket.akka.server

import akka.stream.Materializer
import io.rsocket._
import io.rsocket.akka.api.AkkaRSocket
import reactor.core.publisher.Mono

import scala.concurrent.ExecutionContext

class ServiceHandler(socket: AkkaRSocket)(implicit mat: Materializer, ec: ExecutionContext) extends SocketAcceptor {
  override def accept(setup: ConnectionSetupPayload, sendingSocket: RSocket): Mono[RSocket] = {
    Mono.just(new AkkaToRSocket(socket))
  }
}
