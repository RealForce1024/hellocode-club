package controllers

import javax.inject.Singleton

import play.api.mvc.{Action, Controller}

/**
 * 页面
 * Created by yangjing on 15-5-30.
 */
@Singleton
class PageController extends Controller {

  def index = Action {
    Ok(views.html.index())
  }

  def sign(path: String) = Action {
    Ok(views.html.auth.index())
  }

}
