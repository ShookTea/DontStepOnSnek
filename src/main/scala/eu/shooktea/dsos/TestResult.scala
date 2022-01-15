package eu.shooktea.dsos

case class TestResult(foodEaten: Int, remainingMovePoints: Int, movesGoingAway: Int) extends Ordered[TestResult] {
  def +(other: TestResult): TestResult =
    TestResult(foodEaten + other.foodEaten, remainingMovePoints + other.remainingMovePoints, movesGoingAway + other.movesGoingAway)

  def grade: Double =
    foodEaten * Parameter.foodWeight +
      remainingMovePoints * Parameter.remainingMovesWeight +
      movesGoingAway * Parameter.movesGoingAwayWeight

  override def compare(that: TestResult): Int = grade.compareTo(that.grade)
}

object TestResult {
  val empty: TestResult = TestResult(0, 0, 0)
}
