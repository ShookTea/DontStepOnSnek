package eu.shooktea.dsos

import scala.annotation.tailrec

object Main {
  def main(args: Array[String]): Unit = {
    val resultingClass = runIterations(50)

    val bestNetwork = resultingClass.getBest.head._1
    var board = Board(50, 15)

    var movePoints = 500
    try {
      while (movePoints > 0) {
        println(board)
        Thread.sleep(100)
        movePoints -= 0
        val output = bestNetwork(board.getNeuralNetworkInput)
        val pointsBeforeMove = board.points
        board =
          if (output.shouldGoLeft()) board.moveLeft()
          else if (output.shouldGoRight()) board.moveRight()
          else board.moveForward()
        val currentPoints = board.points
        if (currentPoints > pointsBeforeMove) {
          movePoints += 150
        }
      }
    } catch {
      case e: GameOverException => println(e.getMessage)
    }
    println("Points: " + board.points)
  }

  @tailrec
  def runIterations(iterations: Int, currentClass: NetworkClass = NetworkClass.random()): NetworkClass =
    if (iterations == 0) currentClass
    else runIterations(iterations - 1, NetworkClass.evolve(currentClass))
}
