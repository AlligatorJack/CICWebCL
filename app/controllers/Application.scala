package controllers

import play.api._
import play.api.mvc._
import cmd.parsing.partial.PartialCmdParser
import cmd.parsing.partial.PartialCmdLexer
import java.io.StringReader
import cmd.ast.{partial => AST}
import cmd.ast.Symbol
import play.api.templates.Html
import scala.xml._
import play.api.libs.iteratee.Enumerator

object Application extends Controller {
  
  def index = Action {
    Ok(views.html.test.render())
  }
  
//  def string(s: String)= Action {
//    val expr = new PartialCmdParser().parse(new PartialCmdLexer(new StringReader(s))).asInstanceOf[AST.Expr]
//    Ok(views.html.test.render(colorize(expr)))
//  }
  
  def stringAs(s: String) = Action {
    Ok(colorize(new PartialCmdParser().parse(new PartialCmdLexer(new StringReader(s))).asInstanceOf[AST.Expr]).toString)
  }
  
  def javascriptRoutes = Action { implicit request =>
    Ok(
      Routes.javascriptRouter("jsRoutes")(
          routes.javascript.Application.stringAs        
      )
    ).as("text/javascript") 
  }
  
  def colorize(s: Symbol): NodeSeq = s match {
    case AST.Ref(AST.Ident(name))             => Seq(<span class="vname">{name}</span>)
    case AST.CApp(AST.Ident(name), pl)        => Seq(<span class="cname">{name}</span>, Text("(")) ++ colorize(pl)
    case AST.ParamList(ap, np, isClosed)      => colorizeParams(ap, np) ++ (if(isClosed) Seq(Text(")")) else Seq())
    case AST.NParamWithEqual(AST.Ident(name)) => Seq(<span class="pname">{name}</span>, Text(" = "))
    case AST.NParamNoEqual(AST.Ident(name))   => Seq(<span class="pname">{name}</span>)
    case AST.NParam_(AST.Ident(name), v)      => Seq(<span class="pname">{name}</span>, Text(" = ")) ++ colorize(v)
    case AST.MissingElem()                    => Seq() 
    case AST.Integer(v)                       => Seq(<span class="integer">{v}</span>)
    case AST.Float(v)                         => Seq(<span class="float">{v}</span>)
    case AST.String(v)                        => Seq(<span class="string">{v}</span>)
    case AST.Bool(v)                          => Seq(<span class="boolean">{v}</span>)
    case AST.Seq(seq)                         => Seq(Text("[")) ++ splitSeq(seq, ",") ++ Seq(Text("]"))
    case AST.LocalValDefsNoIn(defs)           => Seq(<span class="keyword">let</span>, Text(" ")) ++ splitSeq(defs, ";")
    case AST.LocalValDefsWithIn(defs)         => colorize(AST.LocalValDefsNoIn(defs)) ++ Seq(Text(" "), <span class="keyword">in</span>)
    case AST.LocalValDefs(defs, expr)         => colorize(AST.LocalValDefsWithIn(defs)) ++ Seq(Text(" ")) ++ colorize(expr)   
    case AST.ValDefNoEqual(AST.Ident(name))   => Seq(<span class="vname">{name}</span>) 
    case AST.ValDefWithEqual(AST.Ident(name)) => Seq(<span class="vname">{name}</span>, Text(" = "))
    case AST.ValDef_(AST.Ident(name), expr)   => Seq(<span class="vname">{name}</span>, Text(" = ")) ++ colorize(expr)
  }
  
  def colorizeParams(ap: Seq[AST.Expr], np: Seq[AST.NParam]): NodeSeq = (ap, np) match {
    case (Seq(), Seq()) => Seq()
    case (Seq(), _)     => splitNParams(np)
    case (_, Seq())     => splitSeq(ap, ",") 
    case (_, _)         => splitSeq(ap, ",") ++ Seq(Text(", ")) ++ splitNParams(np)
  }
  
  def splitSeq(seq: Seq[Symbol], splitter: String): NodeSeq = seq match {
    case Seq() => Seq()
    case s::ss if ss.isEmpty => colorize(s)
    case s::ss => colorize(s) ++ Seq(Text(splitter + " ")) ++ splitSeq(ss, splitter)
  }
  
  def splitNParams(np: Seq[AST.NParam]): NodeSeq = np match {
    case Seq() => Seq()
    case n::np if np.isEmpty => colorize(n)
    case n::np => colorize(n) ++ Seq(Text(", ")) ++ splitNParams(np)
  }
}
