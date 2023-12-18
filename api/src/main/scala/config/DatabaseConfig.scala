package config

import cats.effect.{IO, Resource}
import com.typesafe.config.{Config, ConfigFactory}
import net.ceedubs.ficus.readers.ValueReader
import net.ceedubs.ficus.Ficus.*
import doobie.Transactor
object DatabaseConfig {
  case class Database(url: String, user: String, password: String, driver: String)

  implicit val databaseConfigReader: ValueReader[Database] = new ValueReader[Database] {
    def read(config: Config, path: String): Database = {
      val dbConfig = config.getConfig(path)
      Database(
        url = dbConfig.getString("url"),
        user = dbConfig.getString("user"),
        password = dbConfig.getString("password"),
        driver = dbConfig.getString("driver")
      )
    }
  }
    def loadConfig: IO[Database] = IO {
      val config = ConfigFactory.load()
      config.as[Database]("db")
    }

    def transactor: Resource[IO, Transactor[IO]] = Resource.eval(loadConfig.map { dbConfig =>
      Transactor.fromDriverManager[IO](
        dbConfig.driver,
        dbConfig.url,
        dbConfig.user,
        dbConfig.password,
        logHandler = None,
      )
    })
}
