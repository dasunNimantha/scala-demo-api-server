import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route

import scala.concurrent.ExecutionContext.Implicits.global

object Routes extends JsonSupport {
  val routes: Route = concat(
    path("users") {
      get {
        complete(RequestHandler.getUsers())
      }
    }
  )
}
