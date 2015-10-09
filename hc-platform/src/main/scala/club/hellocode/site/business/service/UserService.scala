package club.hellocode.site.business.service

import javax.inject.{Inject, Singleton}

import club.hellocode.site.data.domain.SignParam
import club.hellocode.site.data.model.User
import club.hellocode.site.data.repo.UserRepo

import scala.concurrent.{ExecutionContext, Future}

/**
 * UserService
 * Created by Yang Jing (yangbajing@gmail.com) on 2015-08-28.
 */
@Singleton
class UserService @Inject()(userRepo: UserRepo)(implicit ec: ExecutionContext) {

  def findList(page: Int, size: Int): Future[Seq[User]] =
    userRepo.findList(page, size)

  def update(body: User): Future[User] =
    userRepo.update(body)

  def findOneById(id: Long): Future[Option[User]] =
    userRepo.findOneById(id)

  def signupByEmail(body: SignParam): Future[Long] =
    userRepo.signupByEmail(body)

  def signin(payload: SignParam): Future[Option[User]] = {
    userRepo.signin(payload)
  }

}
