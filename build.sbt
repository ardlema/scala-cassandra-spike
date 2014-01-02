name := "scala-cassandra-spike"

organization := "ardlema"

version := "0.0.1"

scalaVersion := "2.10.3"

val twitterRepo = "Twitter's Repository" at "http://maven.twttr.com/"
val cassie = "com.twitter" % "cassie" % "0.19.0"

resolvers ++= Seq(twitterRepo)

libraryDependencies ++= Seq(
  "org.scalatest" % "scalatest_2.10" % "2.0" % "test" withSources() withJavadoc(),
  "org.scalacheck" %% "scalacheck" % "1.10.0" % "test" withSources() withJavadoc(),
  "me.prettyprint" % "hector-core" % "1.0-5"
)

initialCommands := "import ardlema.scalacassandraspike._"

