package io.funfunfine.gde

import cats.effect.Sync
import cats.implicits._
import derevo.circe.magnolia.{decoder, encoder}
import derevo.derive
import eu.timepit.refined.types.string.NonEmptyString
import io.funfunfine.gde.domain.{Destination, UserId}
import org.http4s.{EntityDecoder, HttpRoutes}
import io.circe.syntax._
import org.http4s.circe._
import org.http4s.dsl.Http4sDsl



object GdeRoutes {

  @derive(encoder, decoder)
  case class AddTram(userId: UserId )

  @derive(encoder, decoder)
  case class FindTram(userId: UserId, from: Destination, to: Destination)

  implicit def findTramDecoder[F[_]:Sync]: EntityDecoder[F, FindTram] = jsonOf[FindTram]


  def usersRoutes[F[_]: Sync](users: Users[F]): HttpRoutes[F] = {
    val dsl = new Http4sDsl[F]{}
    import dsl._

    HttpRoutes.of[F] {
      case POST -> Root / "users"/ "register" / id =>
        for {
          userId <- NonEmptyString.from(id).map(UserId).fold(new Exception(_).raiseError[F, UserId],_.pure[F] )
          _ <- users.register(userId)
          resp <- Ok()
        } yield resp
    }
  }

  def tramsRoutes[F[_]: Sync](trams: TramsAlgebra[F]): HttpRoutes[F] = {
    val dsl = new Http4sDsl[F]{}
    import dsl._
    HttpRoutes.of[F] {
      case req @ POST -> Root / "trams" /"find" =>
        for {
          FindTram(id, from, to) <- req.as[FindTram]
          trams <- trams.findTram(id, from, to)
          resp <- Ok(trams.asJson)
        } yield resp
    }
  }
}