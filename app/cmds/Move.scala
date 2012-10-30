package cmds
import cmd.interpreter.value.Value
import cmd.interpreter.Type
import cmddef.Param
import cmddef.ParamTyped
import cmddef.Command

object Move extends Command {
  val name = "move"
  val help = "Kommandohilfe fuer move"
  val returnType = "String"
  val params: List[Param] = List(new ParamTyped[String]("src", "hilfe fuer p1", { (p1comp: Option[String]) =>
     
	val list = List("D:\\bank", "D:\\blockHouse", "D:\\boobsDance")
	if (p1comp.isEmpty) list
	else list.filter(x => x.substring(0, p1comp.get.length) == p1comp.get) 
  }, Type.String, None), new ParamTyped[String]("dest", "hilfe fuer p2", { (p2comp: Option[String]) =>
     
	val list = List("D:\\home", "D:\\here", "D:\\hurryUp")
	if (p2comp.isEmpty) list
	else list.filter(x => x.substring(0, p2comp.get.length) == p2comp.get) 
  }, Type.String, None))
  def apply(src: String, dest: String): String = {
     "moved from " + src + " to " + dest 
  }
}