name := """scala-99"""

version := "1.0"

scalaVersion := "2.11.5"

libraryDependencies ++=
  Seq("org.scalatest" %% "scalatest" % "2.2.1" % "test",
    "org.scalacheck" %% "scalacheck" % "1.11.5" % "test",
    "org.mockito" % "mockito-core" % "1.9.5" % "test",
    "joda-time" % "joda-time" % "2.7" % "compile")