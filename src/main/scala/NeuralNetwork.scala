package eu.shooktea.dsos

import NeuralNetwork.{hiddenNeurons, inputNeurons, outputNeurons}
import TypeAddons._

class NeuralNetwork(weights: Seq[Double]) {
  def apply(input: Input): Output =
    Output(calculateOutputNeurons(input))

  private def calculateOutputNeurons(input: Input): Seq[Double] =
    getOutputNeurons(getHiddenNeurons(input))

  private def getOutputNeurons(hidden: Seq[Double]): Seq[Double] =
    for (o <- 0 until outputNeurons)
      yield getPartsForOutputNeuron(o, hidden).sum.sigmoid

  private def getPartsForOutputNeuron(o: Int, hidden: Seq[Double]): Seq[Double] =
    for (h <- 0 until hiddenNeurons)
      yield hidden(h) * weights(inputNeurons * hiddenNeurons + o * hiddenNeurons + h)

  private def getHiddenNeurons(input: Input): Seq[Double] =
    for (h <- 0 until hiddenNeurons)
      yield getPartsForHiddenNeuron(h, input).sum.sigmoid

  private def getPartsForHiddenNeuron(h: Int, input: Input): Seq[Double] =
    for (i <- 0 until inputNeurons)
      yield input(i) * weights(h * inputNeurons + i)
}

object NeuralNetwork {
  val inputNeurons: Int = 24
  val hiddenNeurons: Int = 14
  val outputNeurons: Int = 3

  val weightCount: Int = inputNeurons * hiddenNeurons + hiddenNeurons * outputNeurons

  def random(): NeuralNetwork = new NeuralNetwork(
    for (_ <- 1 to weightCount) yield 2.0 * (Utils.random.nextDouble() - 0.5)
  )
}
