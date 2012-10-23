package colors;

import org.scalatest.FunSuite
import org.scalatest.matchers.ShouldMatchers._
import cmd.partial.PartialCmdParser
import cmd.partial.PartialCmdLexer
import cmd.ast.{Seq => ASeq, String => AString, LocalValDefs => _, CApp => _, ParamList => _, _}
import cmd.partial.ast._
import java.io.StringReader
import controllers.Application._


class ColorizeTestSuite extends FunSuite {

  def parse(input: String): Expr = new PartialCmdParser().parse(new PartialCmdLexer(new StringReader(input))).asInstanceOf[Expr]

  val testCase1 = "cmd"

  test("Parse \"" + testCase1 + "\"") {
    println(colorize(parse(testCase1)))
  }

  val testCase2 = "cmd("

  test("Parse \"" + testCase2 + "\"") {
    println(colorize(parse(testCase2)))
  }

  val testCase3 = "cmd(1"

  test("Parse \"" + testCase3 + "\"") {
    println(colorize(parse(testCase3)))
  }

  val testCase4 = "cmd(1,"

  test("Parse \"" + testCase4 + "\"") {
    println(colorize(parse(testCase4)))
  }

  val testCase5 = "cmd(1, 2"

  test("Parse \"" + testCase5 + "\"") {
    println(colorize(parse(testCase5)))
  }

  val testCase6 = "cmd(1, 2)"

  test("Parse \"" + testCase6 + "\"") {
    println(colorize(parse(testCase6)))
  }

  val testCase7 = "cmd(p1"

  test("Parse \"" + testCase7 + "\"") {
    println(colorize(parse(testCase7)))
  }

  val testCase8 = "cmd(p1 ="

  test("Parse \"" + testCase8 + "\"") {
    println(colorize(parse(testCase8)))
  }

  val testCase9 = "cmd(p1 = 1"

  test("Parse \"" + testCase9 + "\"") {
    println(colorize(parse(testCase9)))
  }

  val testCase10 = "cmd(p1 = 1,"

  test("Parse \"" + testCase10 + "\"") {
    println(colorize(parse(testCase10)))
  }

  val testCase11 = "cmd(p1 = 1, p2"

  test("Parse \"" + testCase11 + "\"") {
    println(colorize(parse(testCase11)))
  }

  val testCase12 = "cmd(p1 = 1, p2 = 2)"

  test("Parse \"" + testCase12 + "\"") {
    println(colorize(parse(testCase12)))
  }

  val testCase13 = "cmd1(1, cmd2("

  test("Parse \"" + testCase13 + "\"") {
    println(colorize(parse(testCase13)))
  }

  val testCase14 = "cmd1(1, cmd2(1"

  test("Parse \"" + testCase14 + "\"") {
    println(colorize(parse(testCase14)))
  }

  val testCase15 = "cmd1(1, cmd2(1)"

  test("Parse \"" + testCase15 + "\"") {
    println(colorize(parse(testCase15)))
  }

  val testCase16 = "cmd1(1, cmd2(1))"

  test("Parse \"" + testCase16 + "\"") {
    println(colorize(parse(testCase16)))
  }
  
  val testCase17 = "let"

  test("Parse \"" + testCase17 + "\"") {
    println(colorize(parse(testCase17)))
  }

  val testCase18 = "let v1"

  test("Parse \"" + testCase18 + "\"") {
    println(colorize(parse(testCase18)))
  }

  val testCase19 = "let v1 ="

  test("Parse \"" + testCase19 + "\"") {
    println(colorize(parse(testCase19)))
  }

  val testCase20 = "let v1 = 1"

  test("Parse \"" + testCase20 + "\"") {
    println(colorize(parse(testCase20)))
  }

  val testCase21 = "let v1 = 1;"

  test("Parse \"" + testCase21 + "\"") {
    println(colorize(parse(testCase21)))
  }

  val testCase22 = "let v1 = 1; v2"

  test("Parse \"" + testCase22 + "\"") {
    println(colorize(parse(testCase22)))
  }

  val testCase23 = "let v1 = 1; v2 = 2"

  test("Parse \"" + testCase23 + "\"") {
    println(colorize(parse(testCase23)))
  }

  val testCase24 = "let v1 = 1; v2 = 2 in"

  test("Parse \"" + testCase24 + "\"") {
    println(colorize(parse(testCase24)))
  }

  val testCase25 = "let v1 = 1; v2 = 2 in cmd(v1, v2)"

  test("Parse \"" + testCase25 + "\"") {
    println(colorize(parse(testCase25)))
  }

}
