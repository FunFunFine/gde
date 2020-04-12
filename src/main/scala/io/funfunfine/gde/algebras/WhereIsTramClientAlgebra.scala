package io.funfunfine.gde.algebras

import eu.timepit.refined.numeric.Positive
import io.funfunfine.gde.domain.{ Place, Tram }

final case class ArrivingTram(tram: Tram,
                              remainingTime: Positive,
                              remainingDistance: Positive)

trait WhereIsTramClientAlgebra[F[_]] {
  def findTram(from: Place): F[List[ArrivingTram]]
}
