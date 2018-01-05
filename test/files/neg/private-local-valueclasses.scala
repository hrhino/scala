object Foo {

  implicit class `add .mogrify to String`(s: String) {
    def mogrify = ???
  }

  "asdf".mogrify // fine
  "fsda".s // not found

}