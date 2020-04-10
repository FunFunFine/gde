package io.funfunfine.gde

import cats.effect.{ExitCode, IO, IOApp}
import cats.implicits._

object Main extends IOApp {
  def run(args: List[String]) =
    GdeServer.stream[IO].compile.drain.as(ExitCode.Success)
}


