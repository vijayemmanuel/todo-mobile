name := "shopping-web-app"

version := "0.0.1"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.8"

// For Docker Plugin which come out of the box in Play from sbt-native-packager
// https://www.scala-sbt.org/sbt-native-packager/formats/docker.html
maintainer in Docker := "Vijay Emmanuel <vijay.emmanuel@gmail.com>"
dockerExposedPorts := Seq(9000)
dockerBaseImage := "java:latest"

libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play-slick" % "4.0.2",
  "com.typesafe.play" %% "play-slick-evolutions" % "4.0.2",
  "com.byteslounge" %% "slick-repo" % "1.5.3",
  evolutions,
  guice,
  evolutions,
  //"com.h2database" % "h2" % "1.4.191", // ENABLE FOR H2 DATABASE
  "org.postgresql" % "postgresql" % "42.2.6", // ENABLE for POSTGRES (IF DEPLOYING IN AWS)
  ehcache,
  ws,
  filters,
  specs2 % Test)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"
