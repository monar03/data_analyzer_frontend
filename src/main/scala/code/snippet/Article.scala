package code.snippet

import code.model.SiteInformationDao

import scala.xml.{NodeSeq, Text}
import net.liftweb.util._
import net.liftweb.common._
import java.util.Date
import code.lib._
import Helpers._

/**
 * Created by motonari on 15/03/25.
 */
class Article {
  def listTop = {
    val list = SiteInformationDao.all
    "#main *" #> ( "#link [href]" #> list(0)._2 & "#title *" #> list(0)._4 &  "#image [src]" #> {
      list(0)._6 match {
        case v if v.isEmpty => "/classpath/foundation/img/noimage.png"
        case v => v
      }
    } ) &
      "#list *" #> list.drop(1).map(v => "#link [href]" #> v._2 & "#title *" #> v._4 & "#image [src]" #> {
        v._6 match {
          case v if v.isEmpty => "/classpath/foundation/img/noimage.png"
          case v => v
        }
      })
  }
}
