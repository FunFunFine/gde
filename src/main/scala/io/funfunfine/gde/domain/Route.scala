package io.funfunfine.gde.domain

final case class Route(path: Path, trams: List[Tram])

/** Serves as the Id of the [[io.funfunfine.gde.domain.Route]]*/
final case class Path(from: PlaceId, to: PlaceId)
