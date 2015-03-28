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
    "#main *" #> ( "#link [href]" #> list(0)._2 & "#title *" #> list(0)._4 &  {
      list(0)._6 match {
        case v if v.isEmpty => "#image" #> NodeSeq.Empty
        case v => "#image img [src]" #> v
      }
    } & {
      list(0)._7 match {
        case v if v.isEmpty => "#site_name *" #> list(0)._3
        case v => "#site_name *" #> v
      }
    } & "#date *" #> list(0)._8.get.toString) &
      "#list *" #> list.drop(1).map(v => "#link [href]" #> v._2 & "#title *" #> v._4 & "#image [src]" #> {
        v._6 match {
          case v if v.isEmpty => "/classpath/foundation/img/noimage.png"
          case v => v
        }
      } & {
        v._7 match {
          case va if va.isEmpty => "#site_name *" #> v._3
          case va => "#site_name *" #> va
        }
      })
  }
}
