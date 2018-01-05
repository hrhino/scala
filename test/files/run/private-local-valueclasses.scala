object Test extends App {

  /* please ignore overflow considerations */

  implicit class `add .succ to Int`(i: Int) extends AnyVal {
    def succ = i + 1
  }

  implicit class `add .pred to Int`(private[this] val j: Int) extends AnyVal {
    def pred = j + 1
  }

  implicit class `add .double to Int`(protected[this] val k: Int) extends AnyVal {
    def double = k * 2
  }

  println(1.succ)
  println(2.pred)
  println(3.double)

}