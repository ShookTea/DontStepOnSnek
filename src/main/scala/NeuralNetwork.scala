package eu.shooktea.dsos

import NeuralNetwork._
import TypeAddons._

class NeuralNetwork(
                     val weights: Seq[Double],
                     val identifier: String,
                     val generation: Int,
                   ) {
  if (weights.length != weightCount) {
    throw new Exception("Invalid weight count")
  }

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

  override def toString: String = identifier
}

object NeuralNetwork {
  val inputNeurons: Int = 24
  val hiddenNeurons: Int = 14
  val outputNeurons: Int = 3

  val weightCount: Int = inputNeurons * hiddenNeurons + hiddenNeurons * outputNeurons

  val mutationProbability: Double = 0.07

  def random(): NeuralNetwork = new NeuralNetwork(
    for (_ <- 1 to weightCount) yield Utils.randomWeight(),
    Utils.randomIdentifier(),
    1,
  )

  def mutate(nn: NeuralNetwork, mutationCount: Int): Seq[NeuralNetwork] =
    for (_ <- 1 to mutationCount) yield mutate(nn)

  def mutate(nn: NeuralNetwork): NeuralNetwork = new NeuralNetwork(
    mutateWeights(nn.weights),
    Utils.randomIdentifier(),
    nn.generation + 1,
  )

  private def mutateWeights(weights: Seq[Double]): Seq[Double] = weights.map(
    w => if (Utils.random.nextDouble() < mutationProbability) Utils.randomWeight() else w
  )

  def createChildren(a: NeuralNetwork, b: NeuralNetwork, mutationCount: Int): Seq[NeuralNetwork] = {
    val child = createChild(a, b)
    for (i <- 0 to mutationCount) yield
      if (i == 0) child
      else new NeuralNetwork(
        mutateWeights(child.weights),
        Utils.randomIdentifier(),
        child.generation,
      )
  }

  private def createChild(a: NeuralNetwork, b: NeuralNetwork): NeuralNetwork = new NeuralNetwork(
    mixWeights(a, b),
    Utils.randomIdentifier(),
    Math.max(a.generation, b.generation) + 1,
  )

  private def mixWeights(a: NeuralNetwork, b: NeuralNetwork): Seq[Double] =
    a.weights.zip(b.weights).map {
      case (aw, bw) => if (Utils.random.nextBoolean()) aw else bw
    }
}
