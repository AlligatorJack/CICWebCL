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
import scala.xml._
import play.api.libs.iteratee.Enumerator
import scala.util.regexp.SyntaxError

object Application extends Controller {
  
  def index = Action {
    Ok(views.html.test.render())
  }
  
  def colorizeString(s: String)= Action {
    val lexer = new PartialCmdLexer(new StringReader(s))
    try {
      val expr = new PartialCmdParser().parse(lexer).asInstanceOf[Expr]
      Ok(colorizeExpr(expr).toString)
    }
    catch {
      //there is no exception if beaver can repair the user-string. We need to override Events.syntaxError maybe(just throw an Exception)
      case e:Exception => Ok(s)
    }
  }
  
  def javascriptRoutes = Action { implicit request =>
    Ok(
      Routes.javascriptRouter("jsRoutes")(
          routes.javascript.Application.colorizeString       
      )
    ).as("text/javascript") 
  }
  
  def colorizeExpr(s: AST.Symbol): NodeSeq = s match {
    case AST.Ref(AST.Ident(name))              => Seq(<span class="vname">{name}</span>)
    case PAST.CApp(AST.Ident(name), pl)        => Seq(<span class="cname">{name}</span>, Text("(")) ++ colorizeExpr(pl)
    case PAST.ParamList(ap, np, isClosed)      => colorizeParams(ap, np) ++ (if(isClosed) Seq(Text(")")) else Seq())
    case PAST.NParamWithEqual(AST.Ident(name)) => Seq(<span class="pname">{name}</span>, Text(" = "))
    case PAST.NParamNoEqual(AST.Ident(name))   => Seq(<span class="pname">{name}</span>)
    case PAST.NParam_(AST.Ident(name), v)      => Seq(<span class="pname">{name}</span>, Text(" = ")) ++ colorizeExpr(v)
    case PAST.MissingElem()                    => Seq() 
    case AST.Integer(v)                        => Seq(<span class="integer">{v}</span>)
    case AST.Float(v)                          => Seq(<span class="float">{v}</span>)
    case AST.String(v)                         => Seq(<span class="string">{v}</span>)
    case AST.Bool(v)                           => Seq(<span class="boolean">{v}</span>)
    case AST.Seq(seq)                          => Seq(Text("[")) ++ splitSeq(seq, ",") ++ Seq(Text("]"))
    case PAST.LocalValDefsNoIn(defs)           => Seq(<span class="keyword">let</span>, Text(" ")) ++ splitSeq(defs, ";")
    case PAST.LocalValDefsWithIn(defs)         => colorizeExpr(PAST.LocalValDefsNoIn(defs)) ++ Seq(Text(" "), <span class="keyword">in</span>)
    case PAST.LocalValDefs(defs, expr)         => colorizeExpr(PAST.LocalValDefsWithIn(defs)) ++ Seq(Text(" ")) ++ colorizeExpr(expr)   
    case PAST.ValDefNoEqual(AST.Ident(name))   => Seq(<span class="vname">{name}</span>) 
    case PAST.ValDefWithEqual(AST.Ident(name)) => Seq(<span class="vname">{name}</span>, Text(" = "))
    case PAST.ValDef_(AST.Ident(name), expr)   => Seq(<span class="vname">{name}</span>, Text(" = ")) ++ colorizeExpr(expr)
  }
  
  def colorizeParams(ap: Seq[AST.Expr], np: Seq[PAST.NParam]): NodeSeq = (ap, np) match {
    case (Seq(), Seq()) => Seq()
    case (Seq(), _)     => splitNParams(np)
    case (_, Seq())     => splitSeq(ap, ",") 
    case (_, _)         => splitSeq(ap, ",") ++ Seq(Text(", ")) ++ splitNParams(np)
  }
  
  def splitSeq(seq: Seq[AST.Symbol], splitter: String): NodeSeq = seq match {
    case Seq() => Seq()
    case s::ss if ss.isEmpty => colorizeExpr(s)
    case s::ss => colorizeExpr(s) ++ Seq(Text(splitter + " ")) ++ splitSeq(ss, splitter)
  }
  
  def splitNParams(np: Seq[PAST.NParam]): NodeSeq = np match {
    case Seq() => Seq()
    case n::np if np.isEmpty => colorizeExpr(n)
    case n::np => colorizeExpr(n) ++ Seq(Text(", ")) ++ splitNParams(np)
  }
  
}