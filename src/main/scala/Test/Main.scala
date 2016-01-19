package Test

import java.io.{PrintWriter, File}
import java.nio.file.{Paths, Path, FileSystems}
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

    /*//finding the files when in a .jar
    //http://stackoverflow.com/questions/1429172/how-do-i-list-the-files-inside-a-jar-file

    //this works!
    val src = getClass.getProtectionDomain.getCodeSource
    val jar = src.getLocation
    val zip = new ZipInputStream(jar.openStream())
    var ne = zip.getNextEntry
    while (ne != null) {
      val name = ne.getName
      if (name.contains("sample") && name.endsWith(".txt")) {
        println("The found file is " + name)
        val item = scala.io.Source.fromInputStream(getClass.getResourceAsStream("/" + name)).getLines.mkString
        println("The content is " + item)
      }
      ne = zip.getNextEntry
    }

    val uri = getClass.getResource("/resources").toURI
    println(uri)*/

    //make temp dir for data to collect
    println("making temp directory")
    val tempDir = new File("/tmp/Pitch/")
    tempDir.mkdir

    val transSource = new File("/tmp/Pitch/transcription.txt")
//    val transTarget = new File("/home/mcapizzi/Desktop/Pitch/transcription.txt")

    val pw = new PrintWriter(transSource)
    pw.println("This is a test.")
    pw.close

    //TODO gz the tmp folder and move to Desktop
    //for now, move directory with transcription and feedback to Desktop

    //convert to JDK 7 Path
    val transPathSource = Paths.get("/tmp/Pitch/transcription.txt")
//    val feedbackPathSource = Paths.get("/tmp/Pitch/feedback.txt")

    val transPathTarget = Paths.get("/home/mcapizzi/Desktop/transcription.txt")
//    val feedbackPathTarget = Paths.get("/home/mcapizzi/Desktop/Pitch/feedback.txt")

    //move to Desktop
    java.nio.file.Files.move(transPathSource, transPathTarget)
//    java.nio.file.Files.move(feedbackPathSource, feedbackPathTarget)

    //deleting temp
    tempDir.delete


  }

}
