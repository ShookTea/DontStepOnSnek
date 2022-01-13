package eu.shooktea.dsos

case class Output(forward: Double, left: Double, right: Double) {
  def shouldGoForward(): Boolean = forward == Utils.max(forward, left, right)
  def shouldGoLeft(): Boolean = left == Utils.max(forward, left, right)
  def shouldGoRight(): Boolean = right == Utils.max(forward, left, right)
}

object Output {
  def apply(output: Seq[Double]): Output = Output(output.head, output(1), output(2))
}
