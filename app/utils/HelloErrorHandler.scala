package utils

import play.api.Logger
import play.api.http.{Status, HttpErrorHandler}
import play.api.libs.json.Json
import play.api.mvc.{RequestHeader, Result, Results}

import scala.concurrent.Future

/**
 * ErrorHandler
 * Created by Yang Jing (yangbajing@gmail.com) on 2015-08-28.
 */
class HelloErrorHandler extends HttpErrorHandler {
  override def onClientError(request: RequestHeader, statusCode: Int, message: String): Future[Result] = {
    Logger.logger.error(s"[onClientError] [$request] $statusCode -> $message")
    Future.successful(Results.Status(statusCode)(Json.obj("code" -> statusCode, "message" -> message)))
  }

  override def onServerError(request: RequestHeader, exception: Throwable): Future[Result] = {
    Logger.logger.error(s"[onServerError] [$request] $exception", exception)
    Future.successful(Results.InternalServerError(
      Json.obj("code" -> Status.INTERNAL_SERVER_ERROR, "message" -> exception.getLocalizedMessage)))
  }
}
