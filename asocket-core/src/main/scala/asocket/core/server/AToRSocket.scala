package asocket.core.server

import asocket.core.api.ASocket
import asocket.core.extensions.ARConverters
import io.rsocket.{Payload, RSocket}
import org.reactivestreams.Publisher
import reactor.core.publisher.{Flux, Mono}

class AToRSocket(akkaRSocket: ASocket, converters: ARConverters) extends RSocket {
  import converters._

  override def fireAndForget(payload: Payload): Mono[Void]                 = akkaRSocket.fireAndForget(payload).toVoidMono
  override def requestResponse(payload: Payload): Mono[Payload]            = akkaRSocket.requestResponse(payload).toMono
  override def requestStream(payload: Payload): Flux[Payload]              = akkaRSocket.requestStream(payload).toFlux
  override def requestChannel(payloads: Publisher[Payload]): Flux[Payload] = akkaRSocket.requestChannel(payloads.toSource).toFlux
  override def metadataPush(payload: Payload): Mono[Void]                  = akkaRSocket.metadataPush(payload).toVoidMono
  override def onClose(): Mono[Void]                                       = akkaRSocket.onClose.toVoidMono
  override def dispose(): Unit                                             = akkaRSocket.dispose()
}
