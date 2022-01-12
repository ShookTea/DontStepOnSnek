package eu.shooktea.dsos

case class Input(
                forward: Distance,
                backward: Distance,
                left: Distance,
                right: Distance,
                forwardLeft: Distance,
                forwardRight: Distance,
                backwardLeft: Distance,
                backwardRight: Distance
                ) {
  def apply(index: Int): Double = (index - (index % 3)) / 3 match {
    case 0 => forward(index % 3)
    case 1 => backward(index % 3)
    case 2 => left(index % 3)
    case 3 => right(index % 3)
    case 4 => forwardLeft(index % 3)
    case 5 => forwardRight(index % 3)
    case 6 => backwardLeft(index % 3)
    case 7 => backwardRight(index % 3)
    case _ => throw new IndexOutOfBoundsException
  }
}
