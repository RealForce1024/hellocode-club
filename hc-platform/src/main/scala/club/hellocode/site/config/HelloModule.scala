package club.hellocode.site.config

import club.hellocode.site.HelloSystem
import com.google.inject.AbstractModule

/**
 * Hello module，随系统启动
 * Created by Yang Jing (yangbajing@gmail.com) on 2015-10-09.
 */
class HelloModule extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[HelloSystem]).asEagerSingleton()
  }
}
