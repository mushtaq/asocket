package io.rsocket.akka.client

import akka.stream.Materializer
import akka.stream.scaladsl.{Sink, Source}
import akka.{Done, NotUsed}
import io.rsocket.akka.api.AkkaRSocket
import io.rsocket.{Payload, RSocket}
import org.reactivestreams.Publisher

import scala.compat.java8.FutureConverters.CompletionStageOps
import scala.concurrent.{ExecutionContext, Future}

class RToAkkaSocket(socket: RSocket)(implicit mat: Materializer, ec: ExecutionContext) extends AkkaRSocket {
  implicit class RichSource[T, Mat](source: Source[T, Mat]) {
    def toPublisher: Publisher[T] = source.runWith(Sink.asPublisher(false))
  }

  override def fireAndForget(payload: Payload): Future[Done]             = socket.fireAndForget(payload).toFuture.toScala.map(_ => Done)
  override def requestResponse(payload: Payload): Future[Payload]        = socket.requestResponse(payload).toFuture.toScala
  override def requestStream(payload: Payload): Source[Payload, NotUsed] = Source.fromPublisher(socket.requestStream(payload))
  override def requestChannel(payloads: Source[Payload, NotUsed]): Source[Payload, NotUsed] =
    Source.fromPublisher(socket.requestChannel(payloads.toPublisher))

  override def metadataPush(payload: Payload): Future[Done] = socket.metadataPush(payload).toFuture.toScala.map(_ => Done)
  override def dispose(): Unit                              = socket.dispose()
  override def isDisposed: Boolean                          = socket.isDisposed
  override def onClose: Future[Done]                        = socket.onClose.toFuture.toScala.map(_ => Done)
}
