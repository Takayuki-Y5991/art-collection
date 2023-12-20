package com.art.collection.adapters.infrastructure.repository

import cats.effect.Async
import cats.implicits.*
import doobie.implicits.toSqlInterpolator
import doobie.util.transactor.Transactor
import doobie.postgres.implicits.*
import doobie.implicits.*
import doobie.util.{Get, Put}

import java.time.LocalDateTime
import java.util.UUID
import com.art.collection.adapters.infrastructure.repository.RepositoryHelper.unwrapOptionToF
enum WorkVisibility:
  case Public, Private

object WorkVisibility {
  given Get[WorkVisibility] = Get[String].temap {
    case "Public"  => Right(WorkVisibility.Public)
    case "Private" => Right(WorkVisibility.Private)
    case other     => Left(s"Invalid work visibility: $other")
  }

  given Put[WorkVisibility] = Put[String].tcontramap {
    case WorkVisibility.Public  => "Public"
    case WorkVisibility.Private => "Private"
  }
}

case class Work(
    id: UUID,
    title: String,
    creator: String,
    description: Option[String],
    workVisibility: WorkVisibility,
    isAiLearning: Boolean,
    createdAt: LocalDateTime
)

class WorkRepository[F[_]: Async](xa: Transactor[F]) extends Repository[F, Work, UUID] {
  override def create(work: Work): F[Work] =
    for {
      id <- sql"""
                INSERT INTO works (id, title, creator, description, work_visibility, is_ai_learning, created_at)
                VALUES (${work.id}, ${work.title}, ${work.creator}, ${work.description}, ${work.workVisibility}, ${work.isAiLearning}, ${work.createdAt})
        """.update
        .withUniqueGeneratedKeys[UUID]("id")
        .transact(xa)
      e <- get(work.id)
      t <- unwrapOptionToF(e, "Work not found after creation.")
    } yield t

  override def update(work: Work): F[Work] =
    for {
      _ <- sql"""
          UPDATE works SET
            title = ${work.title},
            creator = ${work.creator},
            description = ${work.description},
            work_visibility = ${work.workVisibility},
            is_ai_learning = ${work.isAiLearning},
            created_at = ${work.createdAt}
          WHERE id = ${work.id}
      """.update.run.transact(xa)
      e <- get(work.id)
      t <- unwrapOptionToF(e, "Work not found after creation.")
    } yield t

  override def get(id: UUID): F[Option[Work]] =
    sql"SELECT id, title, creator, description, work_visibility, is_ai_learning, created_at FROM works WHERE id = $id"
      .query[Work]
      .option
      .transact(xa)

  override def delete(id: UUID): F[Boolean] =
    sql"DELETE FROM works WHERE id = $id".update.run
      .transact(xa)
      .map(_ > 0)
}
