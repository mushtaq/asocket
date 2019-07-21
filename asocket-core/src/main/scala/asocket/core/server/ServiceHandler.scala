package asocket.core.server

import asocket.core.api.ASocket
import asocket.core.extensions.ARConverters
import io.rsocket._
import reactor.core.publisher.Mono

class ServiceHandler(socket: ASocket, converters: ARConverters) extends SocketAcceptor {
  override def accept(setup: ConnectionSetupPayload, sendingSocket: RSocket): Mono[RSocket] = {
    Mono.just(new AToRSocket(socket, converters))
  }
}
