package eu.shooktea.dsos

object Main {
  def main(args: Array[String]): Unit = {
    val networkClass = NetworkClass.random()
    networkClass.run()
  }
}
