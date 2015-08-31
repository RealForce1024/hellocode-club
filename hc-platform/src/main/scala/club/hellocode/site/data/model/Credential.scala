package club.hellocode.site.data.model

import club.hellocode.site.data.driver.MyDriver
import MyDriver.api._

case class Credential(id: Long,
                      email: Option[String],
                      salt: Array[Byte],
                      password: Array[Byte]) {

}

class TableCredential(tag: Tag) extends Table[Credential](tag, "t_credential") {
  val id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  val email = column[Option[String]]("email")
  val salt = column[Array[Byte]]("salt")
  val password = column[Array[Byte]]("password")

  def indexEmail = index(tableName + "_idx_email", email, true)

  def * = (id, email, salt, password) <>(Credential.tupled, Credential.unapply)
}
