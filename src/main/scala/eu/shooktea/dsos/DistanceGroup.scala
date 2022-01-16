package eu.shooktea.dsos

case class DistanceGroup(
                          forward: Distance,
                          backward: Distance,
                          left: Distance,
                          right: Distance,
                          forwardLeft: Distance,
                          forwardRight: Distance,
                          backwardLeft: Distance,
                          backwardRight: Distance
                        ) {
  def apply(index: Int): Double = (index - (index % Distance.fieldsCount)) / Distance.fieldsCount match {
    case 0 => forward(index % Distance.fieldsCount)
    case 1 => backward(index % Distance.fieldsCount)
    case 2 => left(index % Distance.fieldsCount)
    case 3 => right(index % Distance.fieldsCount)
    case 4 => forwardLeft(index % Distance.fieldsCount)
    case 5 => forwardRight(index % Distance.fieldsCount)
    case 6 => backwardLeft(index % Distance.fieldsCount)
    case 7 => backwardRight(index % Distance.fieldsCount)
    case _ => throw new IndexOutOfBoundsException
  }
}

object DistanceGroup {
  val fieldsCount: Int = Distance.fieldsCount * 8
}
