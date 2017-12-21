import scala.tools.nsc._
import scala.tools.nsc.interpreter.shell.ReplReporterImpl
import scala.tools.partest.ReplTest

object Test extends App {
/* commented out since Enumeration changes break with scala-xml for M2
   TODO: uncomment when scala-xml for M3 is released
  override def extraSettings = "-deprecation"
  def code = <code>
// basics
3+4
def gcd(x: Int, y: Int): Int = {{
          if (x == 0) y
          else if (y == 0) x
          else gcd(y%x, x)
}}
val five = gcd(15,35)
var x = 1
x = 2
val three = x+1
type anotherint = Int
val four: anotherint = 4
val bogus: anotherint = "hello"
trait PointlessTrait
val (x,y) = (2,3)
println("hello")

// ticket #1513
val t1513 = Array(null)
// ambiguous toString problem from #547
val atom = new scala.xml.Atom(())
// overriding toString problem from #1404
class S(override val toString : String)
val fish = new S("fish")
// Test that arrays pretty print nicely.
val arr = Array("What's", "up", "doc?")
// Test that arrays pretty print nicely, even when we give them type Any
val arrInt : Any = Array(1,2,3)
// Test that nested arrays are pretty-printed correctly
val arrArrInt : Any = Array(Array(1, 2), Array(3, 4))

// implicit conversions
case class Foo(n: Int)
case class Bar(n: Int)
implicit def foo2bar(foo: Foo) = Bar(foo.n)
val bar: Bar = Foo(3)

// importing from a previous result
import bar._
val m = n

// stressing the imports mechanism
val one = 1
val one = 1
val one = 1
val one = 1
val one = 1
val one = 1
val one = 1
val one = 1
val one = 1
val one = 1
val one = 1
val one = 1
val one = 1
val one = 1
val one = 1
val one = 1
val one = 1
val one = 1
val one = 1
val one = 1


val x1 = 1
val x2 = 1
val x3 = 1
val x4 = 1
val x5 = 1
val x6 = 1
val x7 = 1
val x8 = 1
val x9 = 1
val x10 = 1
val x11 = 1
val x12 = 1
val x13 = 1
val x14 = 1
val x15 = 1
val x16 = 1
val x17 = 1
val x18 = 1
val x19 = 1
val x20 = 1

val two = one + x5

// handling generic wildcard arrays (#2386)
// It's put here because type feedback is an important part of it.
val xs: Array[_] = Array(1, 2)
xs.size
xs.head
xs filter (_ == 2)
xs map (_ => "abc")
xs map (x => x)
xs map (x => (x, x))

// interior syntax errors should *not* go into multi-line input mode.
// both of the following should abort immediately:
def x => y => z
[1,2,3]


// multi-line XML
&lt;a>
&lt;b
  c="c"
  d="dd"
/>&lt;/a>


/*
  /*
    multi-line comment
  */
*/


// multi-line string
"""
hello
there
"""

(1 +   // give up early by typing two blank lines


// defining and using quoted names should work (ticket #323)
def `match` = 1
val x = `match`

// multiple classes defined on one line
sealed class Exp; class Fact extends Exp; class Term extends Exp
def f(e: Exp) = e match {{  // non-exhaustive warning here
  case _:Fact => 3
}}

</code>.text

  def appendix() = {
    val settings = new Settings
    settings.classpath.value = sys.props("java.class.path")
    val interp = new interpreter.IMain(settings, new ReplReporterImpl(settings))
    interp.interpret("def plusOne(x: Int) = x + 1")
    interp.interpret("plusOne(5)")
    interp.reset()
    interp.interpret("\"after reset\"")
    interp.interpret("plusOne(5) // should be undefined now")
  }

  appendix()
 */
  // temp echo to avoid having to delete the checkfile
  print(
    s"""
      |scala> // basics
      |
      |scala> 3+4
      |res0: Int = 7
      |
      |scala> def gcd(x: Int, y: Int): Int = {
      |          if (x == 0) y
      |          else if (y == 0) x
      |          else gcd(y%x, x)
      |}
      |gcd: (x: Int, y: Int)Int
      |
      |scala> val five = gcd(15,35)
      |five: Int = 5
      |
      |scala> var x = 1
      |x: Int = 1
      |
      |scala> x = 2
      |mutated x
      |
      |scala> val three = x+1
      |three: Int = 3
      |
      |scala> type anotherint = Int
      |defined type alias anotherint
      |
      |scala> val four: anotherint = 4
      |four: anotherint = 4
      |
      |scala> val bogus: anotherint = "hello"
      |                               ^
      |       error: type mismatch;
      |        found   : String("hello")
      |        required: anotherint
      |           (which expands to)  Int
      |
      |scala> trait PointlessTrait
      |defined trait PointlessTrait
      |
      |scala> val (x,y) = (2,3)
      |x: Int = 2
      |y: Int = 3
      |
      |scala> println("hello")
      |hello
      |
      |scala>${' '}
      |
      |scala> // ticket #1513
      |
      |scala> val t1513 = Array(null)
      |t1513: Array[Null] = Array(null)
      |
      |scala> // ambiguous toString problem from #547
      |
      |scala> val atom = new scala.xml.Atom(())
      |atom: scala.xml.Atom[Unit] = ()
      |
      |scala> // overriding toString problem from #1404
      |
      |scala> class S(override val toString : String)
      |defined class S
      |
      |scala> val fish = new S("fish")
      |fish: S = fish
      |
      |scala> // Test that arrays pretty print nicely.
      |
      |scala> val arr = Array("What's", "up", "doc?")
      |arr: Array[String] = Array(What's, up, doc?)
      |
      |scala> // Test that arrays pretty print nicely, even when we give them type Any
      |
      |scala> val arrInt : Any = Array(1,2,3)
      |arrInt: Any = Array(1, 2, 3)
      |
      |scala> // Test that nested arrays are pretty-printed correctly
      |
      |scala> val arrArrInt : Any = Array(Array(1, 2), Array(3, 4))
      |arrArrInt: Any = Array(Array(1, 2), Array(3, 4))
      |
      |scala>${' '}
      |
      |scala> // implicit conversions
      |
      |scala> case class Foo(n: Int)
      |defined class Foo
      |
      |scala> case class Bar(n: Int)
      |defined class Bar
      |
      |scala> implicit def foo2bar(foo: Foo) = Bar(foo.n)
      |warning: there was one feature warning; for details, enable `:setting -feature' or `:replay -feature'
      |foo2bar: (foo: Foo)Bar
      |
      |scala> val bar: Bar = Foo(3)
      |bar: Bar = Bar(3)
      |
      |scala>${' '}
      |
      |scala> // importing from a previous result
      |
      |scala> import bar._
      |import bar._
      |
      |scala> val m = n
      |m: Int = 3
      |
      |scala>${' '}
      |
      |scala> // stressing the imports mechanism
      |
      |scala> val one = 1
      |one: Int = 1
      |
      |scala> val one = 1
      |one: Int = 1
      |
      |scala> val one = 1
      |one: Int = 1
      |
      |scala> val one = 1
      |one: Int = 1
      |
      |scala> val one = 1
      |one: Int = 1
      |
      |scala> val one = 1
      |one: Int = 1
      |
      |scala> val one = 1
      |one: Int = 1
      |
      |scala> val one = 1
      |one: Int = 1
      |
      |scala> val one = 1
      |one: Int = 1
      |
      |scala> val one = 1
      |one: Int = 1
      |
      |scala> val one = 1
      |one: Int = 1
      |
      |scala> val one = 1
      |one: Int = 1
      |
      |scala> val one = 1
      |one: Int = 1
      |
      |scala> val one = 1
      |one: Int = 1
      |
      |scala> val one = 1
      |one: Int = 1
      |
      |scala> val one = 1
      |one: Int = 1
      |
      |scala> val one = 1
      |one: Int = 1
      |
      |scala> val one = 1
      |one: Int = 1
      |
      |scala> val one = 1
      |one: Int = 1
      |
      |scala> val one = 1
      |one: Int = 1
      |
      |scala>${' '}
      |
      |scala>${' '}
      |
      |scala> val x1 = 1
      |x1: Int = 1
      |
      |scala> val x2 = 1
      |x2: Int = 1
      |
      |scala> val x3 = 1
      |x3: Int = 1
      |
      |scala> val x4 = 1
      |x4: Int = 1
      |
      |scala> val x5 = 1
      |x5: Int = 1
      |
      |scala> val x6 = 1
      |x6: Int = 1
      |
      |scala> val x7 = 1
      |x7: Int = 1
      |
      |scala> val x8 = 1
      |x8: Int = 1
      |
      |scala> val x9 = 1
      |x9: Int = 1
      |
      |scala> val x10 = 1
      |x10: Int = 1
      |
      |scala> val x11 = 1
      |x11: Int = 1
      |
      |scala> val x12 = 1
      |x12: Int = 1
      |
      |scala> val x13 = 1
      |x13: Int = 1
      |
      |scala> val x14 = 1
      |x14: Int = 1
      |
      |scala> val x15 = 1
      |x15: Int = 1
      |
      |scala> val x16 = 1
      |x16: Int = 1
      |
      |scala> val x17 = 1
      |x17: Int = 1
      |
      |scala> val x18 = 1
      |x18: Int = 1
      |
      |scala> val x19 = 1
      |x19: Int = 1
      |
      |scala> val x20 = 1
      |x20: Int = 1
      |
      |scala>${' '}
      |
      |scala> val two = one + x5
      |two: Int = 2
      |
      |scala>${' '}
      |
      |scala> // handling generic wildcard arrays (#2386)
      |
      |scala> // It's put here because type feedback is an important part of it.
      |
      |scala> val xs: Array[_] = Array(1, 2)
      |xs: Array[_] = Array(1, 2)
      |
      |scala> xs.size
      |res2: Int = 2
      |
      |scala> xs.head
      |res3: Any = 1
      |
      |scala> xs filter (_ == 2)
      |res4: Array[_] = Array(2)
      |
      |scala> xs map (_ => "abc")
      |res5: Array[String] = Array(abc, abc)
      |
      |scala> xs map (x => x)
      |res6: Array[_] = Array(1, 2)
      |
      |scala> xs map (x => (x, x))
      |warning: there was one feature warning; for details, enable `:setting -feature' or `:replay -feature'
      |res7: Array[(_$$1, _$$1)] forSome { type _$$1 } = Array((1,1), (2,2))
      |
      |scala>${' '}
      |
      |scala> // interior syntax errors should *not* go into multi-line input mode.
      |
      |scala> // both of the following should abort immediately:
      |
      |scala> def x => y => z
      |             ^
      |       error: '=' expected but '=>' found.
      |
      |scala> [1,2,3]
      |       ^
      |       error: illegal start of definition
      |
      |scala>${' '}
      |
      |scala>${' '}
      |
      |scala> // multi-line XML
      |
      |scala> <a>
      |<b
      |  c="c"
      |  d="dd"
      |/></a>
      |res8: scala.xml.Elem =
      |<a>
      |<b c="c" d="dd"/></a>
      |
      |scala>${' '}
      |
      |scala>${' '}
      |
      |scala> /*
      |  /*
      |    multi-line comment
      |  */
      |*/
      |
      |scala>${' '}
      |
      |scala>${' '}
      |
      |scala> // multi-line string
      |
      |scala> "${'"'}"
      |hello
      |there
      |"${'"'}"
      |res9: String =
      |"
      |hello
      |there
      |"
      |
      |scala>${' '}
      |
      |scala> (1 +   // give up early by typing two blank lines
      |
      |
      |You typed two blank lines.  Starting a new command.
      |
      |scala> // defining and using quoted names should work (ticket #323)
      |
      |scala> def `match` = 1
      |match: Int
      |
      |scala> val x = `match`
      |x: Int = 1
      |
      |scala>${' '}
      |
      |scala> // multiple classes defined on one line
      |
      |scala> sealed class Exp; class Fact extends Exp; class Term extends Exp
      |defined class Exp
      |defined class Fact
      |defined class Term
      |
      |scala> def f(e: Exp) = e match {  // non-exhaustive warning here
      |  case _:Fact => 3
      |}
      |                       ^
      |       warning: match may not be exhaustive.
      |       It would fail on the following inputs: Exp(), Term()
      |f: (e: Exp)Int
      |
      |scala> :quit
      |plusOne: (x: Int)Int
      |res0: Int = 6
      |res0: String = after reset
      |       ^
      |       error: not found: value plusOne""".stripMargin)
}
