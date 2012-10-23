import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

  val appName         = "CICWebCL"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here
    "org.scalatest" %% "scalatest" % "1.8" % "test"
  )

  lazy val main = PlayProject(appName, appVersion, appDependencies, mainLang = SCALA).settings(
    // Add your own project settings here      
  ).dependsOn(cic)

  lazy val cic = RootProject(file("../CIC"))
}
