enablePlugins(SriPlatformPlugin)

name := "sritodo"

//scalaVersion := "2.11.11"
scalaVersion := "2.12.2"

resolvers += Resolver.bintrayRepo("scalajs-react-interface", "maven")

libraryDependencies ++= Seq(
  "scalajs-react-interface" %%% "core" % "2017.7.9-RC",
  "scalajs-react-interface" %%% "mobile" % "2017.7.9-RC",
  "scalajs-react-interface" %%% "universal" % "2017.7.9-RC",
  "scalajs-react-interface" %%% "vector-icons" % "2017.7.9-RC",
  "scalajs-react-interface" %%% "platform-config-ios" % "2017.7.9-RC" % ios,
  "scalajs-react-interface" %%% "platform-config-android" % "2017.7.9-RC" % android,
  "scalajs-react-interface" %%% "navigation" % "2017.7.9-RC",

  "com.typesafe.play" %% "play-json" % "2.7.3",
  "org.scala-lang.modules" %% "scala-async" % "0.10.0"
)


scalacOptions ++= Seq(
  "-feature",
  "-deprecation",
  "-unchecked",
  "-language:implicitConversions"
)
