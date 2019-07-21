package asocket.core.server

import io.rsocket.transport.ServerTransport
import io.rsocket.{Closeable, RSocketFactory, SocketAcceptor}

class RSocketServer(serverTransport: ServerTransport[_ <: Closeable], socketAcceptor: SocketAcceptor) {
  def start(): Unit = {
    RSocketFactory.receive
      .frameDecoder(_.retain)
      .acceptor(socketAcceptor)
      .transport(serverTransport)
      .start
      .block
      .onClose
      .block
  }
}
