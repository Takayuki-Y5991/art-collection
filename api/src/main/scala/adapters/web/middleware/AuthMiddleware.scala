package adapters.web.middleware

import cats.data._
import org.http4s._
import org.http4s.headers.Authorization
import cats.effect._

object AuthMiddleware {
  def authenticationMiddleware (service:  HttpRoutes[IO]): HttpRoutes[IO] = Kleisli {
    (req: Request[IO]) =>  {
      val result = extractToken(req)
        .flatMap(verifyToken)
        .flatMap {
          case true => service(req).value
          case false => IO.pure(Some(Response[IO](Status.Forbidden)))
        }
      OptionT(result)
    }
  }
  private def extractToken(req: Request[IO]): IO[String] =
    req.headers.get[Authorization] match {
      case Some(Authorization(Credentials.Token(AuthScheme.Bearer, token))) => IO.pure(token)
      case _ => IO.raiseError(new Exception("Authorization token not found"))
    }
  // TODO: fix verifyTokens
  private def verifyToken(token: String): IO[Boolean] = IO.pure(token.nonEmpty)

}

