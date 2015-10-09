package club.hellocode.site.common.util

import java.net.URLEncoder
import club.hellocode.site.common.{TripleDesUtils, HcConstant}
import club.hellocode.site.common.exception.HcInternalException
import com.typesafe.scalalogging.StrictLogging

/**
 * SecurityService
 * Created by Yang Jing (yangbajing@gmail.com) on 2015-08-28.
 */
class SecurityUtils extends StrictLogging {
  private val des3 = new TripleDesUtils

  def encrypt(clearText: String): String = {
    try {
      des3.encrypt(clearText)
    } catch {
      case e: Exception =>
        logger.error("3des encrypt fail for clearText: {}", clearText)
        logger.error("encrypt exception", e)
        throw HcInternalException("加密失败")
    }
  }

  def decrypt(encryptedText: String): String = {
    try {
      des3.decrypt(encryptedText)
    }
    catch {
      case e: Exception => {
        logger.error("3des decrypt fail for encryptedText: {}", encryptedText)
        logger.error("decrypt exception", e)
        throw HcInternalException("解密失败")
      }
    }
  }

  def generateHashAndSalt(clearText: String): String = {
    PasswordUtil.hash(clearText)
  }

  def compareWithHashAndSalt(clearText: String, hashText: String): Boolean = {
    PasswordUtil.verify(clearText, hashText)
  }

  def urlEncode(value: String): String = URLEncoder.encode(value, HcConstant.UTF8.name())

}