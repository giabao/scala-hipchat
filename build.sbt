organization := "com.sandinh"

name := "scala-hipchat"

version := "0.9"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.2"

crossScalaVersions := Seq("2.11.2", "2.10.4")

scalacOptions ++= Seq("-encoding", "UTF-8", "-deprecation", "-unchecked", "-feature", "-Yinline-warnings")

javacOptions ++= Seq("-encoding", "UTF-8", "-Xlint:unchecked", "-Xlint:deprecation")

libraryDependencies ++= Seq(
  ws,
  cache,
  jdbc,
  anorm,
  "com.nimbusds"        % "nimbus-jose-jwt" % "3.1.2"
)

libraryDependencies += "com.h2database"      % "h2"              % "1.4.181" % "compile->runtime,test->runtime"

//update
libraryDependencies += "com.typesafe.akka"   %% "akka-slf4j"     % "2.3.6"

//@see http://www.playframework.com/documentation/2.3.x/ProductionDist
doc in Compile <<= target.map(_ / "none")
