package io.funfunfine.gde.domain

import cats.data.{NonEmptyList => NEList}
import eu.timepit.refined.api.Refined
import eu.timepit.refined.numeric.Positive
import eu.timepit.refined.string.{Url => URL}
import eu.timepit.refined.types.string.{NonEmptyString => NEString}

final case class User(id: UserId,
                           places: Destination Map List[Place],
                           routes: (Place, Place) Map List[Tram])


final case class Destination(value: NEString)

final case class UserId(value: NEString)

final case class Url(value: String Refined URL)

final case class Place(name: NEString,
                       url: Url,
                       freezeDistance: Positive,
                       trams: NEList[Tram])

final case class Tram(number: Positive)

final case class ArrivingTram(tram: Tram,
                              remainingTime: Positive,
                              remainingDistance: Positive)


sealed trait Response
case class Found(trams: List[ArrivingTram]) extends Response
case object TimedOut extends Response


trait WhereIsTramAlgebra[F[_]] {
  def findTram(from: Place): F[List[ArrivingTram]]
}

trait UserRepositoryAlgebra[F[_]] {
  def save(user: User): F[Unit]
  def get(id: UserId): F[User]
}
trait PlaceRepositoryAlgebra[F[_]]{
  def save(place: Place): F[Unit]
  def get(name: String): F[Place]
}



