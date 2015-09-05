package club.hellocode.site.common

import java.nio.charset.Charset

import org.bson.types.ObjectId

object HsConstant {
  final val CONFIG_DB = "hellocode.db"
  final val OID_LENGTH = ObjectId.get().toString.length
  final val SALT_SIZE = 16
  final val UTF8 = Charset.forName("UTF-8")
  final val REAL_IP = "X-Real-Ip"
  final val CAPTCHA_KEY = "captcha_"
  final val ACCESS_CODE_LENGTH = 7
  final val OWNER_TOKEN_COOKIE_NAME = "hs-owner-token"
  final val OWNER_TOKEN_DELIMITER = ";"

  final val ORDER_ID_KEEP_SIZE = 6
}
