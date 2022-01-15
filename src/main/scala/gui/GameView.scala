package eu.shooktea.dsos
package gui

import gui.GameView._

import java.awt.{Color, Toolkit}
import javax.swing.{JFrame, JPanel, WindowConstants}

class GameView(nn: NeuralNetwork) extends JPanel {
  def start(): Unit = {
    val frame = new JFrame()
    frame setDefaultCloseOperation WindowConstants.EXIT_ON_CLOSE
    frame setTitle s"Don't step on snek - simulation on snake from generation ${nn.generation}"
    frame add this

    val mapSize: Point = Point(Parameter.mapWidth + 2, Parameter.mapHeight + 2)
    val frameSize: Point = mapSize * tileSizeInPixels

    val screenSize: Point = Toolkit.getDefaultToolkit.getScreenSize match {
      case d => Point(d.width, d.height)
    }
    val position: Point = (screenSize - frameSize) / 2

    frame setSize (frameSize.x, frameSize.y)
    frame setLocation (position.x, position.y)

    frame setVisible true
  }
}

object GameView {
  val tileSizeInPixels: Int = 10
  def apply(nn: NeuralNetwork): GameView = new GameView(nn)
}
