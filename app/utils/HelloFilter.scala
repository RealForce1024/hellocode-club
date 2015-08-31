package utils

import play.api.Logger
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc._

import scala.concurrent.Future

/**
 * ResponseFilter
 * Created by Yang Jing (yangbajing@gmail.com) on 2015-08-28.
 */
class HelloFilter extends Filter {

  override def apply(nextFilter: (RequestHeader) => Future[Result])(requestHeader: RequestHeader): Future[Result] = {
    val startTime = System.currentTimeMillis

    nextFilter(requestHeader).map { result =>
      val endTime = System.currentTimeMillis
      val requestTime = endTime - startTime

      Logger.info(s"${requestHeader.method} ${requestHeader.uri} " +
        s"took ${requestTime}ms and returned ${result.header.status}")

      result.withHeaders("Request-Time" -> requestTime.toString)
    }
  }

}
