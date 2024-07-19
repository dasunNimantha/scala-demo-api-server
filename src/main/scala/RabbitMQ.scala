import com.rabbitmq.client.{Channel, Connection, ConnectionFactory}
import com.typesafe.config.ConfigFactory

import scala.util.{Failure, Success, Try}

object RabbitMQ {
  private val config = ConfigFactory.load()
  private val rabbitMQConfig = config.getConfig("rabbitmq")

  def setupRabbitMQ(): Unit = {
    initializeConnection() match {
      case Failure(exception) => throw new Exception("Failed to initialize RabbitMQ connection: " + exception.getMessage, exception)
      case Success(channel) =>
        try {
          channel.queueDeclare("queueName", false, false, false, null)
        } finally {
          channel.close()
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
}
