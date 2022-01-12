package eu.shooktea.dsos

import scala.util.Random

object Utils {
  type Snake = List[Point]

  val random = new Random()
}

object TypeAddons {
  implicit class IntAddons(int: Int) {
    def *(point: Point): Point = point * int
  }
}
