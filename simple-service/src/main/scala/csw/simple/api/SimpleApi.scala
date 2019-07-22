package csw.simple.api

import scala.concurrent.Future

trait SimpleApi {
  def sayHello(name: String): Future[String]
  def square(x: Int): Future[Int]
}
