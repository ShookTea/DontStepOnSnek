package eu.shooktea.dsos

import gui.GameView

object Main {
  def main(args: Array[String]): Unit = {
    if (args.head == "evolve") runEvolutionsFromStart(args(1))
    else if (args.head == "continue") continueEvolutionsFromFile(args(1))
    else if (args.head == "run") runFromFile(args(1))
  }

  private def runEvolutionsFromStart(path: String): Unit =
    runEvolution(
      NetworkClass.random(),
      path
    )

  private def continueEvolutionsFromFile(path: String): Unit =
    runEvolution(
      Persistence.load(path),
      path
    )

  private def runEvolution(startClass: NetworkClass, path: String): NetworkClass = {
    var currentClass = startClass
    while (true) {
      val newClass = NetworkClass.evolve(currentClass)
      Persistence.save(currentClass, path)
      currentClass = newClass
    }
    currentClass
  }

  private def runFromFile(path: String): Unit = {
    val classFromFile = Persistence.load(path)
    val bestNeuralNetwork = classFromFile.getBest.head._1

    GameView(bestNeuralNetwork).start()
  }
}
