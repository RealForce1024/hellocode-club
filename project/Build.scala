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
    .aggregate(hcPlatform, hcCommon)
    .dependsOn(hcPlatform, hcCommon)
    .settings(basicSettings: _*)
    .settings(
      description := "hellocode",
      //      TwirlKeys.templateImports ++= Seq("club.hellocode.site.data.model._", "club.hellocode.site.data.domain"),
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
    .dependsOn(hcCommon)
    .settings(basicSettings: _*)
    .settings(
      description := "hc-platform",
      libraryDependencies ++= (
        __provided(_play) ++
          __compile(_slick) ++
          __compile(_hikariCP) ++
          __compile(_slickPg) ++
          __compile(_postgresql) ++
          __compile(_patchca) ++
          __compile(_akkaActor)))

  lazy val hcCommon = Project("hc-common", file("hc-common"))
    .enablePlugins(ApiMappings)
    .settings(basicSettings: _*)
    .settings(
      description := "hc-common",
      libraryDependencies ++= (
        __compile(_bson) ++
          __compile(_commonsLang3) ++
          __compile(_bouncycastle) ++
          __compile(_commonsCodec) ++
          __provided(_playJson) ++
          __provided(_commonsEmail) ++
          __provided(_scalaLogging) ++
          __provided(_typesafeConfig)))

}

