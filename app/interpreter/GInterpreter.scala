package interpreter
import cmds._
import collection.immutable.IndexedSeq
import cmddef.Command
import cmd.interpreter.{value => VAL}
import cmd.interpreter.BaseInterpreter

object GInterpreter extends BaseInterpreter {
  def applyCommand(name: String, values: IndexedSeq[VAL.Value]): VAL.Value =
    name match {
      case "email" => VAL.Integer(Email.apply(values(0).getBool, values(1).getInt, values(2).getBool))
      case "move" => VAL.String(Move.apply(values(0).getString, values(1).getString))
      case "fuck" => VAL.Unit(Fuck.apply(values(0).getString, values(1).getString, values(2).getInt, values(3).getBool))
      case "copy" => VAL.String(Copy.apply(values(0).getString, values(1).getString))
    }
}
