package csw.event.impl

import csw.event.api.SimpleApi

import scala.concurrent.Future

class SimpleService extends SimpleApi {
  override def sayHello(name: String): Future[String] = Future.successful(s"Hello $name")
  override def square(x: Int): Future[Int]            = Future.successful(x * x)
}
