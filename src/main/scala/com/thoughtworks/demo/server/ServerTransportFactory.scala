package com.thoughtworks.demo.server

import akka.actor.ActorSystem
import akka.stream.{ActorMaterializer, Materializer}
import io.rsocket.transport.akka.server.{TcpServerTransport, WebsocketServerTransport}

class ServerTransportFactory {
  implicit val system: ActorSystem = ActorSystem.create
  implicit val mat: Materializer   = ActorMaterializer()

  val Tcp       = new TcpServerTransport("0.0.0.0", 7878)
  val WebSocket = new WebsocketServerTransport("0.0.0.0", 7879)
}
