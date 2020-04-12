package io.funfunfine.gde.algebras.repositories

import io.funfunfine.gde.domain.{User, UserId}

trait UserRepositoryAlgebra[F[_]] {
  def save(user: User): F[Unit]
  def get(id: UserId): F[Option[User]]
}
