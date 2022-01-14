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

  def points: Int = snake.length - 3

  def distanceToFood: Double = Math.sqrt(Math.pow(food.x - snake.head.x, 2.0) + Math.pow(food.y - snake.head.y, 2.0))

  def moveForward(): Board = move(snake.head - snake.tail.head)
  def moveLeft(): Board = move((snake.head - snake.tail.head).rotateLeft)
  def moveRight(): Board = move((snake.head - snake.tail.head).rotateRight)

  private def move(direction: Point): Board = {
    val newHead = snake.head + direction
    if (newHead.x < 0 || newHead.y < 0 || newHead.x >= width || newHead.y >= height) {
      throw new GameOverException("GAME OVER - snek hit wall, is ded now")
    }

    val eatFood = newHead == food

    val newTail = if (eatFood) snake else snake dropRight 1

    if (newHead in newTail) {
      throw new GameOverException("GAME OVER - snek bit itself, is ded now")
    }

    val newSnake = newHead :: newTail
    val newFood = if (eatFood) Board.randomFood(width, height, newSnake) else food

    new Board(width, height, newSnake, newFood)
  }

  def getNeuralNetworkInput: Input = {
    val forwardDirection = snake.head - snake.tail.head
    val isHorizontal = forwardDirection.x != 0
    val forwardDistance = 1.0 * (if (isHorizontal) width else height)
    val sideDistance = 1.0 * (if (isHorizontal) height else width)
    val angledDistance = Math.sqrt(forwardDistance * forwardDistance + sideDistance * sideDistance)

    Input(
      getDistanceForDirection(forwardDirection, forwardDistance, isAngled = false),
      getDistanceForDirection(forwardDirection.backwards, forwardDistance, isAngled = false),
      getDistanceForDirection(forwardDirection.rotateLeft, sideDistance, isAngled = false),
      getDistanceForDirection(forwardDirection.rotateRight, sideDistance, isAngled = false),
      getDistanceForDirection(forwardDirection.halfLeft, angledDistance, isAngled = true),
      getDistanceForDirection(forwardDirection.halfRight, angledDistance, isAngled = true),
      getDistanceForDirection(forwardDirection.rotateLeft.halfLeft, angledDistance, isAngled = true),
      getDistanceForDirection(forwardDirection.rotateRight.halfRight, angledDistance, isAngled = true),
    )
  }

  private def getDistanceForDirection(direction: Point, mapSize: Double, isAngled: Boolean): Distance = {
    val multiplier = if (isAngled) Math.sqrt(2.0) else 1.0

    var currentPoint = snake.head
    var foodDistance: Option[Double] = None
    var bodyDistance: Option[Double] = None
    var wallDistance: Option[Double] = None
    var countedDistance = 0

    while (wallDistance.isEmpty) {
      countedDistance += 1
      currentPoint += direction

      if (currentPoint == food) {
        foodDistance = Some(countedDistance * multiplier)
      }
      if ((currentPoint in snake) && bodyDistance.isEmpty) {
        bodyDistance = Some(countedDistance * multiplier)
      }
      if (currentPoint.x < 0 || currentPoint.x >= width || currentPoint.y < 0 || currentPoint.y >= height) {
        wallDistance = Some(countedDistance * multiplier)
      }
    }

    Distance(
      foodDistance.getOrElse(mapSize) / mapSize,
      wallDistance.getOrElse(mapSize) / mapSize,
      bodyDistance.getOrElse(mapSize) / mapSize,
    )
  }

  def copy: Board = new Board(width, height, snake, food)
}
object Board {
  def apply(width: Int, height: Int): Board = {
    val snake = buildSnake(width, height)
    new Board(width, height, snake, randomFood(width, height, snake))
  }

  def randomFood(width: Int, height: Int, snake: Snake): Point =
    Point.matrix(width, height)
    .without(snake)
    .random.getOrElse(throw new Exception("GAME OVER - no more food to eat!"))

  private def buildSnake(width: Int, height: Int): Snake = {
    val head = Point(width / 2, height / 2)
    val diff = Point(-1, 0)

    List(
      head,
      head + diff,
      head + 2 * diff,
    )
  }
}
