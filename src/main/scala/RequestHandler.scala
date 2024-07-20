import RabbitMQ.sendMessage
import com.scala.testing.person.Person
import kamon.Kamon.span

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object RequestHandler {
  def getUsers: Future[Seq[UserData]] = {
    for {
      users <- DB.getUsers()
      userData = span("user-mapper") {
        users.map(user => UserData(user.id, user.name))
      }
      _ <- sampleAsyncFunction()
      _ <- sendMessage(Person(Option("Alice"), Option(30)))
    } yield userData
  }

  private def sampleAsyncFunction(): Future[Unit] = {
    span("sample-future-function") {
      for {
        _ <- DB.createUser(User(0, "sample"))
        _ = Thread.sleep(100)

      } yield ()
    }
  }
}
