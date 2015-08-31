package club.hellocode.site.data

import java.util.concurrent.TimeUnit

import akka.actor.ActorSystem
import akka.util.Timeout
import org.scalatest._
import org.scalatest.concurrent.Futures

/**
 * ServiceWordSpec
 * Created by Yang Jing (yangbajing@gmail.com) on 2015-08-31.
 */
trait ServiceWordSpec extends WordSpec with Matchers with EitherValues with OptionValues with Futures with BeforeAndAfterAll {
  implicit val system = ActorSystem("test")
  implicit val timeout = Timeout(10, TimeUnit.SECONDS)

  override protected def afterAll(): Unit = {
    system.shutdown()
  }
}
