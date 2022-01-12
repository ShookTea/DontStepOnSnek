package eu.shooktea.dsos

import NeuralNetwork.{hiddenNeurons, inputNeurons}
import TypeAddons._

class NeuralNetwork(weights: Seq[Double]) {
  def apply(input: Input) = {
    println(getHiddenNeurons(input))
  }

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
    for (_ <- 1 to weightCount) yield Utils.random.nextDouble()
  )
}
