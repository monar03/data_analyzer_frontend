package code.model

import java.sql.Timestamp
import java.util.Date

import scala.slick.driver.MySQLDriver.simple._
import scala.slick.lifted.ProvenShape

/**
 * Created by motonari on 15/03/22.
 */
object SiteListDao extends SiteListReader

trait SiteListTable extends DatabaseInformation {
  val sitelist = TableQuery[SiteList]

  try {
    Database.forURL(dsn, driver = driver) withSession {
      implicit session => sitelist.ddl.create
    }
  } catch {
    case e:Exception => println(e.getMessage)
  }
}

/**
 * サイト情報取得
 */
trait SiteListReader extends SiteListTable {
  def all = Database.forURL(dsn, driver = driver) withSession {
    implicit session => sitelist.filter(_.site_name =!= "").drop(0).take(4).list
  }
}

class SiteList(tag: Tag) extends Table[(String, String)](tag, "site_list"){
  /**
   * ドメイン
   * @return
   */
  def domain = column[String]("host")

  /**
   * イメージ
   * @return
   */
  def site_name = column[String]("site_name")

  def * : ProvenShape[(String, String)] = (domain, site_name)
}
