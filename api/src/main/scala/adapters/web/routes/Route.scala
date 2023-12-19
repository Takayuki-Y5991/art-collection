package adapters.web.routes

import adapters.web.handler.Health.healthHandler
import cats.effect.kernel.Async
import org.http4s.HttpApp
import org.http4s.server.Router

object Route {
  def httpApp[F[_]: Async](): HttpApp[F] = {
    Router("/health" ->  healthHandler).orNotFound
  }
}
