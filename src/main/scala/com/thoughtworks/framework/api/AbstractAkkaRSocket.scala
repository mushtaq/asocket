package com.thoughtworks.framework.api

import akka.stream.scaladsl.Source
import akka.{Done, NotUsed}
import io.rsocket.Payload

import scala.concurrent.{Future, Promise}

abstract class AbstractAkkaRSocket extends AkkaRSocket {
  final private val promisedDone: Promise[Done] = Promise()

  override def fireAndForget(payload: Payload): Future[Done] = {
    payload.release
    Future.failed(new UnsupportedOperationException("Fire and forget not implemented."))
  }

  override def requestResponse(payload: Payload): Future[Payload] = {
    payload.release
    Future.failed(new UnsupportedOperationException("Request-Response not implemented."))
  }

  override def requestStream(payload: Payload): Source[Payload, NotUsed] = {
    payload.release
    Source.failed(new UnsupportedOperationException("Request-Stream not implemented."))
  }

  override def requestChannel(payloads: Source[Payload, NotUsed]): Source[Payload, NotUsed] =
    Source.failed(new UnsupportedOperationException("Request-Channel not implemented."))

  override def metadataPush(payload: Payload): Future[Done] = {
    payload.release
    Future.failed(new UnsupportedOperationException("Metadata-Push not implemented."))
  }

  override def dispose(): Unit = {
    promisedDone.trySuccess(Done)
  }

  override def isDisposed: Boolean = promisedDone.isCompleted

  override def onClose: Future[Done] = promisedDone.future
}
