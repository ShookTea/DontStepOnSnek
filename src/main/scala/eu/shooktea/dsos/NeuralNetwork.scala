package eu.shooktea.dsos

import NeuralNetwork._
import TypeAddons._

import java.util.UUID

class NeuralNetwork(
                     val weights: Seq[Double],
                     val generation: Int,
                     val identifier: String = UUID.randomUUID().toString
                   ) {

  if (weights.length != weightCount) {
    throw new Exception("Invalid weight count")
  }

  def apply(input: Input): Output =
    Output(calculateOutputNeurons(input))

  private def calculateOutputNeurons(input: Input): Seq[Double] =
    getOutputNeurons(
      getHiddenNeurons2(
        getHiddenNeurons1(
          input
        )
      )
    )

  private def getOutputNeurons(hidden2: Seq[Double]): Seq[Double] =
    for (o <- 0 until outputNeurons)
      yield getPartsForOutputNeuron(o, hidden2).sum.sigmoid

  private def getPartsForOutputNeuron(o: Int, hidden2: Seq[Double]): Seq[Double] =
    for (h2 <- 0 until hiddenNeurons2)
      yield hidden2(h2) * weights(inputNeurons * hiddenNeurons1 + hiddenNeurons1 * hiddenNeurons2 + o * hiddenNeurons2 + h2)

  private def getHiddenNeurons2(hidden1: Seq[Double]): Seq[Double] =
    for (h2 <- 0 until hiddenNeurons2)
      yield getPartsForHiddenNeuron2(h2, hidden1).sum.sigmoid

  private def getPartsForHiddenNeuron2(h2: Int, hidden1: Seq[Double]): Seq[Double] =
    for (h1 <- 0 until hiddenNeurons1)
      yield hidden1(h1) * weights(inputNeurons * hiddenNeurons1 + h2 * hiddenNeurons1 + h1)

  private def getHiddenNeurons1(input: Input): Seq[Double] =
    for (h <- 0 until hiddenNeurons1)
      yield getPartsForHiddenNeuron1(h, input).sum.sigmoid

  private def getPartsForHiddenNeuron1(h: Int, input: Input): Seq[Double] =
    for (i <- 0 until inputNeurons)
      yield input(i) * weights(h * inputNeurons + i)

  override def toString: String = identifier
}

object NeuralNetwork {
  val inputNeurons: Int = 48
  val hiddenNeurons1: Int = 26
  val hiddenNeurons2: Int = 26
  val outputNeurons: Int = 3

  val weightCount: Int = inputNeurons * hiddenNeurons1 + hiddenNeurons1 * hiddenNeurons2 + hiddenNeurons1 * outputNeurons

  val mutationProbability: Double = 0.07

  def random(): NeuralNetwork = new NeuralNetwork(
    for (_ <- 1 to weightCount) yield Utils.randomWeight(),
    1,
  )

  def mutate(nn: NeuralNetwork, mutationCount: Int): Seq[NeuralNetwork] =
    for (_ <- 1 to mutationCount) yield mutate(nn)

  def mutate(nn: NeuralNetwork): NeuralNetwork = new NeuralNetwork(
    mutateWeights(nn.weights),
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
        child.generation,
      )
  }

  private def createChild(a: NeuralNetwork, b: NeuralNetwork): NeuralNetwork = new NeuralNetwork(
    mixWeights(a, b),
    Math.max(a.generation, b.generation) + 1,
  )

  private def mixWeights(a: NeuralNetwork, b: NeuralNetwork): Seq[Double] =
    a.weights.zip(b.weights).map {
      case (aw, bw) => if (Utils.random.nextBoolean()) aw else bw
    }
}
