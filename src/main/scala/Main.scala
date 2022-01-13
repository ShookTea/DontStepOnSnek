package eu.shooktea.dsos

object Main {
  def main(args: Array[String]): Unit = {
    var board = Board(50, 15)
    val neuralNetwork = NeuralNetwork.random()

    try {
      for (_ <- 1 to 100) {
        println(board)
        val output = neuralNetwork(board.getNeuralNetworkInput)
        if (output.shouldGoForward()) println("FORWARD!")
        else if (output.shouldGoLeft()) println("LEFT!")
        else println("RIGHT!")
        board =
          if (output.shouldGoLeft()) board.moveLeft()
          else if (output.shouldGoRight()) board.moveRight()
          else board.moveForward()
      }
    } catch {
      case e: Throwable => println(e.getMessage)
    }
  }
}
