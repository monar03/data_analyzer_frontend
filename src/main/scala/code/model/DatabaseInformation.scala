package code.model

/**
 * Created by motonari on 15/03/22.
 */
trait DatabaseInformation {
  protected val dsn = "jdbc:mysql://localhost/datafetcher?user=root&password="
  protected val driver = "com.mysql.jdbc.Driver"
}
