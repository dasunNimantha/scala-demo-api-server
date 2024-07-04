import Routes.routes
import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http

import scala.concurrent.ExecutionContextExecutor
import scala.util.{Failure, Success}

object WebServer {
  implicit val system: ActorSystem[Any] = ActorSystem(Behaviors.empty, "sample-api-system")
  implicit val executionContext: ExecutionContextExecutor = system.executionContext

  def startServer(): Unit = {
    val bindingFuture = Http().newServerAt("localhost", 8080).bind(routes)

    bindingFuture.onComplete {
      case Success(binding) =>
        println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
        scala.io.StdIn.readLine()
        binding.unbind().onComplete(_ => system.terminate())
      case Failure(exception) =>
        println(s"Failed to bind HTTP endpoint, terminating system. ${exception.getMessage}")
        system.terminate()
    }

    scala.io.StdIn.readLine()
    bindingFuture
      .flatMap(_.unbind())
      .onComplete(_ => system.terminate())
  }

}
