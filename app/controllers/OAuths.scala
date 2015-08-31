package controllers

import javax.inject.Singleton

import play.api.mvc._

@Singleton
class OAuths extends Controller {
  def authorization(social: String, state: String) = Action { implicit request =>
    Redirect("")
  }

  def qq = Action { request =>
    Ok("")
  }

  def weibo = Action { request =>
    Ok("")
  }

  def weiboCancel = Action { request =>
    Ok("")
  }
}

