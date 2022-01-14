package eu.shooktea.dsos

import java.io.{DataOutputStream, FileOutputStream}
import java.nio.file.Paths

object Persistence {
  def save(networkClass: NetworkClass, path: String): Unit = {
    val file = Paths.get(path).toFile
    print("Saving...")
    if (file.exists())
      file.delete()
    else
      file.createNewFile()

    val dos = new DataOutputStream(
      new FileOutputStream(file)
    )
    save(networkClass, dos)
    dos.close()
    println(" done.")
  }

  private def save(nc: NetworkClass, dos: DataOutputStream): Unit = {
    dos.writeInt(nc.generation)
    val best = nc.getBest
  }
}
