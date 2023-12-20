package com.art.collection.utility

import cats.effect.{IO, Resource}
import com.art.collection.config.DatabaseConfig.Database
import com.typesafe.config.ConfigFactory
import doobie.util.transactor.Transactor
import net.ceedubs.ficus.Ficus.*

object TestDatabaseConfig {
  def loadTestConfig: IO[Database] = IO {
    val config = ConfigFactory.load("application-test.conf")
    config.as[Database]("db")
  }

  def testTransactor: Resource[IO, Transactor[IO]] = Resource.eval(loadTestConfig.map { dbConfig =>
    Transactor.fromDriverManager[IO](
      dbConfig.driver,
      dbConfig.url,
      dbConfig.user,
      dbConfig.password,
      None
    )
  })
}
