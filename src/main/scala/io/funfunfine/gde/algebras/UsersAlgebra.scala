package io.funfunfine.gde.algebras

import io.funfunfine.gde.domain.{Destination, PlaceId, Tram, UserId}

trait UsersAlgebra[F[_]]{
  def register(userId: UserId): F[Unit]
  def addDestination(id: UserId, nickname: Destination, place: PlaceId): F[Unit]
  def addRoute(id: UserId, from: Destination, to: Destination, tram: Tram): F[Unit]
}

object UsersAlgebra {
  def apply[F[_]](implicit ev: UsersAlgebra[F]): UsersAlgebra[F] = ev

}