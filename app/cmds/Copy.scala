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
  val params: List[Param] = List(new ParamTyped[String]("src", "src is source path", { (p1comp: Option[String]) =>
     
	val list = List("C:\\Programme", "C:\\Projects", "C:\\Pizdec")
	if (p1comp.isEmpty) list
	else list.filter(x => x.substring(0, p1comp.get.length) == p1comp.get) 
  }, Type.String, None), new ParamTyped[String]("dest", "dest is destination path", { (p2comp: Option[String]) =>
     
	val list = List("D:\\Sport", "D:\\Scala", "D:\\Suki")
	if (p2comp.isEmpty) list
	else list.filter(x => x.substring(0, p2comp.get.length) == p2comp.get) 
  }, Type.String, None))
  def apply(src: String, dest: String): String = {
     "copied from " + src + " to " + dest 
  }
}