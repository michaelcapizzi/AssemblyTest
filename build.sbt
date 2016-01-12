import _root_.sbtassembly.AssemblyPlugin.autoImport._
//import sbtassembly.AssemblyPlugin.autoImport.MergeStrategy
import sbtassembly.MergeStrategy

name := "AssemblyTest"

version := "1.0"

scalaVersion := "2.11.7"

assemblyJarName in assembly := "testWithProcessors.jar"

//test in assembly := {}

mainClass in assembly := Some("Test.Main")


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
  "edu.arizona.sista" % "processors_2.11" % "5.5",        //NLP
  "edu.arizona.sista" % "processors_2.11" % "5.5" classifier "models"
)

//trying to add processors and the DEFAULT merge strategy
//  [error] 4 errors were encountered during merge
//  [trace] Stack trace suppressed: run last *:assembly for the full output.
//  [error] (*:assembly) deduplicate: different file contents found in the following:
//[error] /home/mcapizzi/.ivy2/cache/xalan/xalan/jars/xalan-2.7.0.jar:java_cup/runtime/Scanner.class
//[error] /home/mcapizzi/.ivy2/cache/net.sf.squirrel-sql.thirdparty-non-maven/java-cup/jars/java-cup-0.11a.jar:java_cup/runtime/Scanner.class
//[error] deduplicate: different file contents found in the following:
//[error] /home/mcapizzi/.ivy2/cache/xalan/xalan/jars/xalan-2.7.0.jar:java_cup/runtime/Symbol.class
//[error] /home/mcapizzi/.ivy2/cache/net.sf.squirrel-sql.thirdparty-non-maven/java-cup/jars/java-cup-0.11a.jar:java_cup/runtime/Symbol.class
//[error] deduplicate: different file contents found in the following:
//[error] /home/mcapizzi/.ivy2/cache/xalan/xalan/jars/xalan-2.7.0.jar:java_cup/runtime/lr_parser.class
//[error] /home/mcapizzi/.ivy2/cache/net.sf.squirrel-sql.thirdparty-non-maven/java-cup/jars/java-cup-0.11a.jar:java_cup/runtime/lr_parser.class
//[error] deduplicate: different file contents found in the following:
//[error] /home/mcapizzi/.ivy2/cache/xalan/xalan/jars/xalan-2.7.0.jar:java_cup/runtime/virtual_parse_stack.class
//[error] /home/mcapizzi/.ivy2/cache/net.sf.squirrel-sql.thirdparty-non-maven/java-cup/jars/java-cup-0.11a.jar:java_cup/runtime/virtual_parse_stack.class

