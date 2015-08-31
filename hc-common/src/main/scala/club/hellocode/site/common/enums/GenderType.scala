package club.hellocode.site.common.enums

import play.api.libs.json._

/**
 * 性别
 * Created by jingyang on 15/4/22.
 */
object GenderType extends Enumeration {
  val MALE = Value
  val FEMALE = Value

  implicit val __genderTypeFormats = new Format[GenderType.Value] {
    override def reads(json: JsValue): JsResult[GenderType.Value] = JsSuccess(GenderType.withName(json.as[String]))

    override def writes(o: GenderType.Value): JsValue = JsString(o.toString)
  }
}
