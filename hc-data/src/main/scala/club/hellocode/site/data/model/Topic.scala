package club.hellocode.site.data.model

import java.time.ZonedDateTime

import club.hellocode.site.data.driver.MyDriver
import club.hellocode.site.data.driver.MyDriver.api._
import reactivemongo.bson.BSONObjectID

/**
 * 主题
 * Created by jingyang on 15/4/22.
 */
case class Topic(id: BSONObjectID,
                 creator: Long,
                 title: String,
                 content: String,
                 description: Option[String],
                 tags: List[String] = Nil,
                 createdAt: ZonedDateTime = ZonedDateTime.now())

class TableTopic(tag: Tag) extends Table[Topic](tag, "t_topic") {
  val id = column[BSONObjectID]("id", O.PrimaryKey, O.SqlType(MyDriver.SqlTypes.OID))
  val creator = column[Long]("creator")
  val title = column[String]("title")
  val content = column[String]("content", O.SqlType("text"))
  val description = column[Option[String]]("description")
  val tags = column[List[String]]("tags")
  val createdAt = column[ZonedDateTime]("created_at")

  def * = (id, creator, title, content, description, tags, createdAt
    ) <>(Topic.tupled, Topic.unapply)
}
