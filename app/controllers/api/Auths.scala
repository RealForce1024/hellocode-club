package controllers.api

import java.util.UUID
import javax.inject.{Inject, Singleton}

import club.hellocode.site.business.service.{CaptchaService, UserService}
import club.hellocode.site.common.exception.HcUnauthorizedException
import club.hellocode.site.common.setting.Settings
import club.hellocode.site.common.{AccountToken, HcConstant, Utils}
import club.hellocode.site.data.domain.SignParam
import play.api.cache.CacheApi
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}
import utils.{HcController, TokenAction, WebUtils}

import scala.concurrent.Future
import scala.concurrent.duration._

/**
 * 账号认证
 * Created by Yang Jing (yangbajing@gmail.com) on 2015-08-30.
 */
@Singleton
class Auths @Inject()(userService: UserService,
                      captchaService: CaptchaService,
                      cacheApi: CacheApi) extends Controller with HcController {
  def signUp = Action.async(parse.json.map(_.as[SignParam])) { request =>
    println(request.session)
    println(cacheApi.get[String](request.session.get(HcConstant.CAPTCHA_KEY).get))
    println(request.body)
    val action = for {
      key <- request.session.get(HcConstant.CAPTCHA_KEY)
      captcha <- cacheApi.get[String](key)
      if request.body.captcha.isDefined && captcha.equalsIgnoreCase(request.body.captcha.get)
    } yield {
        userService.signupByEmail(request.body).map { accountId =>
          val token = AccountToken(accountId, Nil, Utils.currentTimeSeconds(), request.remoteAddress)
          val cookie = WebUtils.createCookieByToken(token)
          Created(Json.obj("id" -> accountId)).withCookies(cookie).withNewSession
        }
      }
    action getOrElse Future.successful(Unauthorized(Json.toJson(HcUnauthorizedException("验证码不正确"))))
  }

  def signIn = Action.async(parse.json.map(_.as[SignParam])) { request =>
    println(request.body)
    userService.signin(request.body).map {
      case Some(user) =>
        val token = AccountToken(user.id, Nil, Utils.currentTimeSeconds(), request.remoteAddress)
        val cookie = WebUtils.createCookieByToken(token)
        Ok(Json.toJson(user)).withCookies(cookie).withNewSession
      case None =>
        NotFound(s"user not found")
    }
  }

  def signOut = TokenAction {
    val cookie = WebUtils.discardingCookie(HcConstant.ACCOUNT_TOKEN_COOKIE_NAME)
    Ok.withCookies(cookie.toCookie).withNewSession
  }

  def createCaptcha = Action {
    val (token, bytes) = captchaService.createCaptcha()
    val key = UUID.randomUUID().toString
    cacheApi.set(key, token, Settings.cookieSettings.maxAge.seconds)
    Ok(bytes).
      withSession(HcConstant.CAPTCHA_KEY -> key).
      withHeaders("Content-Type" -> "image/png", "Cache-Control" -> "no-cache, no-store", "Pragma" -> "no-cache")
  }

}
