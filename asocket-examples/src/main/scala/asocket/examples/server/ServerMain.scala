package asocket.examples.server

import asocket.server.ServerWiring

object ServerMain {
//  val socket = new PingSocket()
  val socket = new HelloSocket()

  def main(args: Array[String]): Unit = {
    new ServerWiring().start(socket)
  }
}
