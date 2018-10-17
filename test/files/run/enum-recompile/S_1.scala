package e

object S {
  type Enum = e.Enum
   val A    = e.Enum.A
   val B    = e.Enum.B

  def values = foo.Enum.values.toList

  def stringy(e: Enum): String = e match {
    case A => "A"
    case B => "B"
  }
}