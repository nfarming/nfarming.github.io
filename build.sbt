
import scala.scalajs.sbtplugin.ScalaJSPlugin.ScalaJSKeys._

name := "natural-farming"

version := "1.0"

scalaVersion := "2.11.2"

scalaJSSettings

persistLauncher := true

persistLauncher in Test := false

resolvers +=
  "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

libraryDependencies ++= Seq("com.greencatsoft" %%% "scalajs-angular" % "0.3-SNAPSHOT",
  "com.github.benhutchison" %%% "prickle" % "1.1.0",
  "com.lihaoyi" %%% "upickle" % "0.2.5",
  "com.github.benhutchison" %%% "prickle" % "1.1.0",
  "com.github.japgolly.scalajs-react" %%% "core" % "0.5.2-SNAPSHOT")

// React itself
//   (react-with-addons.js can be react.js, react.min.js, react-with-addons.min.js)
jsDependencies ++= Seq("org.webjars" % "angularjs" % "1.3.0" / "angular.min.js",
  "org.webjars" % "angularjs" % "1.3.0" / "angular-route.min.js" dependsOn  "angular.min.js",
  "org.webjars" % "react" % "0.11.1" / "react-with-addons.js" commonJSName "React"
  )
//  )

// creates single js resource file for easy integration in html page
skip in packageJSDependencies := false


// copy  javascript files to js folder,that are generated using fastOptJS/fullOptJS

crossTarget in (Compile, fullOptJS) := file("js")

crossTarget in (Compile, fastOptJS) := file("js")

crossTarget in (Compile, packageJSDependencies) := file("js")

crossTarget in (Compile, packageScalaJSLauncher) := file("js")

artifactPath in (Compile, fastOptJS) := ((crossTarget in (Compile, fastOptJS)).value /
  ((moduleName in fastOptJS).value + "-opt.js"))
  