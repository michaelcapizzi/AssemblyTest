package Test

import java.io.File
import java.nio.file.FileSystems
import java.security.CodeSource
import java.util.Collections
import java.util.jar.{JarEntry, JarFile}
import java.util.zip.ZipInputStream

/**
 * Created by mcapizzi on 10/15/15.
 */
object Main {
  def main(args: Array[String]) = {
    println("This works.")

    val dir1 = getClass.getResource("/dir/")

    val dir2 = getClass.getResource("/dir2/")

//    println("Here is the path to resources: " + resources)
    println("Here is the path to dir: " + dir1)  // jar:file:/media/mcapizzi/data/Github/AssemblyTest/target/scala-2.11/testResources.jar!/dir/
    println("Here is the path to dir2: " + dir2)  //jar:file:/media/mcapizzi/data/Github/AssemblyTest/target/scala-2.11/testResources.jar!/dir2/
//    println("Here is dir with URI: " + dir1.toURI)   // jar:file:/media/mcapizzi/data/Github/AssemblyTest/target/scala-2.11/testResources.jar!/dir/

    //prints a given resource
    val samp1 = scala.io.Source.fromInputStream(getClass.getResourceAsStream("/dir/sample1.txt")).getLines.mkString
    val samp2 = scala.io.Source.fromInputStream(getClass.getResourceAsStream("/dir/sample2.txt")).getLines.mkString
    println(samp1)
    println(samp2)


    //TODO use this - the answer is in here somewhere!
    //http://stackoverflow.com/questions/1429172/how-do-i-list-the-files-inside-a-jar-file

    //this works!
    val src = getClass.getProtectionDomain.getCodeSource
    val jar = src.getLocation
    val zip = new ZipInputStream(jar.openStream())
    var ne = zip.getNextEntry
    while (ne != null) {
      val name = ne.getName
      if (name.contains("dir") && name.contains("sample")) {
        println("The found file is " + name)
        val item = scala.io.Source.fromInputStream(getClass.getResourceAsStream("/" + name)).getLines.mkString
        println("The content is " + item)
      }
      ne = zip.getNextEntry
    }




  }
}
