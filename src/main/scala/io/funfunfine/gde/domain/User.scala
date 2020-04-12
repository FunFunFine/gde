package io.funfunfine.gde.domain

import eu.timepit.refined.types.string.{NonEmptyString => NEString}

final case class User(id: UserId, places: Destination Map List[PlaceId])

final case class UserId(value: NEString)

final case class Destination(value: NEString)












