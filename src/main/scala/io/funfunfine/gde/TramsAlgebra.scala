package io.funfunfine.gde

import cats.{ Applicative, FlatMap, Monad, Parallel }
import cats.implicits._
import io.circe.{ Encoder, Json }
import io.funfunfine.gde.domain.{ Destination, Place, Tram, User, UserId, UserRepositoryAlgebra, WhereIsTramAlgebra }
import org.http4s.EntityEncoder
import org.http4s.circe._

trait TramsAlgebra[F[_]] {

  def findTram(userId: UserId,
               from: Destination,
               to: Destination): F[Option[List[(Place, Tram)]]]
}

class TramsInterpreter[F[_]: Monad: Parallel](userRepo: UserRepositoryAlgebra[F],
                                              whereIsTram: WhereIsTramAlgebra[F])
    extends TramsAlgebra[F] {

  override def findTram(userId: UserId,
                        fromDestination: Destination,
                        toDestination: Destination): F[Option[List[(Place, Tram)]]] =
    for {
      User(_, places, routes) <- userRepo.get(userId)
      trams <- places
                .get(fromDestination)
                .traverse(
                  places =>
                    places.parFlatTraverse(
                      from => whereIsTram.findTram(from).nested.map(from -> _).value
                    )
                )

      arriving = (places.get(toDestination), trams).tupled.map {
        case (toPlaces, ts) =>
          for {
            (from, _) <- ts
            to        <- toPlaces
            rs = routes.get(from, to)
            res <- ts.filter {
                    case (_, tram) => rs.exists(_.contains(tram))
                  }
          } yield res

      }
    } yield arriving
}

object TramsAlgebra {
  implicit def apply[F[_]](implicit ev: TramsAlgebra[F]): TramsAlgebra[F] = ev
}
