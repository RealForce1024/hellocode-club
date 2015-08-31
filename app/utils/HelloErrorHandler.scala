package utils

import play.api.http.HttpErrorHandler
import play.api.mvc.{RequestHeader, Result, Results}

import scala.concurrent.Future

/**
 * ErrorHandler
 * Created by Yang Jing (yangbajing@gmail.com) on 2015-08-28.
 */
class HelloErrorHandler extends HttpErrorHandler {
  override def onClientError(request: RequestHeader, statusCode: Int, message: String): Future[Result] = {
    Future.successful(Results.Status(statusCode)("A client error occurred: " + message))
  }

  override def onServerError(request: RequestHeader, exception: Throwable): Future[Result] = {
    Future.successful(Results.InternalServerError("A server error occurred: " + exception.getMessage))
  }
}
