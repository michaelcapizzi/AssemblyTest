import _root_.sbtassembly.AssemblyPlugin.autoImport._
import sbtassembly.MergeStrategy

name := "AssemblyTest"

version := "1.0"

scalaVersion := "2.11.7"

ivyScala := ivyScala.value map { _.copy(overrideScalaVersion = true) }      //this overrides competing scala versions?  ???

assemblyJarName in assembly := "testWithProcessorswithCommonsMathwithBreezewithTikawithJSON4S.jar"

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
  "edu.arizona.sista" % "processors_2.11" % "5.5",       //required exclude java-cup-0.11a.jar
  "edu.arizona.sista" % "processors_2.11" % "5.5" classifier "models",
  "org.apache.commons" % "commons-math3" % "3.3",
  "org.scalanlp" % "breeze-natives_2.11" % "0.11.2",
//  "org.scalanlp" % "breeze_2.11" % "0.11.2",
  "org.scalanlp" %% "breeze-viz" % "0.11.2",
  "org.scalanlp" % "nak_2.11" % "1.3",
  "com.github.fommil.netlib" % "all" % "1.1.2",
  "com.github.fommil.netlib" % "native_ref" % "1.1",
//  "org.apache.tika" % "tika" % "1.11",
//  "org.apache.tika" % "tika-parsers" % "1.11",
  "org.apache.tika" % "tika-core" % "1.11",
  "org.json4s" % "json4s-native_2.11" % "3.3.0",
  "org.json4s" % "json4s-jackson_2.11" % "3.3.0"
)
