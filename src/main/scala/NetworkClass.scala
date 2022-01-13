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

    newGeneration(bestNetworks.toMap.keySet)
  }

  private def newGeneration(bestNetworks: Set[NeuralNetwork]): Seq[NeuralNetwork] = {
    val pairings = bestNetworks.subsets()
      .map(_.toSeq)
      .filter(_.length == 2)
      .map(pair => (pair.head, pair(1)))
      .filter{ case (a, b) => a.identifier != b.identifier}

    val children = pairings
      .flatMap{ case (a, b) => NeuralNetwork.createChildren(a, b, NetworkClass.mutationCount) }
      .toSeq

    val bestNetworksMutations = bestNetworks
      .flatMap(nn => NeuralNetwork.mutate(nn, NetworkClass.mutationCount).prepended(nn))
      .toSeq

    children.concat(bestNetworksMutations)
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
  val graduationCount: Int = 10
  val mutationCount: Int = 39
  val classSize: Int = ((mutationCount + 1) * graduationCount * (graduationCount + 1)) / 2

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
