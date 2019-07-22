package asocket.core.api

import akka.stream.scaladsl.Source
import io.rsocket.Payload

import scala.concurrent.{ExecutionContext, Future}

trait ToPayload[T] {
  def toPayload(input: T): Payload
}

object ToPayload {
  implicit class RichInputTo[T: ToPayload](input: T) {
    def payload: Payload = implicitly[ToPayload[T]].toPayload(input)
  }

  implicit class RichFutureTo[T: ToPayload](input: Future[T]) {
    def payload(implicit ec: ExecutionContext): Future[Payload] = input.map(_.payload)
  }

  implicit class RichSourceTo[T: ToPayload, Mat](input: Source[T, Mat]) {
    def payload: Source[Payload, Mat] = input.map(_.payload)
  }
}
