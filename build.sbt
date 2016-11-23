name := """babyclasses-server"""
organization := "proactiveoak"

version := "1.0-SNAPSHOT"

offline := true

lazy val root = (project in file(".")).enablePlugins(PlayScala)
resolvers += "snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
resolvers += "Artima Maven Repository" at "http://repo.artima.com/releases"
resolvers += "releases"  at "https://oss.sonatype.org/content/groups/scala-tools"
scalaVersion := "2.11.8"

libraryDependencies += filters
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test
libraryDependencies += "com.typesafe.play" %% "play" % "2.5.9"

libraryDependencies += "org.mongodb.scala" %% "mongo-scala-driver" % "1.1.1"
libraryDependencies += "com.typesafe" % "config" % "1.3.1"
libraryDependencies += "org.scalaz" %% "scalaz-core" % "7.2.7"
libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.1"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test"

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "proactiveoak.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "proactiveoak.binders._"
