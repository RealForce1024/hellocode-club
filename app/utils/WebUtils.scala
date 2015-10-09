package utils

import club.hellocode.site.common.setting.Settings
import club.hellocode.site.common.util.SecurityUtils
import club.hellocode.site.common.{AccountToken, HcConstant}
import com.typesafe.scalalogging.StrictLogging
import play.api.Play
import play.api.Play.current
import play.api.mvc.{Cookie, DiscardingCookie, RequestHeader}

object WebUtils extends StrictLogging {
  val cookieSettings = Settings.cookieSettings
  val securityUtils = new SecurityUtils()

  def getIpFromRequest(implicit request: RequestHeader) =
    request.headers.get(HcConstant.REAL_IP).getOrElse(request.remoteAddress)

  def createCookieByToken(token: AccountToken) =
    createCookie(HcConstant.ACCOUNT_TOKEN_COOKIE_NAME, securityUtils.encrypt(token.toString), None)

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
  def getAccountToken(implicit request: RequestHeader): Option[AccountToken] = {
    val userTokenStr = request.cookies.get(HcConstant.ACCOUNT_TOKEN_COOKIE_NAME).map(_.value) orElse
      request.headers.get(HcConstant.ACCOUNT_TOKEN_COOKIE_NAME)

    userTokenStr.map { v =>
      val s = securityUtils.decrypt(v)
      AccountToken(s)
    }
  }

}
