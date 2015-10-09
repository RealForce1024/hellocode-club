package club.hellocode.site.common

import java.nio.charset.Charset

import reactivemongo.bson.BSONObjectID

object HcConstant {
  final val CONFIG_DB = "hellocode.db"
  final val OID_LENGTH = BSONObjectID.generate.stringify.length
  final val SALT_SIZE = 16
  final val UTF8 = Charset.forName("UTF-8")
  final val REAL_IP = "X-Real-Ip"
  final val CAPTCHA_KEY = "captcha_"
  final val ACCESS_CODE_LENGTH = 7
  final val ACCOUNT_TOKEN_COOKIE_NAME = "hc-a-t"
  final val ACCOUNT_TOKEN_DELIMITER = ";"
}
