package asocket.core.server

import akka.Done
import asocket.core.extensions.ARConverters
import io.rsocket.Closeable
import scala.concurrent.Future

trait SocketBinding {
  def close(): Future[Done]
}

object SocketBinding {
  def from(closeable: Closeable, converters: ARConverters): SocketBinding = {
    import converters._
    () => closeable.onClose().toDoneScala
  }
}
