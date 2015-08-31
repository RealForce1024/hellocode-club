package club.hellocode.site.data.repo

import play.api.libs.concurrent.Execution

trait BaseRepo {
  implicit protected def executionContext = Execution.defaultContext
}
