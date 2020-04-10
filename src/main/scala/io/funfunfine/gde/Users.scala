package io.funfunfine.gde

import cats.Applicative
import cats.effect.Sync
import cats.implicits._
import io.circe.{Decoder, Encoder, HCursor, Json}
import io.circe.generic.semiauto._
import io.funfunfine.gde.domain.{Destination, Place, Tram, UserId}
import org.http4s._
import org.http4s.implicits._
import org.http4s.{EntityDecoder, EntityEncoder, Method, Request, Uri}
import org.http4s.client.Client
import org.http4s.client.dsl.Http4sClientDsl
import org.http4s.Method._
import org.http4s.circe._

trait Users[F[_]]{
  def register(userId: UserId): F[Unit]
  def addDestination(id: UserId, nickname: Destination, place: Place): F[Unit]
  def addRoute(id: UserId, from: Destination, to: Destination, tram: Tram): F[Unit]
}

object Users {
  def apply[F[_]](implicit ev: Users[F]): Users[F] = ev

}