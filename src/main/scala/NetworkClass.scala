package eu.shooktea.dsos

class NetworkClass(neuralNetworks: Seq[NeuralNetwork], generation: Int) {
  def run(): Unit = {
    println(s"Running tests on ${neuralNetworks.length} neural networks of generation $generation...")
    runTests()
  }

  private def runTests(): Seq[(NeuralNetwork, Double)] = {
    NetworkClass.printClassProgress(0, replace = false)
    neuralNetworks.zipWithIndex.map{
      case (nn, index) =>
        val result = Tester.testNetwork(nn)
        NetworkClass.printClassProgress(index)
        (nn, result)
    }
  }
}

object NetworkClass {
  val classSize = 2000

  def apply(neuralNetworks: Seq[NeuralNetwork], generation: Int = 1): NetworkClass =
    new NetworkClass(neuralNetworks, generation)

  def random(): NetworkClass =
    NetworkClass(for (_ <- 0 until classSize) yield NeuralNetwork.random())

  def printClassProgress(count: Int, replace: Boolean = true): Unit = {
    val percentage = Math.round(count * 50.0 / classSize).toInt
    print(
      (if (replace) "\r" else "") + "[" +
        "*".repeat(percentage)
      + " ".repeat(50 - percentage)
      + "]"
    )
  }
}
