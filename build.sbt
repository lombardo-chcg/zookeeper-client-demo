name := "sbt-starter"

version := "0.0.1"

mainClass in assembly := Some("com.lombardo.app.Main")

libraryDependencies ++= Seq(
  "org.apache.zookeeper" % "zookeeper" % "3.4.10",
  "org.apache.curator" % "curator-framework" % "2.12.0"
)

scalaVersion := "2.12.1"
