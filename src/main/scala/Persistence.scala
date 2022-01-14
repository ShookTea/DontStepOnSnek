package eu.shooktea.dsos

import java.nio.file.Paths

object Persistence {
  def save(networkClass: NetworkClass, path: String): Unit = {
    val file = Paths.get(path).toFile
    if (file.exists()) {
      println("EXISTS!")
      file.delete()
    } else {
      println("CREATING FILE!")
      file.createNewFile()
    }
  }
}
