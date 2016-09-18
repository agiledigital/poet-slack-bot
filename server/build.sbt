name := "play-java"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs
)

libraryDependencies += "edu.stanford.nlp" % "stanford-corenlp" % "3.6.0"
libraryDependencies += "edu.stanford.nlp" % "stanford-corenlp" % "3.6.0" classifier "models"


// https://mvnrepository.com/artifact/com.google.code.gson/gson
libraryDependencies += "com.google.code.gson" % "gson" % "2.7"


// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator
