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
import scala.util.regexp.SyntaxError

object Application extends Controller {
  
  def index = Action {
    // Ok(views.html.test.render())
    Ok (views.html.WebCLI.render())
    
  }

  def colorizeString(s: String)= Action {
    val lexer = new PartialCmdLexer(new StringReader(s))
    try {
      val expr = new PartialCmdParser().parse(lexer).asInstanceOf[AST.Expr]
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
  
  def colorizeExpr(s: Symbol): NodeSeq = s match {
    case AST.Ref(AST.Ident(name))             => Seq(<span contenteditable="false" class="vname">{name}</span>)
    case AST.CApp(AST.Ident(name), pl)        => Seq(<span contenteditable="false" class="cname">{name}</span>, Text("(")) ++ colorizeExpr(pl)
    case AST.ParamList(ap, np, isClosed)      => colorizeParams(ap, np) ++ (if(isClosed) Seq(Text(")")) else Seq())
    case AST.NParamWithEqual(AST.Ident(name)) => Seq(<span contenteditable="false" class="pname">{name}</span>, Text(" = "))
    case AST.NParamNoEqual(AST.Ident(name))   => Seq(<span contenteditable="false" class="pname">{name}</span>)
    case AST.NParam_(AST.Ident(name), v)      => Seq(<span contenteditable="false" class="pname">{name}</span>, Text(" = ")) ++ colorizeExpr(v)
    case AST.MissingElem()                    => Seq() 
    case AST.Integer(v)                       => Seq(<span contenteditable="false" class="integer">{v}</span>)
    case AST.Float(v)                         => Seq(<span contenteditable="false" class="float">{v}</span>)
    case AST.String(v)                        => Seq(<span contenteditable="false" class="string">{v}</span>)
    case AST.Bool(v)                          => Seq(<span contenteditable="false" class="boolean">{v}</span>)
    case AST.Seq(seq)                         => Seq(Text("[")) ++ splitSeq(seq, ",") ++ Seq(Text("]"))
    case AST.LocalValDefsNoIn(defs)           => Seq(<span contenteditable="false" class="keyword">let</span>, Text(" ")) ++ splitSeq(defs, ";")
    case AST.LocalValDefsWithIn(defs)         => colorizeExpr(AST.LocalValDefsNoIn(defs)) ++ Seq(Text(" "), <span contenteditable="false" class="keyword">in</span>)
    case AST.LocalValDefs(defs, expr)         => colorizeExpr(AST.LocalValDefsWithIn(defs)) ++ Seq(Text(" ")) ++ colorizeExpr(expr)   
    case AST.ValDefNoEqual(AST.Ident(name))   => Seq(<span contenteditable="false" class="vname">{name}</span>) 
    case AST.ValDefWithEqual(AST.Ident(name)) => Seq(<span contenteditable="false" class="vname">{name}</span>, Text(" = "))
    case AST.ValDef_(AST.Ident(name), expr)   => Seq(<span contenteditable="false" class="vname">{name}</span>, Text(" = ")) ++ colorizeExpr(expr)
  }
  
  def colorizeParams(ap: Seq[AST.Expr], np: Seq[AST.NParam]): NodeSeq = (ap, np) match {
    case (Seq(), Seq()) => Seq()
    case (Seq(), _)     => splitNParams(np)
    case (_, Seq())     => splitSeq(ap, ",") 
    case (_, _)         => splitSeq(ap, ",") ++ Seq(Text(", ")) ++ splitNParams(np)
  }
  
  def splitSeq(seq: Seq[Symbol], splitter: String): NodeSeq = seq match {
    case Seq() => Seq()
    case s::ss if ss.isEmpty => colorizeExpr(s)
    case s::ss => colorizeExpr(s) ++ Seq(Text(splitter + " ")) ++ splitSeq(ss, splitter)
  }
  
  def splitNParams(np: Seq[AST.NParam]): NodeSeq = np match {
    case Seq() => Seq()
    case n::np if np.isEmpty => colorizeExpr(n)
    case n::np => colorizeExpr(n) ++ Seq(Text(", ")) ++ splitNParams(np)
  }

  def getCompletions(str: String){
    List("add", "analyze", "apply", "cancel", "copy", "cut", "maximize", "make", "minimize", "move", "paste", "save", "send")
  }
}
