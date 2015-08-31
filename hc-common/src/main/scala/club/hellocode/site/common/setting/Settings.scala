package club.hellocode.site.common.setting

import java.nio.file.Path
import java.time.{Duration, LocalTime}
import club.hellocode.site.common.Utils
import com.typesafe.config.{Config, ConfigFactory}

import scala.collection.JavaConverters._

case class FileUploadSettings(localPath: String, host: Option[String], context: String) {
  def uriByLocalPath(fileSystemPath: Path): String = {
    context + fileSystemPath.toString.replaceFirst(localPath, "")
  }

  def uri(v: String) = context + v

  def url(v: String) = host.map(_ + uri(v)).getOrElse(uri(v))
}

case class CookieSettings(domain: Option[String], path: String, httpOnly: Boolean, maxAge: Int)

case class TaskSettings(duration: Duration, dailyTask: String) {
  def dailyTaskLocalTime = LocalTime.parse(dailyTask, Utils.formatTime)
}

object Settings {
  val schedule = {
    val conf = ConfigFactory.load().getConfig("helloscala.schedule")
    TaskSettings(conf.getDuration("duration"), conf.getString("daily-schedule"))
  }

  val cookieSettings = {
    val conf = ConfigFactory.load().getConfig("helloscala.cookie")
    CookieSettings(getString(conf, "domain"),
      getString(conf, "path").getOrElse("/"),
      getBoolean(conf, "httpOnly").getOrElse(false),
      getInt(conf, "maxAge").getOrElse(1800))
  }

  val fileUpload = {
    val conf = ConfigFactory.load().getConfig("helloscala.file-upload")
    FileUploadSettings(getString(conf, "local-path").get, getString(conf, "host"), getString(conf, "context").getOrElse(""))
  }

  private def getStringList(conf: Config, name: String): Seq[String] = try {
    conf.getStringList(name).asScala
  } catch {
    case _: Exception =>
      Nil
  }

  private def getString(conf: Config, name: String) = try {
    Some(conf.getString(name))
  } catch {
    case _: Exception =>
      None
  }

  private def getBoolean(conf: Config, name: String) = try {
    Some(conf.getBoolean(name))
  } catch {
    case _: Exception =>
      None
  }

  private def getInt(conf: Config, name: String) = try {
    Some(conf.getInt(name))
  } catch {
    case _: Exception =>
      None
  }
}
