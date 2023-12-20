package com.art.collection.adapters.infrastructure.repository

import cats.effect.Async

object RepositoryHelper {
  def unwrapOptionToF[F[_]: Async, A](opt: Option[A], errorMsg: String): F[A] = opt match {
    case Some(value) => Async[F].pure(value)
    case None        => Async[F].raiseError(new NoSuchElementException(errorMsg))
  }
}
