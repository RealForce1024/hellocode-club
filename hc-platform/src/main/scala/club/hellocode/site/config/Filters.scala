package club.hellocode.site.config

import javax.inject.Inject

import play.api.http.HttpFilters
import play.api.mvc.EssentialFilter

/**
 * Filters
 * Created by Yang Jing (yangbajing@gmail.com) on 2015-10-09.
 */
class Filters @Inject()(helloFilter: HelloFilter) extends HttpFilters {
  override def filters: Seq[EssentialFilter] =
    Seq(helloFilter)
}
