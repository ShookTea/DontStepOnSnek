package eu.shooktea.dsos
package gui

import gui.GameView._

import java.awt.{Color, Graphics, Graphics2D, Toolkit}
import javax.swing.{JFrame, JPanel, WindowConstants}
import TypeAddons._

class GameView(nn: NeuralNetwork) extends JPanel {
  var currentBoard: Board = Board()

  def start(): Unit = {
    val frame = new JFrame()
    frame setDefaultCloseOperation WindowConstants.EXIT_ON_CLOSE
    frame setTitle s"Don't step on snek - simulation on snake from generation ${nn.generation}"
    frame add this

    val mapSize: Point = Point(Parameter.mapWidth + 2, Parameter.mapHeight + 2)
    val frameSize: Point = mapSize * tileSizeInPixels + Point(margin, margin) * 2

    val screenSize: Point = Toolkit.getDefaultToolkit.getScreenSize match {
      case d => Point(d.width, d.height)
    }
    val position: Point = (screenSize - frameSize) / 2

    frame setSize (frameSize.x, frameSize.y)
    frame setLocation (position.x, position.y)

    frame setVisible true
    runSimulation()
  }

  private def runSimulation(): Unit = {
    var movePoints = Parameter.startingMovePoints
    var currentPoints = 0
    while (movePoints > 0) {
      movePoints -= 1
      Thread.sleep(75)

      val nextMove = nn(currentBoard.getNeuralNetworkInput)
      currentBoard =
        if (nextMove.shouldGoLeft()) currentBoard.moveLeft()
        else if (nextMove.shouldGoRight()) currentBoard.moveRight()
        else currentBoard.moveForward()

      if (currentBoard.points > currentPoints) {
        currentPoints = currentBoard.points
        movePoints += Parameter.movePointsPerFood
      }

      this.repaint()
    }
  }

  override def paintComponent(g: Graphics): Unit = {
    super.paintComponent(g)
    val g2 = g.asInstanceOf[Graphics2D]

    for (p <- Point(0, 0) until Point(Parameter.mapWidth + 2, Parameter.mapHeight + 2)) {
      val drawPoint: Point = tileSizeInPixels * p
      val mapPoint = p - Point(1, 1)

      g2.setColor(
        if (mapPoint.x < 0 || mapPoint.y < 0 || mapPoint.x >= Parameter.mapWidth || mapPoint.y >= Parameter.mapHeight) Color.black
        else if (mapPoint == currentBoard.food) Color.gray
        else if (mapPoint in currentBoard.snake) Color.green
        else Color.white
      )
      g2.fillRect(margin + drawPoint.x, margin + drawPoint.y, tileSizeInPixels, tileSizeInPixels)
      g2 setColor new Color(0, 0, 0, 15)
      g2.drawRect(margin + drawPoint.x, margin + drawPoint.y, tileSizeInPixels, tileSizeInPixels)
    }
  }
}

object GameView {
  val tileSizeInPixels: Int = 10
  val margin: Int = 40
  def apply(nn: NeuralNetwork): GameView = new GameView(nn)
}
