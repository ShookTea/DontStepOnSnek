package eu.shooktea.dsos

import TestResult._

case class TestResult(foodEaten: Int, remainingMovePoints: Int) extends Ordered[TestResult] {
  def +(other: TestResult): TestResult =
    TestResult(foodEaten + other.foodEaten, remainingMovePoints + other.remainingMovePoints)

  def grade: Double =
    (foodEaten * foodWeight + remainingMovePoints * remainingMovesWeight) / (foodWeight + remainingMovesWeight)

  override def compare(that: TestResult): Int = grade.compareTo(that.grade)
}

object TestResult {
  val foodWeight = 5.0
  val remainingMovesWeight = 2.0
}