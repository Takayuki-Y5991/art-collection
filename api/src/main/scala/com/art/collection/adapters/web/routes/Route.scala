package com.art.collection.adapters.web.routes

import cats.effect.kernel.Async
import com.art.collection.adapters.web.handler.Health.healthHandler
import org.http4s.HttpApp
import org.http4s.server.Router

object Route {
  def httpApp[F[_]: Async](): HttpApp[F] = {
    Router("/health" ->  healthHandler).orNotFound
  }
}
