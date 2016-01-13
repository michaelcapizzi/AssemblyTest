import _root_.sbtassembly.AssemblyPlugin.autoImport._
//import sbtassembly.AssemblyPlugin.autoImport.MergeStrategy
import sbtassembly.MergeStrategy

name := "AssemblyTest"

version := "1.0"

scalaVersion := "2.11.7"

ivyScala := ivyScala.value map { _.copy(overrideScalaVersion = true) }      //this overrides competing scala versions?  ???

assemblyJarName in assembly := "testWithProcessors.jar"

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
  "edu.arizona.sista" % "processors_2.11" % "5.5",        //NLP
  "edu.arizona.sista" % "processors_2.11" % "5.5" classifier "models"
)

//trying to add processors and the DEFAULT merge strategy (deduplicate)
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

//trying to add processors and FIRST merge strategy
//  [error] (*:assembly) java.util.zip.ZipException: duplicate entry: META-INF/MANIFEST.MF
//  [error] Total time: 47 s, completed Jan 13, 2016 7:05:15 AM

//trying to add processors and MANINFEST => first, _ => deduplicate
//  [trace] Stack trace suppressed: run last *:assembly for the full output.
//  [error] (*:assembly) deduplicate: different file contents found in the following:
//[error] /home/mcapizzi/.ivy2/cache/edu.arizona.sista/processors_2.11/jars/processors_2.11-5.5-models.jar:META-INF/MANIFEST.MF
//  [error] /home/mcapizzi/.ivy2/cache/edu.arizona.sista/processors_2.11/jars/processors_2.11-5.5.jar:META-INF/MANIFEST.MF
//  [error] /home/mcapizzi/.ivy2/cache/org.scala-lang/scala-reflect/jars/scala-reflect-2.11.6.jar:META-INF/MANIFEST.MF
//  [error] /home/mcapizzi/.ivy2/cache/org.scala-lang.modules/scala-parser-combinators_2.11/bundles/scala-parser-combinators_2.11-1.0.3.jar:META-INF/MANIFEST.MF
//  [error] /home/mcapizzi/.ivy2/cache/com.io7m.xom/xom/jars/xom-1.2.10.jar:META-INF/MANIFEST.MF
//  [error] /home/mcapizzi/.ivy2/cache/xml-apis/xml-apis/jars/xml-apis-1.3.03.jar:META-INF/MANIFEST.MF
//  [error] /home/mcapizzi/.ivy2/cache/xerces/xercesImpl/jars/xercesImpl-2.8.0.jar:META-INF/MANIFEST.MF
//  [error] /home/mcapizzi/.ivy2/cache/xalan/xalan/jars/xalan-2.7.0.jar:META-INF/MANIFEST.MF
//  [error] /home/mcapizzi/.ivy2/cache/org.json4s/json4s-native_2.11/jars/json4s-native_2.11-3.2.11.jar:META-INF/MANIFEST.MF
//  [error] /home/mcapizzi/.ivy2/cache/org.json4s/json4s-core_2.11/jars/json4s-core_2.11-3.2.11.jar:META-INF/MANIFEST.MF
//  [error] /home/mcapizzi/.ivy2/cache/org.json4s/json4s-ast_2.11/jars/json4s-ast_2.11-3.2.11.jar:META-INF/MANIFEST.MF
//  [error] /home/mcapizzi/.ivy2/cache/com.thoughtworks.paranamer/paranamer/jars/paranamer-2.6.jar:META-INF/MANIFEST.MF
//  [error] /home/mcapizzi/.ivy2/cache/org.scala-lang/scalap/jars/scalap-2.11.0.jar:META-INF/MANIFEST.MF
//  [error] /home/mcapizzi/.ivy2/cache/org.scala-lang/scala-compiler/jars/scala-compiler-2.11.0.jar:META-INF/MANIFEST.MF
//  [error] /home/mcapizzi/.ivy2/cache/org.scala-lang.modules/scala-xml_2.11/bundles/scala-xml_2.11-1.0.1.jar:META-INF/MANIFEST.MF
//  [error] /home/mcapizzi/.ivy2/cache/joda-time/joda-time/jars/joda-time-2.7.jar:META-INF/MANIFEST.MF
//  [error] /home/mcapizzi/.ivy2/cache/de.jollyday/jollyday/jars/jollyday-0.4.7.jar:META-INF/MANIFEST.MF
//  [error] /home/mcapizzi/.ivy2/cache/javax.xml.bind/jaxb-api/jars/jaxb-api-2.2.7.jar:META-INF/MANIFEST.MF
//  [error] /home/mcapizzi/.ivy2/cache/com.googlecode.efficient-java-matrix-library/ejml/jars/ejml-0.23.jar:META-INF/MANIFEST.MF
//  [error] /home/mcapizzi/.ivy2/cache/edu.stanford.nlp/stanford-corenlp/jars/stanford-corenlp-3.5.1.jar:META-INF/MANIFEST.MF
//  [error] /home/mcapizzi/.ivy2/cache/edu.stanford.nlp/stanford-corenlp/jars/stanford-corenlp-3.5.1-models.jar:META-INF/MANIFEST.MF
//  [error] /home/mcapizzi/.ivy2/cache/javax.json/javax.json-api/bundles/javax.json-api-1.0.jar:META-INF/MANIFEST.MF
//  [error] /home/mcapizzi/.ivy2/cache/ch.qos.logback/logback-classic/jars/logback-classic-1.0.10.jar:META-INF/MANIFEST.MF
//  [error] /home/mcapizzi/.ivy2/cache/ch.qos.logback/logback-core/jars/logback-core-1.0.10.jar:META-INF/MANIFEST.MF
//  [error] /home/mcapizzi/.ivy2/cache/org.slf4j/slf4j-api/jars/slf4j-api-1.7.10.jar:META-INF/MANIFEST.MF
//  [error] /home/mcapizzi/.ivy2/cache/log4j/log4j/bundles/log4j-1.2.17.jar:META-INF/MANIFEST.MF
//  [error] /home/mcapizzi/.ivy2/cache/nz.ac.waikato.cms.weka/weka-dev/jars/weka-dev-3.7.10.jar:META-INF/MANIFEST.MF
//  [error] /home/mcapizzi/.ivy2/cache/net.sf.squirrel-sql.thirdparty-non-maven/java-cup/jars/java-cup-0.11a.jar:META-INF/MANIFEST.MF
//  [error] /home/mcapizzi/.ivy2/cache/org.pentaho.pentaho-commons/pentaho-package-manager/jars/pentaho-package-manager-1.0.8.jar:META-INF/MANIFEST.MF
//  [error] /home/mcapizzi/.ivy2/cache/net.sf.jopt-simple/jopt-simple/jars/jopt-simple-4.5.jar:META-INF/MANIFEST.MF
//  [error] /home/mcapizzi/.ivy2/cache/de.bwaldvogel/liblinear/jars/liblinear-1.94.jar:META-INF/MANIFEST.MF
//  [error] /home/mcapizzi/.ivy2/cache/tw.edu.ntu.csie/libsvm/jars/libsvm-3.17.jar:META-INF/MANIFEST.MF
//  [error] /home/mcapizzi/.ivy2/cache/org.yaml/snakeyaml/bundles/snakeyaml-1.14.jar:META-INF/MANIFEST.MF
//  [error] /home/mcapizzi/.ivy2/cache/jline/jline/jars/jline-2.12.1.jar:META-INF/MANIFEST.MF

