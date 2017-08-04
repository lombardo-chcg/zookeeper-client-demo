name := "sbt-starter"

version := "0.0.1"

mainClass in assembly := Some("com.lombardo.app.Main")

// https://mvnrepository.com/artifact/org.apache.zookeeper/zookeeper
libraryDependencies += "org.apache.zookeeper" % "zookeeper" % "3.4.10"

scalaVersion := "2.12.1"
