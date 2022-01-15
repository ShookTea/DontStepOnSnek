name := "DontStepOnSnek"

version := "0.1"

scalaVersion := "2.13.8"

lazy val commonSettings = Seq(
  organization := "eu.shooktea",
  version := "0.1",
)


Compile / mainClass := Some("eu.shooktea.dsos.Main")

lazy val app = (project in file(".")).
  settings(commonSettings: _*).
  settings(
    name := "backend"
  ).
  enablePlugins(AssemblyPlugin)

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}

resolvers in Global ++= Seq(
  "Sbt plugins"                   at "https://dl.bintray.com/sbt/sbt-plugin-releases",
  "Maven Central Server"          at "https://repo1.maven.org/maven2",
  "TypeSafe Repository Releases"  at "https://repo.typesafe.com/typesafe/releases/",
  "TypeSafe Repository Snapshots" at "https://repo.typesafe.com/typesafe/snapshots/",
)
