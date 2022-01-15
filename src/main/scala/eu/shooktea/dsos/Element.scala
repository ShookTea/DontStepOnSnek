package eu.shooktea.dsos

sealed class Element(val displayCode: Char)

object Elements {
  case object Empty extends Element(' ')
  case object SnakeBody extends Element('*')
  case object SnakeHead extends Element('@')
  case object SnakeTail extends Element('+')
}
