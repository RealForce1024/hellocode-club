package club.hellocode.site.common

import club.hellocode.site.common.exception.HcForbiddenExeption
import club.hellocode.site.common.setting.Settings
import com.typesafe.scalalogging.StrictLogging
import org.apache.commons.lang3.StringUtils

/**
 * @param ownerId
 * @param ip
 * @param timestamp 秒
 */
case class OwnerToken(ownerId: Long, roles: Seq[Int], timestamp: Long, ip: String) {
  assert(ownerId > 0L)
  assert(timestamp > 0L)
  assert(StringUtils.isNoneBlank(ip))

  override def toString = {
    ownerId + HsConstant.OWNER_TOKEN_DELIMITER +
      roles.mkString(",") + HsConstant.OWNER_TOKEN_DELIMITER +
      timestamp + HsConstant.OWNER_TOKEN_DELIMITER +
      ip
  }

  def hasExpired = {
    (timestamp + Settings.cookieSettings.maxAge) < Utils.currentTimeSeconds()
  }

  def isValid = !hasExpired

}

object OwnerToken extends StrictLogging {
  def apply(s: String): OwnerToken = {
    s.split(HsConstant.OWNER_TOKEN_DELIMITER) match {
      case Array(ownerId, roles, timestamp, ip) =>
        OwnerToken(ownerId.toLong, roles.split(",").map(_.toInt), timestamp.toLong, ip)

      case _ =>
        throw HcForbiddenExeption(s"$s is invalid token string")
    }
  }
}
