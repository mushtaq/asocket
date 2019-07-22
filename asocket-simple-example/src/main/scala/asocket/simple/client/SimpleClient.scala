package asocket.simple.client

import asocket.borer.codecs.ASocketCodecs
import asocket.core.api.ASocket
import asocket.core.api.FromPayload.RichFuturePayload
import asocket.core.api.ToPayload.RichInput
import csw.simple.api.{HelloRequest, SimpleApi, SimpleCodecs, SimpleRequest, SquareRequest}

import scala.concurrent.{ExecutionContext, Future}

class SimpleClient(socket: ASocket)(implicit ec: ExecutionContext) extends SimpleApi with SimpleCodecs with ASocketCodecs {
  override def sayHello(name: String): Future[String] = {
    socket.requestResponse(HelloRequest(name).toPayload[SimpleRequest]).toF[String]
  }

  override def square(x: Int): Future[Int] = {
    socket.requestResponse(SquareRequest(9).toPayload[SimpleRequest]).toF[Int]
  }
}
