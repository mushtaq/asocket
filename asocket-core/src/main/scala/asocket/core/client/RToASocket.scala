package asocket.core.client

import akka.stream.scaladsl.Source
import akka.{Done, NotUsed}
import asocket.core.api.ASocket
import asocket.core.extensions.ARConverters
import io.rsocket.{Payload, RSocket}

import scala.concurrent.Future

class RToASocket(socket: RSocket, converters: ARConverters) extends ASocket {
  import converters._

  override def fireAndForget(payload: Payload): Future[Done]             = socket.fireAndForget(payload).toDoneScala
  override def requestResponse(payload: Payload): Future[Payload]        = socket.requestResponse(payload).toScala
  override def requestStream(payload: Payload): Source[Payload, NotUsed] = Source.fromPublisher(socket.requestStream(payload))
  override def requestChannel(payloads: Source[Payload, NotUsed]): Source[Payload, NotUsed] =
    socket.requestChannel(payloads.toPublisher).toSource

  override def metadataPush(payload: Payload): Future[Done] = socket.metadataPush(payload).toDoneScala
  override def dispose(): Unit                              = socket.dispose()
  override def isDisposed: Boolean                          = socket.isDisposed
  override def onClose: Future[Done]                        = socket.onClose.toDoneScala
}
