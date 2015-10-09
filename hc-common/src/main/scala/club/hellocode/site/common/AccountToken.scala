package club.hellocode.site.common

import club.hellocode.site.common.exception.HcForbiddenException
import club.hellocode.site.common.setting.Settings
import com.typesafe.scalalogging.StrictLogging
import org.apache.commons.lang3.StringUtils

/**
 * @param accountId
 * @param ip
 * @param timestamp 秒
 */
case class AccountToken(accountId: Long, roles: Seq[Int], timestamp: Long, ip: String) {
  assert(accountId > 0L)
  assert(timestamp > 0L)
  assert(StringUtils.isNoneBlank(ip))

  override def toString = {
    accountId + HcConstant.ACCOUNT_TOKEN_DELIMITER +
      roles.mkString(",") + HcConstant.ACCOUNT_TOKEN_DELIMITER +
      timestamp + HcConstant.ACCOUNT_TOKEN_DELIMITER +
      ip
  }

  def hasExpired = {
    (timestamp + Settings.cookieSettings.maxAge) < Utils.currentTimeSeconds()
  }

  def isValid = !hasExpired

}

object AccountToken extends StrictLogging {
  def apply(s: String): AccountToken = {
    s.split(HcConstant.ACCOUNT_TOKEN_DELIMITER) match {
      case Array(accountId, roles, timestamp, ip) =>
        AccountToken(accountId.toLong, roles.split(",").filterNot(_.isEmpty).map(_.toInt), timestamp.toLong, ip)

      case _ =>
        throw HcForbiddenException(s"$s is invalid token string")
    }
  }
}
