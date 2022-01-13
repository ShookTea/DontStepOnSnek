package eu.shooktea.dsos

object Main {
  def main(args: Array[String]): Unit = {

    for (i <- 1 to 2000) {
      val neuralNetwork = NeuralNetwork.random()
      print(s"$i,")
      Tester.testNetwork(neuralNetwork)
//      println(s"RESULT FOR ITERATION $i = " + Tester.testNetwork(neuralNetwork))
    }
  }
}
