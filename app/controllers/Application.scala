package controllers

import play.api._
import play.api.mvc._
import cmd.partial.PartialCmdParser
import cmd.partial.PartialCmdLexer
import java.io.StringReader
import cmd.ast.Expr
import cmd.{ast => AST}
import cmd.partial.{ast => PAST}
import play.api.templates.Html

object Application extends Controller {
  
  def index(bla: String) = Action {
    val expr = new PartialCmdParser().parse(new PartialCmdLexer(new StringReader(bla))).asInstanceOf[Expr]
    Ok(views.html.index.render("Your application is super ready", 5, Html(paint(expr))))
  }
  
  def paint(s: AST.Symbol): String = s match {
    case AST.Ref(id) => paint(id)
    case AST.Ident(name) => "" + <span class="cname">{name}</span>
    case PAST.CApp(id, pl) => paint(id) + "(" + paint(pl)
    case PAST.ParamList(ap, np, isClosed) => paintParams(ap, np) + paintBool(isClosed)
    //ToDo: implement other cases
  }
  
  def paintParams(ap: Seq[AST.Expr], np: Seq[PAST.NParam]) = (ap.length, np.length) match {
    case (0, 0) => "" 
    case (0, _) => paintNParams(np)
    case (_, 0) => paintAParams(ap)
    case (_, _) => paintAParams(ap) + "," + paintNParams(np)
  }
  
  def paintAParams(ap: Seq[AST.Expr]): String = ap match {
    case a::ap if ap.isEmpty => paint(a)
    case a::ap => paint(a) + "," + paintAParams(ap)
  }
  
  def paintNParams(np: Seq[PAST.NParam]): String = np match {
    case n::np if np.isEmpty => paint(n)
    case n::np => paint(n) + "," + paintNParams(np)
  }
  
  def paintBool(b: Boolean) = b match {
    case true  => ")"
    case false => ""  
  }
  
}