package club.hellocode.site.data

import club.hellocode.site.data.repo.Schema

/**
 * DbWordSpec
 * Created by Yang Jing (yangbajing@gmail.com) on 2015-08-31.
 */
abstract class DbWordSpec extends ServiceWordSpec {
  val schemas = new Schema()

  override protected def afterAll(): Unit = {
    schemas.db.close()
    super.afterAll()
  }
}
