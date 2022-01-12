package eu.shooktea.dsos

case class Distance(
                   food: Double,
                   wall: Double,
                   bodyPart: Double
                   )

object Distance {
  def apply(mapSize: Double, wallDistance: Int, foodDistance: Option[Int], bodyPartDistance: Option[Int]): Distance =
    Distance(
      foodDistance.getOrElse(mapSize) / mapSize,
      wallDistance / mapSize,
      bodyPartDistance.getOrElse(mapSize) / mapSize,
    )
}