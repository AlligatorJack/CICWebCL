package cmds
import cmd.interpreter.value.Value
import cmd.interpreter.Type
import cmddef.Param
import cmddef.ParamTyped
import cmddef.Command

object Email extends Command {
  val name = "email"
  val help = "Kommandohilfe"
  val returnType = "Integer"
  val params: List[Param] = List(new ParamTyped[Boolean]("fast", "hilfe fuer p1", { (p1comp: Option[Boolean]) =>
     List() 
  }, Type.Boolean, None), new ParamTyped[Integer]("einInt", "hilfe fuer p2", { (p2comp: Option[Integer]) =>
     List() 
  }, Type.Integer, None), new ParamTyped[Boolean]("einBool", "hilfe fuer p3", { (p3comp: Option[Boolean]) =>
     List() 
  }, Type.Boolean, None))
  def apply(fast: Boolean, einInt: Integer, einBool: Boolean): Integer = {
     55 
  }
}