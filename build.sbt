ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.12.18"

lazy val kamonVersion = "2.7.3"

Docker / packageName := "sample-scala-api"
dockerBaseImage := "eclipse-temurin:11-jre-jammy"
dockerExposedPorts := Seq(8080,5266)

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http" % "10.2.4",
  "com.typesafe.akka" %% "akka-http-spray-json" % "10.2.4",
  "com.typesafe.akka" %% "akka-stream" % "2.8.6",
  "com.typesafe.akka" %% "akka-actor-typed" % "2.8.6",
  "com.h2database" % "h2" % "2.2.224",
  "com.typesafe.slick" %% "slick" % "3.5.1",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.5.1",
  "io.kamon" %% "kamon-bundle" % kamonVersion,
  "io.kamon" %% "kamon-jaeger" % "2.7.2",
  "ch.qos.logback" % "logback-classic" % "1.5.6",
)

enablePlugins(JavaAppPackaging, JavaAgent)

javaAgents += "io.kamon" % "kanela-agent" % "1.0.18"