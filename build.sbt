name := "dominolab"

version := "0.1"

scalaVersion := "2.12.7"

val akkaVersion = "2.6.0"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % akkaVersion
)