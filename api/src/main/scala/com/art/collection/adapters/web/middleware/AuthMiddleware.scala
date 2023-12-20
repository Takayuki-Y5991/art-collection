package com.art.collection.adapters.web.middleware

import cats.data._
import org.http4s._
import org.http4s.headers.Authorization
import cats.effect._

object AuthMiddleware {
  def authenticationMiddleware (service:  HttpRoutes[IO]): HttpRoutes[IO] = Kleisli {
    (req: Request[IO]) =>  {
      val result = extractToken(req)
        .flatMap {
          case Some(token) => verifyToken(token).flatMap {
            case true => service(req).value
            case false => IO.pure(Some(Response[IO](Status.Forbidden)))
          }
          case None => IO.pure(Some(Response[IO](Status.Forbidden)))
        }
      OptionT(result)
    }
  }
  private def extractToken(req: Request[IO]): IO[Option[String]] =
    IO(req.headers.get[Authorization].collect {
      case Authorization(Credentials.Token(AuthScheme.Bearer, token)) => token
    })
  // TODO: fix verifyTokens
  private def verifyToken(token: String): IO[Boolean] = IO.pure(token.nonEmpty)

}

