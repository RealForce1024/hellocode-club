import play.core.PlayVersion
import sbt._

object Dependencies {
  def __compile(dep: ModuleID): Seq[ModuleID] = __compile(Seq(dep))

  def __compile(deps: Seq[ModuleID]): Seq[ModuleID] = deps map (_ % "compile")

  def __provided(dep: ModuleID): Seq[ModuleID] = __provided(Seq(dep))

  def __provided(deps: Seq[ModuleID]): Seq[ModuleID] = deps map (_ % "provided")

  def __test(dep: ModuleID): Seq[ModuleID] = __test(Seq(dep))

  def __test(deps: Seq[ModuleID]): Seq[ModuleID] = deps map (_ % "test")

  def __runtime(dep: ModuleID): Seq[ModuleID] = __runtime(Seq(dep))

  def __runtime(deps: Seq[ModuleID]): Seq[ModuleID] = deps map (_ % "runtime")

  def __container(dep: ModuleID): Seq[ModuleID] = __container(Seq(dep))

  def __container(deps: Seq[ModuleID]): Seq[ModuleID] = deps map (_ % "container")

  val _scalaModules = Seq(
    "org.scala-lang.modules" %% "scala-xml" % "1.0.4",
    "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.4"
  ).map(_.exclude("org.scala-lang", "scala-library").
    exclude("org.scala-lang", "scala-compiler").
    exclude("org.scala-lang", "scala-reflect"))


  private val verScala = "2.11.7"
  val _scala = Seq(
    "org.scala-lang" % "scala-library" % verScala,
    "org.scala-lang" % "scala-compiler" % verScala,
    "org.scala-lang" % "scala-reflect" % verScala
  )

  private val verAkka = "2.3.14"
  val _akkaActor = "com.typesafe.akka" %% "akka-actor" % verAkka
  val _akkaRemote = "com.typesafe.akka" %% "akka-remote" % verAkka
  val _akkaSlf4j = "com.typesafe.akka" %% "akka-slf4j" % verAkka
  val _akkaTestkit = Seq(
    "com.typesafe.akka" %% "akka-testkit" % verAkka)

  val slickVersion = "3.0.3"
  val _slick = "com.typesafe.slick" %% "slick" % slickVersion
  val _slickExtensions = "com.typesafe.slick" %% "slick-extensions" % slickVersion

  val verSlickPg = "0.9.2"
  val _slickPg = Seq(
    "com.github.tminglei" %% "slick-pg" % verSlickPg
  )

  val _scalatest = Seq(
    "org.scalatest" %% "scalatest" % "2.2.5"
  )

  val _playJson = ("com.typesafe.play" %% "play-json" % PlayVersion.current).
    exclude("commons-logging", "commons-logging").
    exclude("com.typesafe.akka", "akka-actor").
    exclude("com.typesafe.akka", "akka-slf4j").
    exclude("org.scala-lang", "scala-library").
    exclude("org.scala-lang", "scala-compiler").
    exclude("org.scala-lang", "scala-reflect").
    exclude("org.scala-lang.modules", "scala-xml").
    exclude("org.scala-lang.modules", "scala-parser-combinators")

  val _play = ("com.typesafe.play" %% "play" % PlayVersion.current).
    exclude("commons-logging", "commons-logging").
    exclude("com.typesafe.akka", "akka-actor").
    exclude("com.typesafe.akka", "akka-slf4j").
    exclude("org.scala-lang", "scala-library").
    exclude("org.scala-lang", "scala-compiler").
    exclude("org.scala-lang", "scala-reflect").
    exclude("org.scala-lang.modules", "scala-xml").
    exclude("org.scala-lang.modules", "scala-parser-combinators")

  val _scalatestPlay = ("org.scalatestplus" %% "play" % "1.4.0-M4").
    exclude("com.typesafe.play", "play-test").
    exclude("com.typesafe.play", "play-ws").
    exclude("org.seleniumhq.selenium", "selenium-java").
    exclude("org.scala-lang", "scala-library").
    exclude("org.scala-lang", "scala-compiler").
    exclude("org.scala-lang", "scala-reflect").
    exclude("org.scalatest", "scalatest")

  val _reactivemongo = "org.reactivemongo" %% "reactivemongo" % "0.11.7"

  val _typesafeConfig = "com.typesafe" % "config" % "1.3.0"

  val _scalaLogging = ("com.typesafe.scala-logging" %% "scala-logging" % "3.1.0").exclude("org.scala-lang", "scala-library").exclude("org.scala-lang", "scala-reflect")

  val _guice = "com.google.inject" % "guice" % "4.0"

  val _slf4j = "org.slf4j" % "slf4j-api" % "1.7.12"

  val _logback = "ch.qos.logback" % "logback-classic" % "1.1.3"

  val _bouncycastle = "org.bouncycastle" % "bcprov-ext-jdk15on" % "1.52"

  val _commonsEmail = "org.apache.commons" % "commons-email" % "1.3.3"

  val _commonsLang3 = "org.apache.commons" % "commons-lang3" % "3.4"

  val _commonsCodec = "commons-codec" % "commons-codec" % "1.10"

  val _postgresql = "org.postgresql" % "postgresql" % "9.4-1201-jdbc41"

  val _hikariCP = "com.zaxxer" % "HikariCP" % "2.4.1"

  val _patchca = "com.github.bingoohuang" % "patchca" % "0.0.1"
}
