package club.hellocode.site.common.exception

import play.api.libs.json._

/**
 * HsExceptionJsonImplicits
 * Created by Yang Jing (yangbajing@gmail.com) on 2015-08-28.
 */
trait ExceptionJsonImplicits {
  implicit val __hcExceptionWrites = new Writes[HcBaseException] {
    override def writes(o: HcBaseException): JsValue = Json.obj("code" -> o.code, "message" -> o.message)
  }
}
