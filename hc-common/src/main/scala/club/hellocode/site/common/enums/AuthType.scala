package club.hellocode.site.common.enums

import play.api.libs.json._

object AuthType extends Enumeration {
  val EMAIL = Value
  val PHONE = Value

  implicit val __authTypeFormats = new Format[AuthType.Value] {
    override def reads(json: JsValue): JsResult[AuthType.Value] = JsSuccess(AuthType.withName(json.as[String]))

    override def writes(o: AuthType.Value): JsValue = JsString(o.toString)
  }
}
