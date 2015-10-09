package club.hellocode.site.data.repo

import javax.inject.Singleton

import club.hellocode.site.common.HcConstant
import club.hellocode.site.data.driver.MyDriver.api._
import club.hellocode.site.data.model.{TableCredential, TableTopic, TableTopicComment, TableUser}

@Singleton
class Schema {
  val db = Database.forConfig(HcConstant.CONFIG_DB)

  def tCredential = TableQuery[TableCredential]

  def tUser = TableQuery[TableUser]

  def tTopic = TableQuery[TableTopic]

  def tTopicComment = TableQuery[TableTopicComment]

  def tableSchemas =
    tCredential.schema ++
      tUser.schema ++
      tTopic.schema ++
      tTopicComment.schema
}
