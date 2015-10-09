package club.hellocode.site

import javax.inject.{Inject, Singleton}

import club.hellocode.site.data.repo.Schema
import com.typesafe.scalalogging.StrictLogging
import play.api.inject.ApplicationLifecycle

import scala.concurrent.Future

/**
 * Hello System
 * Created by Yang Jing (yangbajing@gmail.com) on 2015-10-09.
 */
@Singleton
class HelloSystem @Inject()(lifecycle: ApplicationLifecycle,
                            schema: Schema) extends StrictLogging {

  logger.info("Hello System startup")

  lifecycle.addStopHook(() => Future.successful {
    logger.info("close db ....")
    schema.db.close()
  })

}
