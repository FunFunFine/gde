package io.funfunfine.gde.algebras.repositories

import io.funfunfine.gde.domain.{Path, Route, Tram}

trait RouteRepositoryAlgebra[F[_]] {
  def addTram(path: Path)(tram: Tram): F[Unit]
  def getTrams(path: Path): F[Option[Route]]
  def addRoute(route: Route): F[Unit]
}
