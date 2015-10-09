package club.hellocode.site.data.domain

import play.api.libs.json.Json

/**
 * 认证参数
 * Created by jingyang on 15/4/22.
 */
case class SignParam(account: String, password: String, captcha: Option[String])

object SignParam {
  implicit val __signParamFormat = Json.format[SignParam]
}
