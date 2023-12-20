package com.art.collection.adapter.web.handler

import cats.effect.IO
import com.art.collection.adapters.web.handler.Health
import munit.CatsEffectSuite
import org.http4s.dsl.io.GET
import org.http4s.{Request, Status}
import org.http4s.implicits.uri

class HealthHandlerTest extends CatsEffectSuite {

  private[this] val healthService = Health.healthHandler[IO]

  test("Health check endpoint return OK") {
    val request = Request[IO](GET, uri"/health")
    val response = healthService.orNotFound.run(request)

    assertIO(response.map(_.status), Status.Ok)
  }
}
