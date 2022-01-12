package eu.shooktea.dsos

import Utils._
import TypeAddons._


class Board(width: Int, height: Int, snake: Snake, food: Point) {
  override def toString: String =
    "#".repeat(width + 2) + "\n" + boardToString.mkString("\n") + "\n" + "#".repeat(width + 2)

  private def boardToString: Seq[String] =
    for (y <- 0 until height) yield "#" + rowToString(y).mkString + "#"

  private def rowToString(y: Int): Seq[String] = {
    for (x <- 0 until width) yield {
      if (snake.head == (x, y)) "@"
      else if (snake.last == (x, y)) "+"
      else if (snake contains (x, y)) "*"
      else if (food == (x, y)) "&"
      else " "
    }
  }

  def moveForward(): Board = move(snake.head - snake.tail.head)
  def moveLeft(): Board = move((snake.head - snake.tail.head).rotateLeft)
  def moveRight(): Board = move((snake.head - snake.tail.head).rotateRight)

  private def move(direction: Point): Board = {
    val newHead = snake.head + direction
    if (newHead.x < 0 || newHead.y < 0 || newHead.x >= width || newHead.y >= height) {
      throw new Exception("GAME OVER - snek hit wall, is ded now")
    }

    val newTail = snake dropRight 1
    if (newTail contains newHead) {
      throw new Exception("GAME OVER - snek bit itself, is ded now")
    }

    val newSnake = newHead :: newTail

    new Board(width, height, newSnake, food)
  }
}
object Board {
  def apply(width: Int, height: Int): Board = {
    val snake = randomSnake(width, height)
    new Board(width, height, snake, randomFood(width, height, snake))
  }

  def randomFood(width: Int, height: Int, snake: Snake): Point =
    Point.matrix(width, height)
    .without(snake)
    .random.getOrElse(throw new Exception("GAME OVER - no more food to eat!"))

  private def randomSnake(width: Int, height: Int): Snake = {
    val snakeDirection = Utils.random.nextInt(4)
    var minX = 0
    var minY = 0
    var maxX = width
    var maxY = height
    var xDiffForBody = 0
    var yDiffForBody = 0

    if (snakeDirection == 0) { // left to right
      minX += 2
      xDiffForBody = -1
    }
    if (snakeDirection == 1) { // right to left
      maxX -= 2
      xDiffForBody = 1
    }
    if (snakeDirection == 2) { // up to bottom
      minY += 2
      yDiffForBody = -1
    }
    if (snakeDirection == 3) { // bottom to up
      maxY -= 2
      yDiffForBody = 1
    }

    val headX = random.nextInt(maxX - minX) + minX
    val headY = random.nextInt(maxY - minY) + minY

    val head = Point(headX, headY)
    val diff = Point(xDiffForBody, yDiffForBody)

    List(
      head,
      head + diff,
      head + 2 * diff,
    )
  }
}
