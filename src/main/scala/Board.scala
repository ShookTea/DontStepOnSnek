package eu.shooktea.dsos

class Board(elements: Seq[Seq[Element]]) {
  def height: Int = elements.length

  def width: Int = elements.head.length

  override def toString: String =
    "#".repeat(width + 2) + "\n" +
    elements.map(row => "#" + row.map(r => r.displayCode).mkString + "#").mkString("\n") + "\n" +
    "#".repeat(width + 2)

}
object Board {
  def apply(elements: Seq[Seq[Element]]): Board = new Board(elements)

  def apply(width: Int, height: Int): Board = Board(
    for (_ <- 0 until height) yield
      for (_ <- 0 until width) yield Elements.Empty
  )
}
