package club.hellocode.site.data.model

import java.time.ZonedDateTime

import club.hellocode.site.data.driver.MyDriver
import club.hellocode.site.data.driver.MyDriver.api._
import org.bson.types.ObjectId

/**
 * 主题回复
 * Created by jingyang on 15/4/22.
 */
case class TopicComment(id: ObjectId,
                        topicId: Long,
                        creator: Long,
                        content: String,
                        title: Option[String],
                        createdAt: ZonedDateTime = ZonedDateTime.now())

class TableTopicComment(tag: Tag) extends Table[TopicComment](tag, "t_topic_comment") {
  val id = column[ObjectId]("id", O.PrimaryKey, O.SqlType(MyDriver.SqlTypes.OID))
  val topicId = column[Long]("topic_id")
  val creator = column[Long]("creator")
  val content = column[String]("content", O.SqlType("text"))
  val title = column[Option[String]]("title")
  val createdAt = column[ZonedDateTime]("created_at")

  def * = (id, topicId, creator, content, title, createdAt) <>(TopicComment.tupled, TopicComment.unapply)
}

