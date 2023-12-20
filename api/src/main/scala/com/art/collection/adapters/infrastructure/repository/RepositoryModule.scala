package com.art.collection.adapters.infrastructure.repository

import cats.effect.Async
import doobie.util.transactor.Transactor

trait Repository[F[_], Entity, Id] {
  def create(entity: Entity): F[Entity]

  def update(entity: Entity): F[Entity]

  def get(id: Id): F[Option[Entity]]

  def delete(id: Id): F[Boolean]

//  def find(criteria: Criteria): F[List[Entity]]
}

trait RepositoryModule[F[_]] {
  def repositoryFor[A, Id](using ev: Repository[F, A, Id]): Repository[F, A, Id]
}

class DefaultRepositoryModule[F[_]: Async](xa: Transactor[F]) extends RepositoryModule[F] {
  override def repositoryFor[A, Id](using ev: Repository[F, A, Id]): Repository[F, A, Id] = ev
}
