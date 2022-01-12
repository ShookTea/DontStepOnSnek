package eu.shooktea.dsos

import Types._

import scala.util.Random

class Board(width: Int, height: Int, snake: Snake) {

  override def toString: String =
    "#".repeat(width + 2) + "\n" + boardToString.mkString("\n") + "\n" + "#".repeat(width + 2)

  private def boardToString: Seq[String] =
    for (y <- 0 until height) yield "#" + rowToString(y).mkString + "#"

  private def rowToString(y: Int): Seq[String] = {
    for (x <- 0 until width) yield {
      if (snake.head == (x, y)) "@"
      else if (snake.last == (x, y)) "+"
      else if (snake contains (x, y)) "*"
      else " "
    }
  }

}
object Board {
  val random = new Random()

  def apply(width: Int, height: Int): Board = new Board(width, height, randomSnake(width, height))

  private def randomSnake(width: Int, height: Int): Snake = {
    val snakeDirection = random.nextInt(4)
    var minX = 0
    var minY = 0
    var maxX = width
    var maxY = height
    var xDiffForBody = 0
    var yDiffForBody = 0

    if (snakeDirection == 0) { // left to right
      minX += 3
      xDiffForBody = 1
    }
    if (snakeDirection == 1) { // right to left
      maxX -= 3
      xDiffForBody = -1
    }
    if (snakeDirection == 2) { // up to bottom
      minY += 3
      yDiffForBody = -1
    }
    if (snakeDirection == 3) { // bottom to up
      maxY -= 3
      yDiffForBody = 1
    }

    val headX = random.nextInt(maxX - minX) + minX
    val headY = random.nextInt(maxY - minY) + minY

    List(
      (headX, headY),
      (headX + xDiffForBody, headY + yDiffForBody),
      (headX + 2 * xDiffForBody, headY + 2 * yDiffForBody),
    )
  }
}
