package club.hellocode.site.data.model

import java.time.ZonedDateTime
import club.hellocode.site.common.enums.GenderType
import club.hellocode.site.data.driver.MyDriver
import play.api.libs.json.JsValue
import MyDriver.api._

case class User(id: Long,
                nick: Option[String],
                age: Option[Int],
                gender: Option[GenderType.Value],
                attrs: JsValue,
                createdAt: ZonedDateTime = ZonedDateTime.now()) {

}

class TableUser(tag: Tag) extends Table[User](tag, "t_user") {
  val id = column[Long]("id", O.PrimaryKey)
  val nick = column[Option[String]]("nick")
  val age = column[Option[Int]]("age")
  val gender = column[Option[GenderType.Value]]("gender")
  val attrs = column[JsValue]("attrs")
  val createdAt = column[ZonedDateTime]("created_at")

  def * = (id, nick, age, gender, attrs, createdAt
    ) <>(User.tupled, User.unapply)
}