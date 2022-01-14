package eu.shooktea.dsos

import java.io.{DataInputStream, DataOutputStream, FileInputStream, FileOutputStream}
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

  def load(path: String): NetworkClass = {
    val file = Paths.get(path).toFile
    print("Loading...")

    val dis = new DataInputStream(
      new FileInputStream(file)
    )

    val nc = load(dis)
    dis.close()
    println(" done.")
    nc
  }

  private def save(nc: NetworkClass, dos: DataOutputStream): Unit = {
    dos.writeInt(nc.generation)
    val best = nc.getBest
    dos.writeInt(best.length)

    best.foreach(saveResultEntry(dos))
  }

  private def load(dis: DataInputStream): NetworkClass = {
    val generation = dis.readInt()
    val bestCount = dis.readInt()
    val networks = for (_ <- 1 to bestCount) yield loadNeuralNetwork(dis)

    new NetworkClass(networks, generation)
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

  private def loadNeuralNetwork(dis: DataInputStream): NeuralNetwork = {
    val uuid = new UUID(
      dis.readLong(),
      dis.readLong(),
    ).toString
    val generation = dis.readInt()

    val weightsCount = dis.readInt()
    val weights = for (_ <- 1 to weightsCount) yield dis.readDouble()

    new NeuralNetwork(weights, generation, uuid)
  }
}
