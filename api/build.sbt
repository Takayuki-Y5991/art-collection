val scala3Version = "3.3.1"

val http4sVersion = "1.0.0-M40"
val doobieVersion = "1.0.0-RC4"
val postgresqlVersion = "42.3.1"
val catsEffectVersion = "3.3.0"
val ficusVersion = "1.5.2"

val flywayVersion = "9.22.3"
val typeLevelVersion = "2.0.0-M1"

lazy val root = project
  .in(file("."))
  .settings(
    name := "api",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,

    // sbt-assembly プラグイン設定
    assembly / mainClass := Some("com.art.collection.Main"),
    assembly / assemblyJarName := "api.jar",
    assembly / assemblyMergeStrategy := {
      case PathList("META-INF", xs@_*) => MergeStrategy.discard
      case _ => MergeStrategy.first
    },

    libraryDependencies ++= Seq(
      "org.http4s" %% "http4s-ember-client" % http4sVersion,
      "org.http4s" %% "http4s-ember-server" % http4sVersion,
      "org.http4s" %% "http4s-circe" % http4sVersion,
      "org.http4s" %% "http4s-dsl" % http4sVersion,
      "org.tpolecat" %% "doobie-core" % doobieVersion,
      "org.tpolecat" %% "doobie-postgres" % doobieVersion,
      "org.tpolecat" %% "doobie-hikari" % doobieVersion,
      "org.postgresql" % "postgresql" % postgresqlVersion,
      "org.typelevel" %% "cats-effect" % catsEffectVersion,
      "com.iheart" %% "ficus" %  ficusVersion,
      "org.flywaydb" % "flyway-core" % flywayVersion,
      "org.scalameta" %% "munit" % "0.7.29" % Test,
      "org.typelevel" %% "munit-cats-effect" % typeLevelVersion % Test,
      "org.tpolecat" %% "doobie-scalatest" % doobieVersion
    )
  )
