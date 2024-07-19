import kamon.Kamon.span

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object RequestHandler {
  def getUsers: Future[Seq[UserData]] = {
    for {
      users <- DB.getUsers()
      userData = span("user-mapper"){users.map(user => UserData(user.id, user.name))}
      _ <- sampleAsyncFunction()
    } yield userData
  }

  private def sampleAsyncFunction(): Future[Unit] = {
    span("sample-future-function"){
      for {
        _ <- DB.createUser(User(0, "sample"))
        _ = Thread.sleep(100)

      } yield ()
    }
  }
}
