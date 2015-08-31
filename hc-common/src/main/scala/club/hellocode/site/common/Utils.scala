package club.hellocode.site.common

import java.io.{File, FileInputStream}
import java.nio.file.{Files, Path, Paths}
import java.security.SecureRandom
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit

import club.hellocode.site.common.exception.HcBadRequestException
import club.hellocode.site.common.setting.Settings
import org.apache.commons.codec.digest.DigestUtils
import org.bson.types.ObjectId

import scala.concurrent.duration.{Duration, FiniteDuration}

object Utils {
  private final val LOW = 33
  private final val HIGH = 127

  val formatDate = DateTimeFormatter.ofPattern("YYYY-MM-dd")
  val formatTime = DateTimeFormatter.ofPattern("HH:mm:ss")
  val random = new SecureRandom()

  def require(requirement: Boolean) {
    if (!requirement)
      throw HcBadRequestException("requirement failed")

  }

  @inline
  final def require(requirement: Boolean, message: => Any) {
    if (!requirement)
      throw HcBadRequestException("requirement failed: " + message)
  }

  def validateMobile(mobile: String): Boolean = {
    (mobile ne null) && mobile.length == 11 && mobile.charAt(0) == '1'
  }

  def getFileExtension(filename: String): String = {
    val idx = filename.lastIndexOf('.')
    if (idx == -1) {
      ""
    } else {
      filename.substring(idx + 1)
    }
  }

  /**
   *
   * @param sha
   * @param suffix
   * @return Left: 文件已存在, Right: 文件不存在
   */
  def mkPath(sha: String, suffix: String = null): Either[Path, Path] = {
    val dir = Paths.get(Settings.fileUpload.localPath, sha.substring(0, 2), sha.substring(2, 4))
    if (!Files.isDirectory(dir)) {
      Files.createDirectories(dir)
    }

    val path = dir.resolve(sha + Option(suffix).map("." + _).getOrElse(""))
    if (Files.isRegularFile(path))
      Left(path)
    else
      Right(path)
  }

  def pageOffset(page: Int, size: Int): Int = {
    Utils.require(page > 0)
    Utils.require(size > 0)
    (page - 1) * size
  }

  def generateOid() = ObjectId.get

  def currentTimeSeconds() = System.currentTimeMillis() / 1000

  def randomString(size: Int): String = {
    assert(size > 0, s"size: $size must be > 0")
    (0 until size).map { _ =>
      val c = nextPrintableChar()
      if (c == '"') '-'
      else if (c == '\\') '`'
      else c
    }.mkString
  }

  @inline
  def nextPrintableChar(): Char = {
    (random.nextInt(HIGH - LOW) + LOW).toChar
  }

  @inline
  def randomNextInt(begin: Int, bound: Int) = {
    random.nextInt(bound - begin) + begin
  }

  def sha256(file: File): String = {
    val in = new FileInputStream(file)
    try {
      DigestUtils.sha256Hex(in)
    } finally {
      if (in ne null) {
        in.close()
      }
    }
  }

  def durationFormNow(endAt: ZonedDateTime, seconds: Long = 0L, now: ZonedDateTime = ZonedDateTime.now()): FiniteDuration = {
    require(now.isBefore(endAt))
    Duration(java.time.Duration.between(now, endAt).getSeconds + seconds, TimeUnit.SECONDS)
  }

  def generatePassword(password: String): (Array[Byte], Array[Byte]) = {
    val salt = Array.ofDim[Byte](HsConstant.SALT_SIZE)
    random.nextBytes(salt)
    val digestPassword = DigestUtils.sha256(password.getBytes(HsConstant.UTF8) ++ salt)
    (salt, digestPassword)
  }

  def matchPassword(password: String, salt: Array[Byte], digestPassword: Array[Byte]): Boolean = {
    java.util.Arrays.equals(DigestUtils.sha256(password.getBytes(HsConstant.UTF8) ++ salt), digestPassword)
  }

}
