package asocket.core.server

import akka.stream.Materializer
import asocket.core.api.ASocket
import io.rsocket._
import reactor.core.publisher.Mono

import scala.concurrent.ExecutionContext

class ServiceHandler(socket: ASocket)(implicit mat: Materializer, ec: ExecutionContext) extends SocketAcceptor {
  override def accept(setup: ConnectionSetupPayload, sendingSocket: RSocket): Mono[RSocket] = {
    Mono.just(new AToRSocket(socket))
  }
}
