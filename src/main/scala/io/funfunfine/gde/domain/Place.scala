package io.funfunfine.gde.domain
import eu.timepit.refined.api.Refined
import eu.timepit.refined.numeric.Positive
import eu.timepit.refined.types.string.{NonEmptyString => NEString}
import eu.timepit.refined.string.{ Url => URL }

final case class PlaceId(value: NEString)
final case class Tram(number: Positive)
final case class Url(value: String Refined URL)

final case class Place(placeId: PlaceId,
                       name: NEString,
                       url: Url,
                       freezeDistance: Option[Positive],
                       trams: List[Tram])