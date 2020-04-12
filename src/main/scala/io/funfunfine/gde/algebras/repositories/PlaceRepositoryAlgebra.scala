package io.funfunfine.gde.algebras.repositories

import io.funfunfine.gde.domain.{Place, PlaceId}

trait PlaceRepositoryAlgebra[F[_]] {
  def save(place: Place): F[Unit]
  def get(id: PlaceId): F[Option[Place]]
}
