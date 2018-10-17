import e._

object Test {
  def main(args: Array[String]): Unit = {
    try {
      (E.A : @unchecked) match { case E.B => }
    } catch {
      case me: MatchError => println(me.getMessage()); return
    }
    ???
  }
}
