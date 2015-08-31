package utils

import club.hellocode.site.common.{OwnerToken, HsConstant}
import club.hellocode.site.common.setting.Settings
import club.hellocode.site.common.util.SecurityUtils
import com.typesafe.scalalogging.StrictLogging
import play.api.Play
import play.api.Play.current
import play.api.mvc.{DiscardingCookie, Cookie, RequestHeader}

object WebUtils extends StrictLogging {
  val cookieSettings = Settings.cookieSettings
  val securityUtils = new SecurityUtils()

  def getIpFromRequest(implicit request: RequestHeader) =
    request.headers.get(HsConstant.REAL_IP).getOrElse(request.remoteAddress)

  def hasMin(path: String) = {
    if (Play.isDev) path
    else {
      val n = path.lastIndexOf('.')
      path.substring(0, n) + ".min" + path.substring(n)
    }
  }

  def createCookieByToken(token: OwnerToken) =
    createCookie(HsConstant.OWNER_TOKEN_COOKIE_NAME, securityUtils.encrypt(token.toString), None)

  def createCookie(name: String, value: String, maxAge: Option[Int]) = {
    val cookie = Cookie(name, value, maxAge orElse Some(cookieSettings.maxAge), cookieSettings.path, cookieSettings.domain, httpOnly = cookieSettings.httpOnly)
    logger.debug("create cookie: " + cookie)
    cookie
  }

  def discardingCookie(name: String) = {
    DiscardingCookie(name, cookieSettings.path, cookieSettings.domain)
  }

  /**
   * 从Request中获取token
   * @param request
   * token未找到或超期抛出异常
   * @return
   */
  def getOwnerToken(implicit request: RequestHeader): Option[OwnerToken] = {
    val userTokenStr = request.cookies.get(HsConstant.OWNER_TOKEN_COOKIE_NAME).map(_.value) orElse
      request.headers.get(HsConstant.OWNER_TOKEN_COOKIE_NAME)

    userTokenStr.map { v =>
      val s = securityUtils.decrypt(v)
      OwnerToken(s)
    }
  }

}
