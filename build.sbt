import play.routes.compiler.InjectedRoutesGenerator
import play.sbt.PlayScala

def common = Seq(
	scalaVersion := "2.11.8",
	crossScalaVersions := Seq("2.11.8", "2.12.6"),
	version := "1.0",
	organization := "com.blackmirror"
)

lazy val root = (project in file(".")).
	enablePlugins(PlayScala).
	settings(common: _*).
	settings(
		name := "dongda-web",
		fork in run := true,
		javaOptions += "-Xmx2G"
	)

routesGenerator := InjectedRoutesGenerator
resolvers += Resolver.mavenLocal

libraryDependencies ++= Seq(
	guice,

	"log4j" % "log4j" % "1.2.17",
    "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test,
    "org.specs2" % "specs2_2.11" % "3.7" % Test
)