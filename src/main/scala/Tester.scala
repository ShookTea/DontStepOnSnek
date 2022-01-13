package eu.shooktea.dsos

object Tester {
  val testIterations = 10
  val mapWidth = 50
  val mapHeight = 15
  val startingMovePoints = 300
  val movePointsPerFood = 100

  def testNetwork(neuralNetwork: NeuralNetwork): TestResult =
    getIterationsResults(neuralNetwork).reduce(_ + _)

  private def getIterationsResults(nn: NeuralNetwork): Seq[TestResult] =
    for (_ <- 1 to testIterations) yield testIteration(nn)

  private def testIteration(nn: NeuralNetwork): TestResult = {
    var board = Board(mapWidth, mapHeight)
    var movePoints = startingMovePoints
    var foodEaten = 0

    try {
      while (movePoints > 0) {
        movePoints -= 1

        val output = nn.apply(board.getNeuralNetworkInput)
        board =
          if (output.shouldGoLeft()) board.moveLeft()
          else if (output.shouldGoRight()) board.moveRight()
          else board.moveForward()

        if (board.points > foodEaten) {
          foodEaten = board.points
          movePoints += movePointsPerFood
        }
      }
    } catch {
      case e: GameOverException =>
    }

    TestResult(foodEaten, movePoints)
  }
}
