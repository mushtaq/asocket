package com.thoughtworks.demo.client

import akka.actor.ActorSystem
import akka.http.scaladsl.model.ws.WebSocketRequest
import akka.stream.{ActorMaterializer, Materializer}
import io.rsocket.transport.akka.client.{TcpClientTransport, WebsocketClientTransport}

class ClientTransportFactory {
  implicit val system: ActorSystem = ActorSystem.create
  implicit val mat: Materializer   = ActorMaterializer()

  val Tcp       = new TcpClientTransport("localhost", 7878)
  val WebSocket = new WebsocketClientTransport(WebSocketRequest.fromTargetUriString(s"ws://localhost:7879"))
}
