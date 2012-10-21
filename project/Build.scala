import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

/*	lazy val cicWebCl = Project(id = "CICWebCL", base = file("D:/Studium/Projekte/Webanwendungen/Workspace/CICWebCL")) dependsOn(cic)

	lazy val cic = Project(id = "CIC-Project", base = file("D:/Studium/Projekte/Webanwendungen/Workspace/CIC"))*/

    val appName         = "CICWebCL"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
      // Add your project dependencies here,
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = SCALA).settings(
      // Add your own project settings here      
    )

}
