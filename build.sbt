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
  val projectPath = "shared"
  commonProject(projectId, projectId)
    .configs(IntegrationTest.extend(Test))
    .settings(Defaults.itSettings: _* )
    .settings(
      sourceDirectory := file(s"$projectPath/src"),
      Compile / sourceDirectory := file(s"$projectPath/src/main"),
      Test / sourceDirectory := file(s"$projectPath/src/test"),
      IntegrationTest / sourceDirectory := file(s"$projectPath/src/it"),
      libraryDependencies +=  "org.scalatest" %% "scalatest" % "3.0.5" % Test,
      libraryDependencies +=  "org.scalatest" %% "scalatest" % "3.0.5" % IntegrationTest
    )
}

lazy val play23 = playProject(play_2_3)

lazy val play25 = playProject(play_2_5)

lazy val play27 = playProject(play_2_7)
