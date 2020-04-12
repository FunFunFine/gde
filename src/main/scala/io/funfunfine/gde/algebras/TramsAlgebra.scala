package io.funfunfine.gde.algebras

import io.funfunfine.gde.domain.{Destination, Place, Tram, UserId}

trait TramsAlgebra[F[_]] {

  def findTram(userId: UserId,
               from: Destination,
               to: Destination): F[Option[List[(Place, Tram)]]]
}

object TramsAlgebra {
  implicit def apply[F[_]](implicit ev: TramsAlgebra[F]): TramsAlgebra[F] = ev
}