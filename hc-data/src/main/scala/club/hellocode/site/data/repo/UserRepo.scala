package club.hellocode.site.data.repo

import java.time.ZonedDateTime
import javax.inject.{Inject, Singleton}

import club.hellocode.site.common.Utils
import club.hellocode.site.common.exception.HcUnauthorizedException
import club.hellocode.site.data.domain.SignParam
import club.hellocode.site.data.driver.MyDriver.api._
import club.hellocode.site.data.model.{Credential, User}
import play.api.libs.json.JsObject

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class UserRepo @Inject()(schemas: Schema)(implicit ex: ExecutionContext) {

  import schemas._

  def update(body: User): Future[User] = ???

  def findList(page: Int, size: Int): Future[Seq[User]] = {
    ???
  }

  def signin(payload: SignParam): Future[Option[User]] = {
    val q = for {
      c <- tCredential.filter(_.email === payload.account)
      u <- tUser.filter(_.id === c.id)
    } yield (c, u)

    db.run(q.result).map(_.headOption).map {
      case Some((c, u)) =>
        if (!Utils.matchPassword(payload.password, c.salt, c.password)) {
          throw HcUnauthorizedException("密码不匹配")
        }
        Some(u)
      case None =>
        None
    }
  }

  def signupByEmail(body: SignParam): Future[Long] = {
    val (salt, password) = Utils.generatePassword(body.password)
    val insertCredential = tCredential returning tCredential.map(_.id) += Credential(0, Some(body.account), salt, password)

    val q = for {
      id <- insertCredential
      _ <- tUser += User(id, Some(body.account), None, None, JsObject(Nil), ZonedDateTime.now())
    } yield id
    db.run(q.transactionally)
  }

  def findOneById(id: Long): Future[Option[User]] = {
    db.run(tUser.filter(_.id === id).result).map(_.headOption)
  }

  def findOneCredential(id: Long): Future[Option[Credential]] = {
    db.run(tCredential.filter(_.id === id).result).map(_.headOption)
  }

  def update(id: Long, user: User): Future[Int] = {
    db.run(tUser.filter(_.id === id).update(user).transactionally)
  }
}
