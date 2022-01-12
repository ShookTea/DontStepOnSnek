package eu.shooktea.dsos

object Main {
  def main(args: Array[String]): Unit = {
    var board = Board(50, 15)

    for (_ <- 1 to 100) {
      println(board)
      board = Utils.random.nextInt(3) match {
        case 0 => board.moveForward()
        case 1 => board.moveRight()
        case 2 => board.moveLeft()
      }
    }
  }
}
