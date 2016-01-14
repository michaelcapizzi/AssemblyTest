package Test

import java.io.File
import java.nio.file.FileSystems
import java.util.Collections
import java.util.jar.{JarEntry, JarFile}
import java.util.zip.ZipInputStream

/**
 * Created by mcapizzi on 10/15/15.
 */
object Main {
  def main(args: Array[String]) = {
    println("This works.")

    //don't work
    /*val resources = getClass.getResource("resources/").getPath

    val dir1 = getClass.getResource("resources/dir/").getPath
    val dir2 = getClass.getResource("resources/dir2/").getPath*/

    val dir1 = getClass.getResource("/dir/")

    val dir2 = getClass.getResource("/dir2/")

//    println("Here is the path to resources: " + resources)
    println("Here is the path to dir: " + dir1)  // jar:file:/media/mcapizzi/data/Github/AssemblyTest/target/scala-2.11/testResources.jar!/dir/
    println("Here is the path to dir2: " + dir2)  //jar:file:/media/mcapizzi/data/Github/AssemblyTest/target/scala-2.11/testResources.jar!/dir2/
//    println("Here is dir with URI: " + dir1.toURI)   // jar:file:/media/mcapizzi/data/Github/AssemblyTest/target/scala-2.11/testResources.jar!/dir/

    //prints a given resource
    val samp1 = scala.io.Source.fromInputStream(getClass.getResourceAsStream("/dir/sample1.txt")).getLines.mkString
    println(samp1)

    //something to do with JarFile
    //just lists nonsense classes to me
    /*val dir1Path = dir1.getPath.substring(5, dir1.getPath.indexOf("!"))

    val dir1Jar = new JarFile(dir1Path)

    val dir1Entries = dir1Jar.entries()
    while (dir1Entries.hasMoreElements) {
      val entry = new JarEntry(dir1Entries.nextElement)
      val name = entry.getName
      println("This entry is called: " + name)
    }*/

    //doesn't work
    /*val dir1ZipInputStream = new ZipInputStream(dir1.openStream)

    while (true) {
      val entry = dir1ZipInputStream.getNextEntry
      val name = entry.getName
      println("This is inside dir: " + name)
    }
*/

    //TODO use this - the answer is in here somewhere!
    //http://stackoverflow.com/questions/1429172/how-do-i-list-the-files-inside-a-jar-file


  }

}
