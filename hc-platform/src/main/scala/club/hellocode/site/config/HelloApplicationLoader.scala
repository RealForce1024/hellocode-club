package club.hellocode.site.config

import com.typesafe.scalalogging.LazyLogging
import play.api.inject.guice.{GuiceApplicationBuilder, GuiceApplicationLoader}
import play.api.libs.json.Json
import play.api.{ApplicationLoader, Configuration}

/**
 * Hello Application Loader
 * Created by Yang Jing (yangbajing@gmail.com) on 2015-10-09.
 */
class HelloApplicationLoader extends GuiceApplicationLoader with LazyLogging {
  override protected def builder(context: ApplicationLoader.Context): GuiceApplicationBuilder = {
    val extra = Configuration() // generateExtraConfiguration(context)

    //    logger.debug("extra: " + extra.getString("hieat.assets.js.app"))
    //    logger.info(context.environment.toString)

    initialBuilder
      .in(context.environment)
      .loadConfig(extra ++ context.initialConfiguration)
      .overrides(overrides(context): _*)
  }

  private def generateExtraConfiguration(context: ApplicationLoader.Context) = {
    val inAssetsStat = context.environment.classLoader.getResourceAsStream("assets-stats.json")
    val assetsStatJson = Json.parse(inAssetsStat)
    inAssetsStat.close()

    val app = assetsStatJson.\("app").as[String]
    val sign = assetsStatJson.\("sign").as[String]
    val common = assetsStatJson.\("common").as[String]

    Configuration("hellocode.assets.js.app" -> app,
      "hellocode.assets.js.sign" -> sign,
      "hellocode.assets.js.common" -> common)
  }

}
