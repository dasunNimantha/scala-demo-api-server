import slick.jdbc.H2Profile.api._

case class User(id: Int, name: String)

class Users(tag: Tag) extends Table[User](tag, "USERS") {
  def * = (id, name) <> (User.tupled, User.unapply)

  def id = column[Int]("ID", O.PrimaryKey, O.AutoInc)

  def name = column[String]("NAME")
}
