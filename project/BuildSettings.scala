import Dependencies._
import sbt.Keys._
import sbt._

object BuildSettings {

  lazy val basicSettings = Seq(
    version := "0.0.1",
    homepage := Some(new URL("http://www.hellocode.club/")),
    organization := "club.hellocode",
    organizationHomepage := Some(new URL("http://team.hellocode.club/")),
    startYear := Some(2015),
    scalaVersion := "2.11.7",
    scalacOptions := Seq(
      "-encoding", "utf8",
      "-unchecked",
      "-feature",
      "-deprecation"
    ),
    javacOptions := Seq(
      "-encoding", "utf8",
      "-Xlint:unchecked",
      "-Xlint:deprecation"
    ),
    resolvers ++= Seq(
      "snapshots" at "http://oss.sonatype.org/content/repositories/snapshots",
      "releases" at "http://oss.sonatype.org/content/repositories/releases",
      "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
      "Typesafe Snapshots" at "http://repo.typesafe.com/typesafe/snapshots/"),
    libraryDependencies ++= (
      __compile(_scalaModules) ++
        __compile(_slf4j) ++
        __compile(_logback) ++
        __compile(_scala) ++
        __test(_scalatest)),
    offline := true
  )

  lazy val noPublishing = Seq(
    publish :=(),
    publishLocal :=()
  )

}

