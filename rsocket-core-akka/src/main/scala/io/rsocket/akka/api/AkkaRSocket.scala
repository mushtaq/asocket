package io.rsocket.akka.api

import akka.{Done, NotUsed}
import akka.stream.scaladsl.Source
import io.rsocket.Payload

import scala.concurrent.Future

trait AkkaRSocket {
  def fireAndForget(payload: Payload): Future[Done]
  def requestResponse(payload: Payload): Future[Payload]
  def requestStream(payload: Payload): Source[Payload, NotUsed]
  def requestChannel(payloads: Source[Payload, NotUsed]): Source[Payload, NotUsed]
  def metadataPush(payload: Payload): Future[Done]
  def dispose(): Unit
  def isDisposed: Boolean
  def onClose: Future[Done]
}
