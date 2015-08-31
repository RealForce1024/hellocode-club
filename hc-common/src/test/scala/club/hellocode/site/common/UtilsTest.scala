package club.hellocode.site.common

import org.scalatest.{Matchers, WordSpec}

/**
 * UtilsTest
 * Created by Yang Jing (yangbajing@gmail.com) on 2015-08-30.
 */
class UtilsTest extends WordSpec with Matchers {
  "UtilsTest" should {
    "password" in {
      val password = "jingyang"
      val (salt, digestPassword) = Utils.generatePassword(password)
      Utils.matchPassword(password, salt, digestPassword) shouldBe true
    }
  }
}
