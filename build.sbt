import sbt.Keys._

val commonSettings: Seq[Setting[_]] = Seq(
  version := s"0.1.0-SNAPSHOT",
  organization := "com.github.cquiroz",
  scalaVersion := "2.11.8",
  crossScalaVersions := Seq("2.10.4", "2.11.8", "2.12.0"),
  scalacOptions ++= Seq("-deprecation", "-feature", "-Xfatal-warnings"),
  exportJars := true,
  publishMavenStyle := true,
  publishArtifact in Test := false,
  publishTo := {
    val nexus = "https://oss.sonatype.org/"
    if (isSnapshot.value)
      Some("snapshots" at nexus + "content/repositories/snapshots")
    else
      Some("releases" at nexus + "service/local/staging/deploy/maven2")
  },
  pomExtra :=
    <url>https://github.com/cquiroz/kuyfi</url>
    <licenses>
      <license>
        <name>BSD-style</name>
        <url>http://www.opensource.org/licenses/bsd-license.php</url>
        <distribution>repo</distribution>
      </license>
    </licenses>
    <scm>
      <url>git@github.com:cquiroz/kuyfi.git</url>
      <connection>scm:git:git@github.com:cquiroz/kuyfi.git</connection>
    </scm>
    <developers>
      <developer>
        <id>cquiroz</id>
        <name>Carlos Quiroz</name>
        <url>https://github.com/cquiroz/</url>
      </developer>
    </developers>
  ,
  pomIncludeRepository := { _ => false }
)

lazy val kuyfi: Project = project.in(file("."))
  .settings(commonSettings: _*)
  .settings(
    name := "kuyfi",
    publish := {},
    publishLocal := {},
    libraryDependencies ++= Seq(
      "org.tpolecat" %% "atto-core"  % "0.5.1",
      "org.tpolecat" %% "atto-compat-scalaz72" % "0.5.1",
      "org.scalaz" %% "scalaz-core" % "7.2.7"
    )
  )