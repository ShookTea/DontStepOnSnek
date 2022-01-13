package eu.shooktea.dsos

case class Output(forward: Double, left: Double, right: Double)

object Output {
  def apply(output: Seq[Double]): Output = Output(output.head, output(1), output(2))
}
