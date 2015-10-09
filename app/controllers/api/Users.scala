package controllers.api

import javax.inject.{Inject, Singleton}
import club.hellocode.site.business.service.UserService
import club.hellocode.site.common.exception.HcNotFoundException
import club.hellocode.site.data.model.User
import play.api.libs.json.Json
import play.api.mvc.Controller
import utils.{HcController, TokenAction}

@Singleton
class Users @Inject()(userService: UserService
                       ) extends Controller with HcController {

  def findOneById(id: Long) = TokenAction.async { request =>
    userService.findOneById(id).map {
      case Some(bo) => Ok(Json.toJson(bo))
      case None => Unauthorized
    }
  }

  def update(id: Long) = TokenAction.async(parse.json.map(_.as[User])) { request =>
    userService.update(request.body).map { bo =>
      Ok(Json.toJson(bo))
    }
  }

  def findList(page: Int,
               size: Int) = TokenAction.async {
    userService.findList(page, size).map(bo => Ok(Json.toJson(bo)))
  }

  def getUser = TokenAction.async { request =>
    userService.findOneById(request.token.accountId).map {
      case Some(user) => Ok(Json.toJson(user))
      case None => NotFound(Json.toJson(HcNotFoundException()))
    }
  }
}
