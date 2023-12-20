package com.art.collection.config

import org.flywaydb.core.Flyway
import com.art.collection.config.DatabaseConfig.Database

object FlywayConfig {
  case class FlywaySetting(location: String, schemas: String)

  def migrate(dbConfig: Database): Unit = {
    val flyway = Flyway.configure()
      .dataSource(dbConfig.url, dbConfig.user, dbConfig.password)
      .load()

    flyway.migrate()
  }
}
