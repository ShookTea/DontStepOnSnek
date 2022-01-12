package eu.shooktea.dsos

import scala.util.Random

class Board(elements: Seq[Seq[Element]]) {
  def height: Int = elements.length

  def width: Int = elements.head.length

  override def toString: String =
    "#".repeat(width + 2) + "\n" +
    elements.map(row => "#" + row.map(r => r.displayCode).mkString + "#").mkString("\n") + "\n" +
    "#".repeat(width + 2)

}
object Board {
  val random = new Random()

  def apply(elements: Seq[Seq[Element]]): Board = new Board(elements)

  def apply(width: Int, height: Int): Board = {
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

    Board(
      for (y <- 0 until height) yield
        for (x <- 0 until width) yield
          if (x == headX && y == headY) Elements.SnakeHead
          else if (x == headX + xDiffForBody && y == headY + yDiffForBody) Elements.SnakeBody
          else if (x == headX + 2 * xDiffForBody && y == headY + 2 * yDiffForBody) Elements.SnakeTail
          else Elements.Empty
    )
  }
}
