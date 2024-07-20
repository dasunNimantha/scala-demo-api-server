import com.rabbitmq.client.{Channel, Connection, ConnectionFactory}
import com.typesafe.config.ConfigFactory
import scalapb.GeneratedMessage

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success, Try}

object RabbitMQ {
  private val config = ConfigFactory.load()
  private val rabbitMQConfig = config.getConfig("rabbitmq")
  private val queueName = "sample-que"
  private var channel: Option[Channel] = None

  def setupRabbitMQ(): Unit = {
    initializeConnection() match {
      case Failure(exception) => throw new Exception("Failed to initialize RabbitMQ connection: " + exception.getMessage, exception)
      case Success(ch) =>
        channel = Some(ch)
        try {
          ch.queueDeclare(queueName, false, false, true, null)
          println(s"RabbitMQ setup complete on channel: $ch")
        } catch {
          case ex: Exception => println(s"Failed to declare queue: ${ex.getMessage}")
        }
    }
  }

  private def initializeConnection(): Try[Channel] = {
    getConnection match {
      case Success(connection) =>
        Try(connection.createChannel()).recoverWith {
          case ex: Exception =>
            connection.close()
            Failure(ex)
        }
      case Failure(ex) => Failure(ex)
    }
  }

  private def getConnection: Try[Connection] = Try {
    val connectionFactory = new ConnectionFactory()
    connectionFactory.setUri(rabbitMQConfig.getString("uri"))
    connectionFactory.setPort(rabbitMQConfig.getInt("port"))
    connectionFactory.setUsername(rabbitMQConfig.getString("username"))
    connectionFactory.setPassword(rabbitMQConfig.getString("password"))
    connectionFactory.newConnection()
  }

  def sendMessage(message: GeneratedMessage)(implicit ec: ExecutionContext): Future[Unit] = {
    Future {
      channel match {
        case Some(ch) =>
          try {
            val serializedMessage = message.toByteArray
            ch.basicPublish("", "sample-que", null, serializedMessage)
            println(s"Sent protobuf message: ${message.toString}")
          } catch {
            case ex: Exception =>
              println(s"Failed to send message: ${ex.getMessage}")
              throw ex
          }
        case None =>
          val errorMsg = "Channel not initialized. Call setupRabbitMQ() first."
          println(errorMsg)
          throw new Exception(errorMsg)
      }
    }
  }
}
