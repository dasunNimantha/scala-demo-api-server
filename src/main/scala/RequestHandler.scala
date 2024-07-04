import scala.concurrent.{ExecutionContext, Future}

object RequestHandler {
  def getUsers()(implicit ctx: ExecutionContext): Future[Seq[UserData]] = {
    for {
      users <- DB.getUsers()
      userData = users.map(user => UserData(user.id, user.name))
    } yield userData
  }
}
