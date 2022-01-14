package eu.shooktea.dsos

object Parameter {
  /** How many boards does each NN get to make a grade? */
  val testIterations: Int = 20

  /** Map sizes */
  val mapWidth: Int = 50
  val mapHeight: Int = 15

  /** How many move points NN gets on start? */
  val startingMovePoints: Int = 200
  /** How many move points NN receives after eating food? */
  val movePointsPerFood: Int = 90

  /** How many best NNs graduate to next class? */
  val graduationCount: Int = 15
  /** How many mutated clones are created for each graduated NN? */
  val mutationCount: Int = 40
  /** How many new networks must be randomly generated on start of a round? */
  val classSize: Int = ((mutationCount + 1) * graduationCount * (graduationCount + 1)) / 2

  /** Weights of test results for final grade */
  val foodWeight: Double = 9.0
  val remainingMovesWeight: Double = 0.1
  val movesGoingAwayWeight: Double = -0.25
}
