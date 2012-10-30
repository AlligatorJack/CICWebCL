package cmds

import cmddef.Param
import cmddef.ParamTyped
import cmddef.Command
import cmd.interpreter.{value => VAL}

object CommandsSeq {
  val cmds: Seq[Command] = Seq(Fuck, Move, Copy, Email)
}
