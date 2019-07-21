package com.thoughtworks.framework.client

import akka.actor.ActorSystem
import akka.stream.{ActorMaterializer, Materializer}
import com.thoughtworks.framework.api.AkkaRSocket
import io.rsocket.transport.akka.client.TcpClientTransport

import scala.concurrent.{ExecutionContext, Future}

class ClientWiring {
  implicit val system: ActorSystem  = ActorSystem("client")
  implicit val mat: Materializer    = ActorMaterializer()
  implicit val ec: ExecutionContext = system.dispatcher

  def socket(): Future[AkkaRSocket] = {
    val Tcp = new TcpClientTransport("localhost", 7878)
    //    val WebSocket = new WebsocketClientTransport(WebSocketRequest.fromTargetUriString(s"ws://localhost:7879"))
    val rSocketClientFactory = new RSocketClientFactory
    rSocketClientFactory.client(Tcp)
  }
}
