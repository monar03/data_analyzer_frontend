package code.model

import java.sql.Timestamp
import java.util.Date

import scala.slick.driver.MySQLDriver.simple._
import scala.slick.lifted.ProvenShape

/**
 * Created by motonari on 15/03/22.
 */
object SiteInformationDao extends SiteInformationReader with SiteInformationWriter

trait SiteInformationTable extends DatabaseInformation {
  val siteinformation = TableQuery[SiteInformation]

  try {
    Database.forURL(dsn, driver = driver) withSession {
      implicit session => siteinformation.ddl.create
    }
  } catch {
    case e:Exception => println(e.getMessage)
  }
}

/**
 * サイト情報取得
 */
trait SiteInformationReader extends SiteInformationTable {
  def all = Database.forURL(dsn, driver = driver) withSession {
    implicit session => siteinformation.drop(0).take(5).list
  }

  def host(hostname:String) = Database.forURL(dsn, driver=driver) withSession{
    implicit session => siteinformation.filter(_.domain === hostname).sortBy(_.date.desc).drop(0).take(5).list
  }

  def hostlist = Database.forURL(dsn, driver = driver) withSession {
    implicit session => siteinformation.sortBy(_.date.desc).map(row => (row.domain, row.site_name)).list.distinct.drop(0).take(6)
  }

}

/**
 * サイト情報更新
 */
trait SiteInformationWriter extends SiteInformationTable {
  def set(id:String, url:String, domain:String, title:String, description:String, image:String, site_name:String) = Database.forURL(dsn, driver = driver) withSession {
    implicit session => siteinformation +=(id, url, domain, title, description, image, site_name, Option(new Timestamp(new Date().getTime)))
  }
}

class SiteInformation(tag: Tag) extends Table[(String, String, String, String, String, String, String, Option[Timestamp])](tag, "site_data"){
  /**
   * ID
   * @return
   */
  def id = column[String]("id", O.PrimaryKey)

  /**
   * URL
   * @return
   */
  def url = column[String]("url")

  /**
   * ドメイン
   * @return
   */
  def domain = column[String]("host")

  /**
   * タイトル
   * @return
   */
  def title = column[String]("title", O.DBType("VARCHAR(2048)"))

  /**
   * 詳細
   * @return
   */
  def description = column[String]("description", O.DBType("VARCHAR(2048)"))

  /**
   * イメージ
   * @return
   */
  def image = column[String]("image")

  /**
   * イメージ
   * @return
   */
  def site_name = column[String]("site_name")

  /**
   * 取得日時
   * @return
   */
  def date = column[Option[Timestamp]]("date")

  def * : ProvenShape[(String, String, String, String, String, String, String, Option[Timestamp])] = (id, url, domain, title, description, image, site_name, date)
}
