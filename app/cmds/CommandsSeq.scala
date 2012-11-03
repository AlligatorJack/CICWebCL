package cmds

import cmddef.Param
import cmddef.ParamTyped
import cmddef.Command
import cmd.interpreter.{value => VAL}
import scala.collection.immutable.Seq

object CommandsSeq {
  val cmds: Seq[Command] = Seq(Move, Copy, Email)
}
