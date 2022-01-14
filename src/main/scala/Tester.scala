package eu.shooktea.dsos

object Tester {
  def testNetwork(neuralNetwork: NeuralNetwork, boards: Seq[Board] = getBoards): TestResult =
    boards.map(board => testIteration(neuralNetwork, board))
      .reduce(_ + _)

  private def getBoards: Seq[Board] = for (_ <- 1 to Parameter.testIterations) yield Board(Parameter.mapWidth, Parameter.mapHeight)

  private def testIteration(nn: NeuralNetwork, startBoard: Board): TestResult = {
    var board = startBoard.copy
    var movePoints = Parameter.startingMovePoints
    var foodEaten = 0
    var movesGoingAway = 0
    var currentDistanceToFood = board.distanceToFood

    try {
      while (movePoints > 0) {
        movePoints -= 1

        val output = nn.apply(board.getNeuralNetworkInput)
        board =
          if (output.shouldGoLeft()) board.moveLeft()
          else if (output.shouldGoRight()) board.moveRight()
          else board.moveForward()

        var eaten = false
        if (board.points > foodEaten) {
          foodEaten = board.points
          movePoints += Parameter.movePointsPerFood
          eaten = true
        }
        val newCurrentDistanceToFood = board.distanceToFood
        if (newCurrentDistanceToFood > currentDistanceToFood && !eaten) {
          movesGoingAway += 1
        }
        currentDistanceToFood = newCurrentDistanceToFood
      }
    } catch {
      case e: GameOverException =>
    }

    TestResult(foodEaten, movePoints, movesGoingAway)
  }
}
