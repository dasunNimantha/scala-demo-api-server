import slick.jdbc.H2Profile.api._

import scala.concurrent.{ExecutionContext, Future}

object DB {
  val db = Database.forConfig("h2mem1")
  private val users = TableQuery[Users]

  def setupDB()(implicit ctx: ExecutionContext): Future[Unit] = {
    val setup = DBIO.seq(
      users.schema.create,
      users += User(1, "Alice"),
      users += User(2, "Bob"),
      users += User(3, "Charlie")
    )

    db.run(setup)
  }

  def getUsers()(implicit ctx: ExecutionContext): Future[Seq[User]] = {
    db.run(users.result)
  }

  def createUser(user: User)(implicit ctx: ExecutionContext): Future[Int] = {
    db.run(users += user)
  }

  def deleteUser(id: Int)(implicit ctx: ExecutionContext): Future[Int] = {
    db.run(users.filter(_.id === id).delete)
  }

}
