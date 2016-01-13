import _root_.sbtassembly.AssemblyPlugin.autoImport._
//import sbtassembly.AssemblyPlugin.autoImport.MergeStrategy
import sbtassembly.MergeStrategy

name := "AssemblyTest"

version := "1.0"

scalaVersion := "2.11.7"

ivyScala := ivyScala.value map { _.copy(overrideScalaVersion = true) }      //this overrides competing scala versions?  ???

assemblyJarName in assembly := "testWithProcessorswithCommonsMath.jar"

//test in assembly := {}

mainClass in assembly := Some("Test.Main")

assemblyExcludedJars in assembly := {
  val cp = (fullClasspath in assembly).value
  cp filter {_.data.getName == "java-cup-0.11a.jar"}
}

assemblyMergeStrategy in assembly := {
    case PathList("javax", "servlet", xs @ _*)         => MergeStrategy.first
    case PathList(ps @ _*) if ps.last endsWith ".html" => MergeStrategy.first
    case "application.conf"                            => MergeStrategy.concat
    case "unwanted.txt"                                => MergeStrategy.discard
    case x =>
      val oldStrategy = (assemblyMergeStrategy in assembly).value
      oldStrategy(x)
}

libraryDependencies ++= Seq(
  "edu.arizona.sista" % "processors_2.11" % "5.5",        //NLP         //required exclude java-cup-0.11a.jar
  "edu.arizona.sista" % "processors_2.11" % "5.5" classifier "models",
  "org.apache.commons" % "commons-math3" % "3.3"
)
