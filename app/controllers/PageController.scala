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

  def appSingle = app("")

  def app(path: String) = Action {
    Ok(views.html.app())
  }

  def sign(path: String) = Action {
    Ok(views.html.auth.index())
  }

  def todoapp() = Action {
    Ok(views.html.todoapp())
  }

  def redditapp() = Action {
    Ok(views.html.redditapp())
  }
}
