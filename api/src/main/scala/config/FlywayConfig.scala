package config

import config.DatabaseConfig.Database
import org.flywaydb.core.Flyway

object FlywayConfig {
  case class FlywaySetting(location: String, schemas: String)

  def migrate(dbConfig: Database): Unit = {
    val flyway = Flyway.configure()
      .dataSource(dbConfig.url, dbConfig.user, dbConfig.password)
      .load()

    flyway.migrate()
  }
}
