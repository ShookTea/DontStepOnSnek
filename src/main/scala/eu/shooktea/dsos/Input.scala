package eu.shooktea.dsos

case class Input(
                head: DistanceGroup
                ) {
  def apply(index: Int): Double = head(index)
}
