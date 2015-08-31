package club.hellocode.site.data.repo

import club.hellocode.site.data.DbWordSpec
import club.hellocode.site.data.driver.MyDriver
import MyDriver.api._

import scala.concurrent.Await

/**
 * SchemasTest
 * Created by Yang Jing (yangbajing@gmail.com) on 2015-08-31.
 */
class SchemasTest extends DbWordSpec {

  import schemas._

  "SchemasTest" should {
    "create statements" in {
      tableSchemas.createStatements.foreach(println)
    }

    "create tables" in {
      val f = db.run(tableSchemas.create.transactionally)
      Await.result(f, timeout.duration).shouldBe(())
    }
  }

}
