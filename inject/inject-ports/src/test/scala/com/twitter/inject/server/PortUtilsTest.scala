package com.twitter.inject.server

import com.twitter.finagle.ListeningServer
import com.twitter.inject.Test
import com.twitter.util.Awaitable.CanAwait
import com.twitter.util.{Time, Duration, Future}
import java.net.InetSocketAddress

class PortUtilsTest extends Test {

  test("PortUtils#getPort for ListeningServer") {
    val server = new ListeningServer {

      /**
       * The address to which this server is bound.
       */
      override def boundAddress = new InetSocketAddress(9999)

      override protected def closeServer(deadline: Time): Future[Unit] = Future.Done

      /**
       * Support for `Await.result`. The use of the implicit permit is an
       * access control mechanism: only `Await.result` may call this
       * method.
       */
      override def result(timeout: Duration)(implicit permit: CanAwait) = ???

      /**
       * Is this Awaitable ready? In other words: would calling
       * [[com.twitter.util.Awaitable.ready Awaitable.ready]] block?
       */
      override def isReady(implicit permit: CanAwait) = ???

      /**
       * Support for `Await.ready`. The use of the implicit permit is an
       * access control mechanism: only `Await.ready` may call this
       * method.
       */
      override def ready(timeout: Duration)(implicit permit: CanAwait) = ???
    }

    try {
      PortUtils.getPort(server) should be(9999)
    } finally {
      server.close()
    }
  }
}
