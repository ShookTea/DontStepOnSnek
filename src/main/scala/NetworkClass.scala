package eu.shooktea.dsos

class NetworkClass(neuralNetworks: Seq[NeuralNetwork], generation: Int) {
  def run(): Unit = {
    println(s"Running tests on ${neuralNetworks.length} neural networks of generation $generation...")

    val results = runTests()
    val bestNetworks = results.sortBy(_._2).reverse.slice(0, NetworkClass.graduationCount)
    println("Best results:")
    bestNetworks.foreach{
      case (nn, result) => println(f"${result.grade}%.4f (F=${result.foodEaten}, M=${result.remainingMovePoints}) - ${nn.identifier} (gen. ${nn.generation})")
    }
  }

  private def runTests(): Seq[(NeuralNetwork, TestResult)] = {
    NetworkClass.printClassProgress(0, replace = false)
    val results = neuralNetworks.zipWithIndex.map{
      case (nn, index) =>
        val result = Tester.testNetwork(nn)
        NetworkClass.printClassProgress(index)
        (nn, result)
    }
    println()
    results
  }
}

object NetworkClass {
  val classSize = 100
  val graduationCount = 10

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
