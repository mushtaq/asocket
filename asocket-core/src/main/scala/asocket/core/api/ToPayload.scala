package asocket.core.api

import akka.stream.scaladsl.Source
import io.rsocket.Payload

import scala.concurrent.{ExecutionContext, Future}

trait ToPayload[T] {
  def toPayload(input: T): Payload
}

object ToPayload {
  implicit class RichInputTo[T](input: T) {
    def toPayload[S >: T: ToPayload]: Payload = implicitly[ToPayload[S]].toPayload(input)
  }

  implicit class RichFutureTo[T](input: Future[T]) {
    def toPayloadF[S >: T: ToPayload](implicit ec: ExecutionContext): Future[Payload] = input.map(_.toPayload[S])
  }

  implicit class RichSourceTo[T, Mat](input: Source[T, Mat]) {
    def toPayloadS[S >: T: ToPayload]: Source[Payload, Mat] = input.map(_.toPayload[S])
  }
}
