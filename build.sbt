val Http4sVersion = "0.21.1"
val CirceVersion = "0.13.0"
val Specs2Version = "4.8.3"
val LogbackVersion = "1.2.3"
val CanoeVersion = "0.4.1"
val NewtypeVersion = "0.4.3"
val RefinedVersion = "0.9.13"
val derevoVersion = "0.11.1"

lazy val root = (project in file("."))
  .settings(
    organization := "io.funfunfine",
    name := "gde",
    version := "0.0.1-SNAPSHOT",
    scalaVersion := "2.13.1",
    libraryDependencies ++= Seq(
      "org.augustjune" %% "canoe"                 % CanoeVersion,
      "io.estatico"    %% "newtype"               % NewtypeVersion,
      "eu.timepit"     %% "refined"               % RefinedVersion,
      "eu.timepit"     %% "refined-cats"          % RefinedVersion,
      "org.http4s"     %% "http4s-blaze-server"   % Http4sVersion,
      "org.http4s"     %% "http4s-blaze-client"   % Http4sVersion,
      "org.http4s"     %% "http4s-circe"          % Http4sVersion,
      "org.http4s"     %% "http4s-dsl"            % Http4sVersion,
      "io.circe"       %% "circe-generic"         % CirceVersion,
      "org.specs2"     %% "specs2-core"           % Specs2Version % "test",
      "ch.qos.logback" % "logback-classic"        % LogbackVersion,
      "org.manatki"    %% "derevo-cats"           % derevoVersion,
      "org.manatki"    %% "derevo-circe-magnolia" % derevoVersion
    ),
    addCompilerPlugin("org.typelevel" %% "kind-projector"     % "0.10.3"),
    addCompilerPlugin("com.olegpy"    %% "better-monadic-for" % "0.3.0")
  )

scalacOptions ++= Seq(
  "-deprecation",
  "-encoding", "UTF-8",
  "-language:higherKinds",
  "-language:postfixOps",
  "-feature",
  "-Ymacro-annotations",
  "-Xfatal-warnings"
)
