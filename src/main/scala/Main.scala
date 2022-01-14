package eu.shooktea.dsos

object Main {
  def main(args: Array[String]): Unit = {
    if (args.head == "evolve") runEvolutionsFromStart(args(1))
    else if (args.head == "continue") continueEvolutionsFromFile(args(1))
    else if (args.head == "run") runFromFile(args(1))
  }

  private def runEvolutionsFromStart(path: String): Unit =
    runEvolution(
      NetworkClass.random(),
      path
    )

  private def continueEvolutionsFromFile(path: String): Unit =
    runEvolution(
      Persistence.load(path),
      path
    )

  private def runEvolution(startClass: NetworkClass, path: String): NetworkClass = {
    var currentClass = startClass
    while (true) {
      val newClass = NetworkClass.evolve(currentClass)
      Persistence.save(currentClass, path)
      currentClass = newClass
    }
    currentClass
  }

  private def runFromFile(path: String): Unit = {
    val classFromFile = Persistence.load(path)
    val bestNeuralNetwork = classFromFile.getBest.head._1

    var board = Board()
    var movePoints = Parameter.startingMovePoints
    var currentPoints = 0
    while (movePoints > 0) {
      println(board)
      Thread.sleep(75)
      movePoints -= 1
      val nextMove = bestNeuralNetwork(board.getNeuralNetworkInput)
      board =
        if (nextMove.shouldGoLeft()) board.moveLeft()
        else if (nextMove.shouldGoRight()) board.moveRight()
        else board.moveForward()

      if (board.points > currentPoints) {
        currentPoints = board.points
        movePoints += Parameter.movePointsPerFood
      }
    }
  }
}
