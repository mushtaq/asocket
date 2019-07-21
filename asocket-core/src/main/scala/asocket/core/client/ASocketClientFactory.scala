package asocket.core.client

import akka.stream.Materializer
import asocket.core.api.ASocket
import io.rsocket.RSocketFactory
import io.rsocket.transport.ClientTransport

import scala.compat.java8.FutureConverters.CompletionStageOps
import scala.concurrent.{ExecutionContext, Future}

class ASocketClientFactory {
  def client(clientTransport: ClientTransport)(implicit mat: Materializer, ec: ExecutionContext): Future[ASocket] = {
    RSocketFactory.connect
      .frameDecoder(_.retain)
      .transport(clientTransport)
      .start
      .toFuture
      .toScala
      .map(new RToASocket(_))
  }
}
