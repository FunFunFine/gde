package io.funfunfine.gde.interpreters

import cats.implicits._
import cats.{Monad, MonadError, Parallel}
import io.funfunfine.gde.algebras.repositories.UserRepositoryAlgebra
import io.funfunfine.gde.algebras.{TramsAlgebra, WhereIsTramClientAlgebra}
import io.funfunfine.gde.domain._
import cats.FunctorFilter.Ops
import tofu.syntax.foption._
class TramsInterpreter[F[_]: MonadError[*[_], Throwable]: Parallel](userRepo: UserRepositoryAlgebra[F],
                                              whereIsTram: WhereIsTramClientAlgebra[F])
    extends TramsAlgebra[F] {

  override def findTram(userId: UserId,
                        fromDestination: Destination,
                        toDestination: Destination): F[Option[List[(Place, Tram)]]] =
    for {
      User(_, places) <- userRepo.get(userId).orThrow(new Exception("no user"))
      allTramsFrom <- places
                       .get(fromDestination)
                       .traverse(
                         places =>
                           places.parFlatTraverse(
                             from =>
                               whereIsTram.findTram(from).nested.map(from -> _).value
                           )
                       )

      arrivingTrams = (places.get(toDestination), allTramsFrom).tupled.map {
        case (toPlaces, ts) =>
          for {
            (from, _) <- ts
            to        <- toPlaces
            acceptableRoutes = routes.get(from, to)
            res <- ts.filter {
                    case (_, tram) => acceptableRoutes.exists(_.contains(tram))
                  }
          } yield res

      }
    } yield arrivingTrams
}


