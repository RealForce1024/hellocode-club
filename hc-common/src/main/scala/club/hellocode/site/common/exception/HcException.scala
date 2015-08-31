package club.hellocode.site.common.exception

import club.hellocode.site.common.TMessageResponse

abstract class HcBaseException(val message: String, val code: Int) extends RuntimeException(message) with TMessageResponse {
  override def toString = code + ": " + super.toString
}

case class HcException(override val code: Int,
                       override val message: String) extends HcBaseException(message, code)

case class HcUnauthorizedException(override val message: String = "Unauthorized",
                                   override val code: Int = 401) extends HcBaseException(message, code)

case class HcBadRequestException(override val message: String = "Bad Request",
                                 override val code: Int = 400) extends HcBaseException(message, code)

case class HcInternalException(override val message: String = "Internal Exception",
                               override val code: Int = 500) extends HcBaseException(message, code)

case class HcNotFoundException(override val message: String = "Not Found",
                               override val code: Int = 404) extends HcBaseException(message, code)

case class HcForbiddenExeption(override val message: String = "Forbidden",
                               override val code: Int = 403) extends HcBaseException(message, code)