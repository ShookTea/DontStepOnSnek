package eu.shooktea.dsos

import TestResult._

case class TestResult(foodEaten: Int, remainingMovePoints: Int) extends Ordered[TestResult] {
//  val remainingMovesWeight = 1.5 * (1.0 - 1.0 / (1 + Math.exp(-0.3 * (foodEaten - 15))) - 0.2)
//  val remainingMovesWeight = 1.5 * (1.0 / (1 + Math.exp(-0.3 * (foodEaten - 15))) - 0.2)
  val remainingMovesWeight = 1.0

  def +(other: TestResult): TestResult =
    TestResult(foodEaten + other.foodEaten, remainingMovePoints + other.remainingMovePoints)

  def grade: Double = {
//    println(remainingMovesWeight)
    (foodEaten * foodWeight + remainingMovePoints * remainingMovesWeight) / (foodWeight + remainingMovesWeight)
  }

  override def compare(that: TestResult): Int = grade.compareTo(that.grade)
}

object TestResult {
  val foodWeight = 700.0
}