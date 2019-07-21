package asocket.core.extensions

import akka.{Done, NotUsed}
import akka.stream.Materializer
import akka.stream.scaladsl.{Sink, Source}
import org.reactivestreams.Publisher
import reactor.core.publisher.{Flux, Mono}

import scala.compat.java8.FutureConverters.FutureOps
import scala.concurrent.{ExecutionContext, Future}
import scala.compat.java8.FutureConverters.CompletionStageOps

class ARConverters(implicit mat: Materializer, ec: ExecutionContext) {
  implicit class RichFuture[T](future: Future[T]) {
    def toMono: Mono[T]        = Mono.fromFuture(future.toJava.toCompletableFuture)
    def toVoidMono: Mono[Void] = future.map(_ => null.asInstanceOf[Void]).toMono
  }
  implicit class RichMono[T](mono: Mono[T]) {
    def toScala: Future[T]                        = mono.toFuture.toScala
    def toDoneScala: Future[Done]                 = mono.mapS(_ => Done)
    def mapS[S](f: T => S): Future[S]             = mono.toScala.map(f)
    def flatMapS[S](f: T => Future[S]): Future[S] = mono.toScala.flatMap(f)
  }
  implicit class RichSource[T, Mat](source: Source[T, Mat]) {
    def toFlux: Flux[T]           = Flux.from(source.toPublisher)
    def toPublisher: Publisher[T] = source.runWith(Sink.asPublisher(false))
  }
  implicit class RichPublisher[T](publisher: Publisher[T]) {
    def toSource: Source[T, NotUsed] = Source.fromPublisher(publisher)
  }
}
