package eu.shooktea.dsos

case class Point(x: Int, y: Int) {
  def diff(other: Point): Point = Point(x - other.x, y - other.y)
  def -(other: Point): Point = diff(other)

  def add(other: Point): Point = Point(x + other.x, y + other.y)
  def +(other: Point): Point = add(other)

  def multiplyByScalar(scalar: Int): Point = Point(x * scalar, y * scalar)
  def *(scalar: Int): Point = multiplyByScalar(scalar)

  def rotateLeft: Point = Point(
    if (x == 0) y else 0,
    if (y == 0) -x else 0,
  )

  def rotateRight: Point = Point(
    if (x == 0) -y else 0,
    if (y == 0) x else 0,
  )

  override def equals(obj: Any): Boolean = obj match {
    case Point(otherX, otherY) => x == otherX && y == otherY
    case (otherX: Int, otherY: Int) => x == otherX && y == otherY
    case _ => false
  }
}

object Point {
  def matrix(width: Int, height: Int): Seq[Point] =
    for (x <- 0 until width; y <- 0 until height) yield Point(x, y)

  implicit class MatrixOps(matrix: Seq[Point]) {
    def without(points: Seq[Point]): Seq[Point] = matrix.filterNot(points.contains)
    def random: Option[Point] = Utils.random.shuffle(matrix).headOption
  }
}