package adapters.web.middleware

import cats.effect._
import cats.effect.unsafe.IORuntime
import org.http4s._
import org.http4s.dsl.io._
import org.http4s.headers.Authorization
import org.http4s.implicits._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class AuthMiddlewareTest extends AnyFlatSpec with Matchers {

  implicit val runtime: IORuntime = cats.effect.unsafe.IORuntime.global

  "AuthMiddleware" should "If the token is valid" in {
    val service = HttpRoutes.of[IO] {
      case _ => Ok()
    }
    val request = Request[IO](Method.GET, uri"/").putHeaders(Authorization(Credentials.Token(AuthScheme.Bearer, "validToken")))
    val response = AuthMiddleware.authenticationMiddleware(service).orNotFound(request).unsafeRunSync()

    response.status should be (Status.Ok)
  }

  it should "Returns Forbidden if headers do not contain authentication" in {
    val service = HttpRoutes.of[IO] {
      case _ => Ok()
    }
    val request = Request[IO](Method.GET, uri"/")
    val response = AuthMiddleware.authenticationMiddleware(service).orNotFound(request).unsafeRunSync()

    response.status should be (Status.Forbidden)
  }
}
