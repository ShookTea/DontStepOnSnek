package eu.shooktea.dsos

case class Input(
                head: DistanceGroup,
                secondPart: DistanceGroup
                ) {
  def apply(index: Int): Double =
    (index - (index % DistanceGroup.fieldsCount)) / DistanceGroup.fieldsCount match {
      case 0 => head(index % DistanceGroup.fieldsCount)
      case 1 => secondPart(index % DistanceGroup.fieldsCount)
      case _ => throw new IndexOutOfBoundsException
    }
}
