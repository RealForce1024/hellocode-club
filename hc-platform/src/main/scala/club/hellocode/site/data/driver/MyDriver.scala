package club.hellocode.site.data.driver

import javax.inject.Singleton

import club.hellocode.site.common.HsConstant
import club.hellocode.site.common.enums.GenderType
import com.github.tminglei.slickpg._
import com.github.tminglei.slickpg.utils.SimpleArrayUtils
import org.bson.types.ObjectId
import play.api.libs.json.{JsValue, Json}
import slick.driver.JdbcDriver

trait MyColumnTypes {
  this: JdbcDriver =>

  trait MyColumnsAPI {
    self: API =>
    implicit val __objectIdTypeColumn = MappedColumnType.base[ObjectId, String](_.toString, new ObjectId(_))
    implicit val __genderTypeColumn = MappedColumnType.base[GenderType.Value, String](_.toString, GenderType.withName)
  }

}

trait BaseDriver
  extends ExPostgresDriver
  with PgDate2Support
  with PgHStoreSupport
  with PgPlayJsonSupport
  with PgArraySupport
  //with PgRangeSupport
  //with PgSearchSupport
  //with PgPostGISSupport
  with MyColumnTypes {
  override val pgjson = "jsonb"
  override val api = MyAPI

  object MyAPI
    extends API
    with DateTimeImplicits
    with HStoreImplicits
    with JsonImplicits
    with ArrayImplicits
    //  with RangeImplicits
    //  with SearchImplicits
    //  with PostGISImplicits
    with MyColumnsAPI {
    implicit val strListTypeMapper = new SimpleArrayJdbcType[String]("text").to(_.toList)
    implicit val json4sJsonArrayTypeMapper =
      new AdvancedArrayJdbcType[JsValue](pgjson,
        (s) => SimpleArrayUtils.fromString[JsValue](Json.parse)(s).orNull,
        (v) => SimpleArrayUtils.mkString[JsValue](_.toString())(v)
      ).to(_.toList)

  }

  object SqlTypes {
    val OID = "char(" + HsConstant.OID_LENGTH + ")"
  }

}

@Singleton
class MyDriver extends BaseDriver

object MyDriver extends BaseDriver
