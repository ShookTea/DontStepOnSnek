package eu.shooktea.dsos

object Tester {
  val testIterations = 10
  val mapWidth = 50
  val mapHeight = 15
  val startingMovePoints = 300
  val movePointsPerFood = 100
  val foodWeight = 5.0
  val remainingMovesWeight = 2.0

  def testNetwork(neuralNetwork: NeuralNetwork) = {
    println(testIteration(neuralNetwork))
  }

  private def testIteration(nn: NeuralNetwork): Double = {
    var board = Board(mapWidth, mapHeight)
    var movePoints = startingMovePoints
    var currentPoints = 0

    try {
      while (movePoints > 0) {
        movePoints -= 1

        val output = nn.apply(board.getNeuralNetworkInput)
        board =
          if (output.shouldGoLeft()) board.moveLeft()
          else if (output.shouldGoRight()) board.moveRight()
          else board.moveForward()

        if (board.points > currentPoints) {
          currentPoints = board.points
          movePoints += movePointsPerFood
        }
      }
    } catch {
      case e: GameOverException => println(e.getMessage)
    }

    (movePoints * remainingMovesWeight + currentPoints * foodWeight) / (remainingMovesWeight + foodWeight)
  }
}
