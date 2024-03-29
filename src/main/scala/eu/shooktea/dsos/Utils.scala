package eu.shooktea.dsos

import scala.util.Random

object Utils {
  type Snake = List[Point]

  val random = new Random()

  def max(values: Double*): Double = values.max

  def randomWeight(): Double = 2.0 * (random.nextDouble() - 0.5)
}

object TypeAddons {
  implicit class IntAddons(int: Int) {
    def *(point: Point): Point = point * int
    def /(point: Point): Point = Point(int / point.x, int / point.y)
  }

  implicit class DoubleAddons(double: Double) {
//    def sigmoid: Double = 1.0 / (1.0 + Math.pow(Math.E, -0.7 * (double - 7.5)))
    def sigmoid: Double = 1.0 / (1.0 + Math.pow(Math.E, -1 * double))
  }
}
