package eu.shooktea.dsos

class NetworkClass(val neuralNetworks: Seq[NeuralNetwork], val generation: Int) {
  private var best: Option[Seq[(NeuralNetwork, TestResult)]] = None

  def getBest: Seq[(NeuralNetwork, TestResult)] = {
    if (best.isEmpty) {
      val bestCalculated = runTests().sortBy(_._2).reverse.slice(0, Parameter.graduationCount)
      this.best = Some(bestCalculated)
    }
    best.get
  }

  private def runTests(): Seq[(NeuralNetwork, TestResult)] = {
    NetworkClass.printClassProgress(0, replace = false)
    val boards = for (_ <- 1 to Parameter.testIterations) yield Board(Parameter.mapWidth, Parameter.mapHeight)
    val results = neuralNetworks.zipWithIndex.map{
      case (nn, index) =>
        val result = Tester.testNetwork(nn, boards)
        NetworkClass.printClassProgress(index)
        (nn, result)
    }
    println()
    results
  }
}

object NetworkClass {
  def evolve(networkClass: NetworkClass): NetworkClass = {
    println(s"Running tests on ${networkClass.neuralNetworks.length} neural networks of generation ${networkClass.generation}...")
    val bestNetworks = networkClass.getBest

    println("Best results:")
    bestNetworks.foreach{
      case (nn, result) => println(f"${result.grade}%.4f (F=${result.foodEaten}, M=${result.remainingMovePoints}, A=${result.movesGoingAway}) - ${nn.identifier} (gen. ${nn.generation})")
    }

    val mutations = createMutations(bestNetworks.toMap.keySet)

    new NetworkClass(
      mutations,
      networkClass.generation + 1,
    )
  }

  private def createMutations(bestNetworks: Set[NeuralNetwork]): Seq[NeuralNetwork] = {
    val pairings = bestNetworks.subsets()
      .map(_.toSeq)
      .filter(_.length == 2)
      .map(pair => (pair.head, pair(1)))
      .filter{ case (a, b) => a.identifier != b.identifier}

    val children = pairings
      .flatMap{ case (a, b) => NeuralNetwork.createChildren(a, b, Parameter.mutationCount) }
      .toSeq

    val bestNetworksMutations = bestNetworks
      .flatMap(nn => NeuralNetwork.mutate(nn, Parameter.mutationCount).prepended(nn))
      .toSeq

    children.concat(bestNetworksMutations)
  }

  def apply(neuralNetworks: Seq[NeuralNetwork], generation: Int = 1): NetworkClass =
    new NetworkClass(neuralNetworks, generation)

  def random(): NetworkClass =
    NetworkClass(for (_ <- 0 until Parameter.classSize) yield NeuralNetwork.random())

  def printClassProgress(count: Int, replace: Boolean = true): Unit = {
    val percentage = Math.round(count * 50.0 / Parameter.classSize).toInt
    print(
      (if (replace) "\r" else "") + "[" +
        "*".repeat(percentage)
      + " ".repeat(50 - percentage)
      + "]"
    )
  }
}
