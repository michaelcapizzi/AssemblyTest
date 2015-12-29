import _root_.sbtassembly.AssemblyPlugin.autoImport._

name := "AssemblyTest"

version := "1.0"

scalaVersion := "2.11.7"

assemblyJarName in assembly := "test.jar"

//test in assembly := {}

mainClass in assembly := Some("Test.Main")

//val meta = """META.INF(.)*""".r

//assemblyMergeStrategy in assembly := {
//  //  case "META-INF/MANIFEST.MF" => MergeStrategy.first
//  case PathList("META-INF", "MANIFEST.MF") => MergeStrategy.discard
//  case _ => MergeStrategy.concat
//}

