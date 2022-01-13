package eu.shooktea.dsos

import TestResult._

case class TestResult(foodEaten: Int, remainingMovePoints: Int, movesGoingAway: Int) extends Ordered[TestResult] {
  val remainingMovesWeight = 1.0

  def +(other: TestResult): TestResult =
    TestResult(foodEaten + other.foodEaten, remainingMovePoints + other.remainingMovePoints, movesGoingAway + other.movesGoingAway)

  def grade: Double =
    foodEaten * foodWeight + remainingMovePoints * remainingMovesWeight + movesGoingAway * movesGoingAwayWeight

  override def compare(that: TestResult): Int = grade.compareTo(that.grade)
}

object TestResult {
  val foodWeight: Double = 45.0
  val remainingMovesWeight: Double = 0.1
  val movesGoingAwayWeight: Double = -0.25
}