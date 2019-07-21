package com.thoughtworks.framework.server

import akka.{Done, NotUsed}
import akka.stream.Materializer
import akka.stream.scaladsl.{Sink, Source}
import com.thoughtworks.framework.api.AkkaRSocket
import io.rsocket.{Payload, RSocket}
import org.reactivestreams.Publisher
import reactor.core.publisher.{Flux, Mono}

import scala.compat.java8.FutureConverters.FutureOps
import scala.concurrent.{ExecutionContext, Future}

class AkkaToRSocket(akkaRSocket: AkkaRSocket)(implicit mat: Materializer, ec: ExecutionContext) extends RSocket {
  implicit class RichFuture[T](future: Future[T]) {
    def toMono: Mono[T]        = Mono.fromFuture(future.toJava.toCompletableFuture)
    def toVoidMono: Mono[Void] = Mono.fromFuture(future.map(_ => null).toJava.toCompletableFuture)
  }
  implicit class RichSource[T, Mat](source: Source[T, Mat]) {
    def toFlux: Flux[T] = Flux.from(source.runWith(Sink.asPublisher(false)))
  }
  implicit class RichPublisher[T](publisher: Publisher[T]) {
    def toSource: Source[T, NotUsed] = Source.fromPublisher(publisher)
  }

  override def fireAndForget(payload: Payload): Mono[Void]                 = akkaRSocket.fireAndForget(payload).toVoidMono
  override def requestResponse(payload: Payload): Mono[Payload]            = akkaRSocket.requestResponse(payload).toMono
  override def requestStream(payload: Payload): Flux[Payload]              = akkaRSocket.requestStream(payload).toFlux
  override def requestChannel(payloads: Publisher[Payload]): Flux[Payload] = akkaRSocket.requestChannel(payloads.toSource).toFlux
  override def metadataPush(payload: Payload): Mono[Void]                  = akkaRSocket.metadataPush(payload).toVoidMono
  override def onClose(): Mono[Void]                                       = akkaRSocket.onClose.toVoidMono
  override def dispose(): Unit                                             = akkaRSocket.dispose()
}
