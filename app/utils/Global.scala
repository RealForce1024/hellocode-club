package utils

import com.typesafe.scalalogging.StrictLogging
import play.api.mvc.RequestHeader
import play.api.{Application, GlobalSettings}

/**
 * Created by Yang Jing on 2015-04-06.
 */
object Global extends GlobalSettings with StrictLogging {
  override def onStart(app: Application) = {
    logger.info("Global.onStart:")
  }

  override def onStop(app: Application) = {
    logger.info("Global.onStop:")
  }

  override def onError(request: RequestHeader, ex: Throwable) = super.onError(request, ex)

  override def onBadRequest(request: RequestHeader, error: String) = super.onBadRequest(request, error)

  override def onRequestReceived(request: RequestHeader) = super.onRequestReceived(request)

  override def onRouteRequest(request: RequestHeader) = super.onRouteRequest(request)
}
