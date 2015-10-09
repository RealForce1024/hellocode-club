package club.hellocode.site.data

import club.hellocode.site.common.exception.ExceptionJsonImplicits
import club.hellocode.site.data.model.{Credential, Topic, TopicComment, User}
import play.api.libs.json._
import reactivemongo.bson.BSONObjectID

/**
 * Play Json case class formats
 * Created by Yang Jing (yangbajing@gmail.com) on 2015-08-28.
 */
trait DataJsonImplicits extends ExceptionJsonImplicits {
  implicit val __bsonObjectIdFormat = new Format[BSONObjectID] {
    override def reads(json: JsValue): JsResult[BSONObjectID] = JsSuccess(BSONObjectID(json.as[String]))

    override def writes(o: BSONObjectID): JsValue = JsString(o.stringify)
  }

  implicit val __credentialWrites = new Writes[Credential] {
    override def writes(o: Credential): JsValue =
      JsObject(Seq(Some("id" -> Json.toJson(o.id)), o.email.map(v => "email" -> Json.toJson(v))).flatten)
  }
  implicit val __userFormat = Json.format[User]
  implicit val __topicFormat = Json.format[Topic]
  implicit val __topicCommentFormat = Json.format[TopicComment]
}

object DataJsonImplicits extends DataJsonImplicits
