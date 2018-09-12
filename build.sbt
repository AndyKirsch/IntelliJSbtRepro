import sbt.Keys.libraryDependencies
import sbt._

val play_2_3 = "2.3.10"
val play_2_5 = "2.5.10"
val play_2_7 = "2.7.0-M1"

def commonProject(id: String, path: String): Project = {
  Project(id, file(path))
}
def playProject(includePlayJsonVersion: String): Project = {
  val suffix = includePlayJsonVersion match {
    case `play_2_3` => "23"
    case `play_2_5` => "25"
    case `play_2_7` => "27"
  }
  val projectId = s"play$suffix"
  val path = includePlayJsonVersion match {
    case `play_2_3` => "./shared"
    case `play_2_5` => "./play25"
    case `play_2_7` => "./play27"
  }
  commonProject(projectId, path)


}

lazy val play23 = playProject(play_2_3)
  .in(file("./shared"))
  //.settings(projectID := ModuleID("play23")
  .configs(IntegrationTest.extend(Test))
  .settings(Defaults.itSettings: _* )
  .settings(
    //    sourceDirectory in Compile := file(s"$projectPath/src/main"),
    //   sourceDirectory in IntegrationTest := file(s"$projectPath/src/it"),
    libraryDependencies +=  "org.scalatest" %% "scalatest" % "3.0.5" % Test,
    libraryDependencies +=  "org.scalatest" %% "scalatest" % "3.0.5" % IntegrationTest
  )

lazy val play25 = playProject(play_2_5)
  .in(file("./play25"))
  .configs(IntegrationTest.extend(Test))
  .settings(Defaults.itSettings: _* )
  .settings(
    sourceDirectory := (sourceDirectory in play23).value,
    (sourceDirectory in Test) := (sourceDirectory in Test in play23).value,
    (sourceDirectory in IntegrationTest) := (sourceDirectory in IntegrationTest in play23).value,
    libraryDependencies +=  "org.scalatest" %% "scalatest" % "3.0.5" % Test,
    libraryDependencies +=  "org.scalatest" %% "scalatest" % "3.0.5" % IntegrationTest
  )

//lazy val play27 = playProject(play_2_7)
