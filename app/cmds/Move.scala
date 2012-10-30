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
  val params: List[Param] = List(new ParamTyped[String]("src", "hilfe fuer p1", { (comp: Option[String]) =>
     
	List("C:\\Programme", "C:\\Projects", "C:\\Pizdec").filter(p => p.startsWith(comp.getOrElse("")))

  }, Type.String, None), new ParamTyped[String]("dest", "hilfe fuer p2", { (comp: Option[String]) =>
     
	List("D:\\Sport", "D:\\Scala", "D:\\Suki").filter(p => p.startsWith(comp.getOrElse("")))

  }, Type.String, None))
  def apply(src: String, dest: String): String = {
     "moved from " + src + " to " + dest 
  }
}