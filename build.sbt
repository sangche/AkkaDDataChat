name := """DistributedChat"""

version := "1.0"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.8"

val akkaVersion = "2.5.6"

libraryDependencies ++= Seq(
  guice,
  "com.typesafe.akka" %% "akka-remote" % akkaVersion,
  "com.typesafe.akka" %% "akka-cluster" % akkaVersion,
  "com.typesafe.akka" %% "akka-distributed-data" % akkaVersion
)