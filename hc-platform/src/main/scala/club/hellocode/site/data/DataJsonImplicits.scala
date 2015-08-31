package club.hellocode.site.data

import club.hellocode.site.common.exception.HsExceptionJsonImplicits
import club.hellocode.site.data.domain.SignParam
import club.hellocode.site.data.model.{TopicComment, User, Topic, Credential}
import org.bson.types.ObjectId
import play.api.libs.json._

/**
 * Play Json case class formats
 * Created by Yang Jing (yangbajing@gmail.com) on 2015-08-28.
 */
trait DataJsonImplicits extends HsExceptionJsonImplicits {
  implicit val __objectIdFormat = new Format[ObjectId] {
    override def reads(json: JsValue): JsResult[ObjectId] = JsSuccess(new ObjectId(json.as[String]))

    override def writes(o: ObjectId): JsValue = JsString(o.toString)
  }

  implicit val __signParamFormat = Json.format[SignParam]

  implicit val __credentialWrites = new Writes[Credential] {
    override def writes(o: Credential): JsValue =
      JsObject(Seq(Some("id" -> Json.toJson(o.id)), o.email.map(v => "email" -> Json.toJson(v))).flatten)
  }
  implicit val __userFormat = Json.format[User]
  implicit val __topicFormat = Json.format[Topic]
  implicit val __topicCommentFormat = Json.format[TopicComment]
}

object DataJsonImplicits extends DataJsonImplicits
