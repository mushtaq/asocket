package asocket.core.server

import asocket.core.extensions.ARConverters
import io.rsocket.transport.ServerTransport
import io.rsocket.{Closeable, RSocketFactory, SocketAcceptor}

import scala.concurrent.Future

class ASocketServerHelper(serverTransport: ServerTransport[_ <: Closeable], socketAcceptor: SocketAcceptor) {
  def start(converters: ARConverters): Future[SocketBinding] = {
    import converters._
    RSocketFactory.receive
      .frameDecoder(_.retain)
      .acceptor(socketAcceptor)
      .transport(serverTransport)
      .start
      .mapS(SocketBinding.from(_, converters))
  }
}
