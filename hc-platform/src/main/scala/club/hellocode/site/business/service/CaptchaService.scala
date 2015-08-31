package club.hellocode.site.business.service

import java.awt.Color
import java.io.ByteArrayOutputStream
import javax.inject.{Inject, Singleton}

import club.hellocode.site.common.Utils
import com.github.bingoohuang.patchca.color.ColorFactory
import com.github.bingoohuang.patchca.custom.ConfigurableCaptchaService
import com.github.bingoohuang.patchca.filter.predefined._
import com.github.bingoohuang.patchca.utils.encoder.EncoderHelper
import com.github.bingoohuang.patchca.word.RandomWordFactory

@Singleton
class CaptchaService @Inject()(captchaService: ConfigurableCaptchaService) {
  private val c = Array.ofDim[Int](3)
  private val wf = new RandomWordFactory()

  captchaService.setColorFactory(new ColorFactory {
    override def getColor(index: Int): Color = {
      val tag = Utils.random.nextInt(c.length)
      var i = 0
      while (i < c.length) {
        c(i) = if (tag == i) Utils.random.nextInt(71) else Utils.random.nextInt(256)
        i += 1
      }
      new Color(c(0), c(1), c(2))
    }
  })

  wf.setCharacters("0123456789abcefghkmpqrtvwxyz")
  wf.setMaxLength(4)
  wf.setMinLength(4)
  captchaService.setWordFactory(wf)

  def createCaptcha(format: String = "png"): (String, Array[Byte]) = {
    val out = new ByteArrayOutputStream()
    try {
      Utils.random.nextInt(5) match {
        case 0 =>
          captchaService.setFilterFactory(new CurvesRippleFilterFactory(captchaService.getColorFactory))
        case 1 =>
          captchaService.setFilterFactory(new MarbleRippleFilterFactory)
        case 2 =>
          captchaService.setFilterFactory(new DoubleRippleFilterFactory)
        case 3 =>
          captchaService.setFilterFactory(new WobbleRippleFilterFactory)
        case 4 =>
          captchaService.setFilterFactory(new DiffuseRippleFilterFactory)
      }

      val captcha = EncoderHelper.getChallangeAndWriteImage(captchaService, format, out)
      (captcha, out.toByteArray)
    } finally {
      out.close()
    }
  }
}
