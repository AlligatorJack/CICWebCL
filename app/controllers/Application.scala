package controllers

import play.api._
import play.api.mvc._
import cmd.partial.PartialCmdParser
import cmd.partial.PartialCmdLexer
import java.io.StringReader
import cmd.ast.Expr

object Application extends Controller {
  
  def index(bla: String) = Action {
    val s = new PartialCmdParser().parse(new PartialCmdLexer(new StringReader(bla))).asInstanceOf[Expr]
    Ok(views.html.index.render("Your new application is super ready."+bla, 5))
  }
  
}