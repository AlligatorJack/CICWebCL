package cmds
import cmd.interpreter.value.Value
import cmd.interpreter.Type
import cmddef.Param
import cmddef.ParamTyped
import cmddef.Command

object Fuck extends Command {
  val name = "fuck"
  val help = "how to fuck"
  val returnType = "Unit"
  val params: List[Param] = List(new ParamTyped[String]("me", "hilfe fuer p1", { (p1comp: Option[String]) =>
     List() 
  }, Type.String, None), new ParamTyped[String]("dest", "hilfe fuer p2", { (p2comp: Option[String]) =>
     List() 
  }, Type.String, None), new ParamTyped[Int]("times", "hilfe fuer p3", { (p3comp: Option[Int]) =>
     List() 
  }, Type.Integer, None), new ParamTyped[Boolean]("cute", "is she cute?", { (p4comp: Option[Boolean]) =>
     List() 
  }, Type.Boolean, None))
  def apply(me: String, dest: String, times: Int, cute: Boolean): Unit = {
     "I will fuck you soon" 
  }
}
