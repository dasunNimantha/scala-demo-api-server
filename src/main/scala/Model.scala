import slick.jdbc.H2Profile.api._

case class User(id: Int, name: String)

class Users(tag: Tag) extends Table[User](tag, "USERS") {
  def * = (id, name) <> (User.tupled, User.unapply)

  private def id = column[Int]("ID", O.PrimaryKey, O.AutoInc)

  private def name = column[String]("NAME")
}
