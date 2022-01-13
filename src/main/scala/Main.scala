package eu.shooktea.dsos

object Main {
  def main(args: Array[String]): Unit = {
    val neuralNetwork = NeuralNetwork.random()

    Tester.testNetwork(neuralNetwork)
  }
}
