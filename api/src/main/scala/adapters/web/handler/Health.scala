package adapters.web.handler

import cats.effect.kernel.{Async}
import org.http4s.dsl.impl./
import org.http4s.dsl.io.*
import org.http4s.{HttpRoutes, Response, Status}

object Health {
  def healthHandler[F[_]: Async] = HttpRoutes.of[F] {
    case GET -> Root / "health" =>
      Async[F].pure(Response(Status.Ok).withEntity("ok"))
  }
}
