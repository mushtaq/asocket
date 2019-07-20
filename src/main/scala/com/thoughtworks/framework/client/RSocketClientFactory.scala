package com.thoughtworks.framework.client

import akka.stream.Materializer
import com.thoughtworks.framework.api.AkkaRSocket
import io.rsocket.RSocketFactory
import io.rsocket.transport.ClientTransport

import scala.compat.java8.FutureConverters.CompletionStageOps
import scala.concurrent.{ExecutionContext, Future}

class RSocketClientFactory {
  def client(clientTransport: ClientTransport)(implicit mat: Materializer, ec: ExecutionContext): Future[AkkaRSocket] = {
    RSocketFactory.connect
      .frameDecoder(_.retain)
      .transport(clientTransport)
      .start
      .toFuture
      .toScala
      .map(new RToAkkaSocket(_))
  }
}
