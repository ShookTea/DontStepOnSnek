package eu.shooktea.dsos

object Utils {
  type Snake = List[Point]
}

object TypeAddons {
  implicit class IntAddons(int: Int) {
    def *(point: Point): Point = point * int
  }
}
