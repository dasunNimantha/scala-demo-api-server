import DB.setupDB

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

object Main {

  def main(args: Array[String]): Unit = {
    setupDB().onComplete {
      case Success(_) => println("Database setup successful")
      case Failure(exception) => println(s"Database setup failed: ${exception.getMessage}")
    }
    WebServer.startServer()
  }
}
