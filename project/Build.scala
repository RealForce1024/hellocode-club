import _root_.sbt.Keys._
import _root_.sbt._
import com.thoughtworks.sbtApiMappings.ApiMappings
import play.routes.compiler.InjectedRoutesGenerator
import play.sbt.Play.autoImport._
import play.sbt.PlayScala
import play.sbt.routes.RoutesKeys
import play.twirl.sbt.Import.TwirlKeys

object Build extends Build {

  import BuildSettings._
  import Dependencies._

  override lazy val settings = super.settings :+ {
    shellPrompt := (s => Project.extract(s).currentProject.id + " > ")
  }

  ///////////////////////////////////////////////////////////////
  // hellocode project
  ///////////////////////////////////////////////////////////////
  lazy val hellocode = Project("hellocode", file("."))
    .enablePlugins(PlayScala)
    .enablePlugins(ApiMappings)
    .aggregate(hcPlatform, hcData, hcCommon)
    .dependsOn(hcPlatform, hcData, hcCommon)
    .settings(basicSettings: _*)
    .settings(
      description := "hellocode",
      TwirlKeys.templateImports ++= Seq("club.hellocode.site.data.domain", "club.hellocode.site.common.enums._" /*, "club.hellocode.site.data.model._"*/),
      RoutesKeys.routesGenerator := InjectedRoutesGenerator,
      libraryDependencies ++= (
        __compile(cache) ++
          __compile(ws) ++
          __compile(_scalaModules) ++
          __compile(_scalaLogging) ++
          __compile(_commonsEmail) ++
          __compile(_typesafeConfig) ++
          __test(_scalatestPlay)))

  ///////////////////////////////////////////////////////////////
  // projects
  ///////////////////////////////////////////////////////////////
  lazy val hcPlatform = Project("hc-platform", file("hc-platform"))
    .enablePlugins(ApiMappings)
    .dependsOn(hcData, hcCommon)
    .settings(basicSettings: _*)
    .settings(
      description := "hc-platform",
      libraryDependencies ++= (
        __provided(_play) ++
          __provided(_scalaLogging) ++
          __compile(_patchca) ++
          __compile(_akkaActor) ++
          __compile(_akkaSlf4j)))

  lazy val hcData = Project("hc-data", file("hc-data"))
    .enablePlugins(ApiMappings)
    .dependsOn(hcCommon)
    .settings(basicSettings: _*)
    .settings(
      description := "hc-data",
      libraryDependencies ++= (
        __provided(_playJson) ++
          __provided(_scalaLogging) ++
          __provided(_guice) ++
          __compile(_reactivemongo) ++
          __compile(_slick) ++
          __compile(_hikariCP) ++
          __compile(_slickPg) ++
          __compile(_postgresql)))

  lazy val hcCommon = Project("hc-common", file("hc-common"))
    .enablePlugins(ApiMappings)
    .settings(basicSettings: _*)
    .settings(
      description := "hc-common",
      libraryDependencies ++= (
        __compile(_commonsLang3) ++
          __compile(_bouncycastle) ++
          __compile(_commonsCodec) ++
          __provided(_reactivemongo) ++
          __provided(_playJson) ++
          __provided(_commonsEmail) ++
          __provided(_scalaLogging) ++
          __provided(_typesafeConfig)))

}

