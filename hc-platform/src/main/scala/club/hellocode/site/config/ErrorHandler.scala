package club.hellocode.site.config

import club.hellocode.site.common.exception._
import com.typesafe.scalalogging.StrictLogging
import play.api.http.{HttpErrorHandler, Status}
import play.api.libs.json.Json
import play.api.mvc.{RequestHeader, Result, Results}

import scala.concurrent.Future

/**
 * Error Handler
 * Created by Yang Jing (yangbajing@gmail.com) on 2015-10-09.
 */
class ErrorHandler extends HttpErrorHandler with ExceptionJsonImplicits with StrictLogging {
  override def onClientError(request: RequestHeader, statusCode: Int, message: String): Future[Result] = {
    logger.error(s"[onClientError] [$request] $statusCode -> $message")
    Future.successful(Results.Status(statusCode)(Json.obj("code" -> statusCode, "message" -> message)))
  }

  override def onServerError(request: RequestHeader, e: Throwable): Future[Result] = {
    logger.error(e.toString, e)
    Future.successful(e match {
      case ex: HcBaseException =>
        exceptionToJson(ex)
      case _ =>
        Results.InternalServerError(Json.obj("code" -> Status.INTERNAL_SERVER_ERROR,
          "message" -> e.getLocalizedMessage))
    })
  }

  private def exceptionToJson(ex: HcBaseException) = ex match {
    case _: HcBadRequestException =>
      Results.BadRequest(Json.toJson(ex))
    case _: HcUnauthorizedException =>
      Results.Unauthorized(Json.toJson(ex))
    case _: HcForbiddenException =>
      Results.Forbidden(Json.toJson(ex))
    case _: HcNotFoundException =>
      Results.NotFound(Json.toJson(ex))
    case _ =>
      Results.InternalServerError(Json.toJson(ex))
  }
}
