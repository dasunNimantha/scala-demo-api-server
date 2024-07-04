ThisBuild / version := "0.1.0-SNAPSHOT"


libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http" % "10.5.3",
  "com.typesafe.akka" %% "akka-http-spray-json" % "10.5.3",
  "com.typesafe.akka" %% "akka-stream" % "2.8.6",
  "com.typesafe.akka" %% "akka-actor-typed" % "2.8.6",
  "com.h2database" % "h2" % "2.2.224",
  "com.typesafe.slick" %% "slick" % "3.5.1",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.5.1",
  "io.kamon" %% "kamon-core" % "2.7.3",
  "io.kamon" %% "kamon-bundle" % "2.7.3",
  "io.kamon" %% "kamon-jaeger" % "2.7.2",
  "ch.qos.logback" % "logback-classic" % "1.5.6",
)

