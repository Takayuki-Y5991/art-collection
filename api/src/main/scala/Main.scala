import adapters.web.routes.Route.httpApp
import cats.effect.{ExitCode, IO, IOApp}
import com.comcast.ip4s.{ipv4, port}
import config.DatabaseConfig.{Database, loadConfig}
import config.FlywayConfig.migrate
import org.http4s.ember.server.EmberServerBuilder
import org.typelevel.log4cats.LoggerFactory
import org.typelevel.log4cats.slf4j.Slf4jFactory

object Main extends IOApp {

  implicit val loggerFactory: LoggerFactory[IO] = Slf4jFactory.create[IO]
  def run(args: List[String]): IO[ExitCode] = {
//    loadConfig.flatMap { config =>
//      IO(migrate(config))
//    }.as(ExitCode.Success)

    EmberServerBuilder
      .default[IO]
      .withHost(ipv4"0.0.0.0")
      .withPort(port"8080")
      .withHttpApp(httpApp[IO]())
      .build
      .use(_ => IO.never)
      .as(ExitCode.Success)
  }
}
