import e._

object Test extends App {
  def test(e: E) = (e: @annotation.switch) match {
    case E.A0 | E.A1 | E.A2 | E.A3 | E.A4 | E.A5 | E.A6 | E.A7 | E.A8 | E.A9 =>
      "foo"
    case E.Z0 | E.Z1 | E.Z2 | E.Z3 | E.Z4 | E.Z5 | E.Z6 | E.Z7 | E.Z8 | E.Z9 =>
      "bar"
    case E.Z10 | E.Z11 | E.Z12 | E.Z13 | E.Z14 | E.Z15 | E.Z16 | E.Z17 | E.Z18 | E.Z19 =>
      "baz"
    case _ =>
      "bip"
  }

  println(test(E.A0))  // foo
  println(test(E.B0))  // bip
  println(test(E.X9))  // bip
  println(test(E.Z9))  // bar
  println(test(E.Y10)) // bip
  println(test(E.Z13)) // baz
}
