package cmds
import cmd.interpreter.value.Value
import cmd.interpreter.Type
import cmddef.Param
import cmddef.ParamTyped
import cmddef.Command

object Copy extends Command {
  val name = "copy"
  val help = "Kommandohilfe"
  val returnType = "String"
  val params: List[Param] = List(new ParamTyped[String]("src", "src is source path", { (comp: Option[String]) =>
     
	List("C:\\Programme", "C:\\Projects", "C:\\Pride").filter(p => p.startsWith(comp.getOrElse("")))

  }, Type.String, None), new ParamTyped[String]("dest", "dest is destination path", { (comp: Option[String]) =>
     
	List("D:\\Sport", "D:\\Scala", "D:\\Soul").filter(p => p.startsWith(comp.getOrElse("")))

  }, Type.String, None))
  def apply(src: String, dest: String): String = {
     "copied from " + src + " to " + dest 
  }
}