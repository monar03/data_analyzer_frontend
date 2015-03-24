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
    "#panel *" #> SiteInformationDao.getAll.map(v => "#link [href]" #> v._2 & "#title *" #> v._4 & "#image [src]" #> v._6)
  }
}
