package eu.shooktea.dsos

case class Distance(
                   food: Double,
                   wall: Double,
                   bodyPart: Double
                   ) {
  def apply(index: Int): Double = index match {
    case 0 => food
    case 1 => wall
    case 2 => bodyPart
    case _ => throw new IndexOutOfBoundsException
  }
}