package eu.shooktea.dsos

import java.io.{DataOutputStream, FileOutputStream}
import java.nio.file.Paths
import java.util.UUID

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
    dos.writeInt(best.length)

    best.foreach(saveResultEntry(dos))
  }

  private def saveResultEntry(dos: DataOutputStream)(entry: (NeuralNetwork, TestResult)) : Unit = entry match {
    case (nn: NeuralNetwork, _) =>
      val uuid = UUID.fromString(nn.identifier)
      dos.writeLong(uuid.getMostSignificantBits)
      dos.writeLong(uuid.getLeastSignificantBits)
      dos.writeInt(nn.generation)

      dos.writeInt(nn.weights.length)
      nn.weights.foreach(dos.writeDouble)
  }
}
