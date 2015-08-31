package utils

import club.hellocode.site.common.OwnerToken
import club.hellocode.site.common.exception.HcUnauthorizedException
import com.typesafe.scalalogging.StrictLogging
import play.api.mvc.{ActionBuilder, ActionTransformer, Request, WrappedRequest}

import scala.concurrent.Future

class TokenRequest[A](val ownerToken: OwnerToken, request: Request[A]) extends WrappedRequest[A](request) {
  override def toString() = s"TokenRequest($ownerToken)" + super.toString
}

object TokenAction extends ActionBuilder[TokenRequest] with ActionTransformer[Request, TokenRequest] with StrictLogging {
  override protected def transform[A](request: Request[A]): Future[TokenRequest[A]] = {
    WebUtils.getOwnerToken(request) match {
      case Some(token) =>
        logger.debug(token.toString)

        if (token.hasExpired) {
          throw HcUnauthorizedException("token timeout")
        }

        Future.successful(new TokenRequest(token, request))

      case None =>
        throw HcUnauthorizedException("token not exists")
    }
  }
}
