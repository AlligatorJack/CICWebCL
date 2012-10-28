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
import cmd.parsing.SymbolSeq

object Application extends Controller {
  
  def index = Action {
    Ok(views.html.WebCLI.render())
  }
  
  def test = Action {
    Ok(views.html.test.render())
  }

  def colorizeString(s: String)= Action {
    //version with lexer
    try {
      val expr = new PartialCmdParser().parse(new PartialCmdLexer(new StringReader(s))).asInstanceOf[AST.Expr]
      Ok(colorizeExpr(expr).toString)
    }
    catch {
      //there is no exception if beaver can repair the user-string. We need to override Events.syntaxError maybe(just throw an Exception)
      case e:Exception => Ok(colorizeLexer(new PartialCmdLexer(new StringReader(s))).toString)
    }
    
//    //version with SymbolSeq
//    try {
//      val expr = new PartialCmdParser().parse(new PartialCmdLexer(new StringReader(s))).asInstanceOf[AST.Expr]
//      Ok(colorizeExpr(expr).toString)
//    }
//    catch {
//      //there is no exception if beaver can repair the user-string. We need to override Events.syntaxError maybe(just throw an Exception)
//      case e:Exception => Ok(colorizeTokens(new SymbolSeq(new PartialCmdLexer(new StringReader(s)))).toString)
//    }
    
//    //test for colorizeTokens
//    Ok(colorizeTokens(new SymbolSeq(new PartialCmdLexer(new StringReader(s)))).toString)
    
//    //test for colorizeLexer
//    Ok(colorizeLexer(new PartialCmdLexer(new StringReader(s))).toString)
  }
  
  def javascriptRoutes = Action { implicit request =>
    Ok(
      Routes.javascriptRouter("jsRoutes")(
          routes.javascript.Application.colorizeString       
      )
    ).as("text/javascript") 
  }
  
  def colorizeLexer(lexer: PartialCmdLexer): NodeSeq = {
    val token = lexer.nextToken()
    token.getId() match {
      case 0  => Seq()
      case 1  => Seq(Text(", ")) ++ colorizeLexer(lexer)
      case 2  => Seq(Text("] ")) ++ colorizeLexer(lexer)
      case 3  => Seq(Text(") ")) ++ colorizeLexer(lexer)
      case 4  => Seq(<span contenteditable="false" class="keyword">in </span>) ++ colorizeLexer(lexer)
      case 5  => Seq(Text("; ")) ++ colorizeLexer(lexer)
      case 6  => Seq(<span contenteditable="false" class="name">{token.value} </span>) ++ colorizeLexer(lexer)
      case 7  => Seq(Text("(")) ++ colorizeLexer(lexer)
      case 8  => Seq(<span contenteditable="false" class="keyword">let </span>) ++ colorizeLexer(lexer)
      case 9  => Seq(Text("[")) ++ colorizeLexer(lexer)
      case 10 => Seq(<span contenteditable="false" class="integer">{token.value} </span>) ++ colorizeLexer(lexer)
      case 11 => Seq(<span contenteditable="false" class="float">{token.value} </span>) ++ colorizeLexer(lexer)
      case 12 => Seq(<span contenteditable="false" class="string">"{token.value}" </span>) ++ colorizeLexer(lexer)
      case 13 => Seq(<span contenteditable="false" class="boolean">{token.value} </span>) ++ colorizeLexer(lexer)
      case 14 => Seq(<span contenteditable="false" class="boolean">{token.value} </span>) ++ colorizeLexer(lexer)
      case 15 => Seq(Text(" = ")) ++ colorizeLexer(lexer)
    }
  }
  
  //something is wrong with SymbolSeq
  def colorizeTokens(tokenSeq: SymbolSeq): NodeSeq = {
    tokenSeq.map(x => <span class="keyword">{x.value}</span>)
//    tokenSeq.flatMap(x => x.getId() match {
//      case 0  => Seq()
//      case 1  => Seq(Text(","))
//      case 2  => Seq(Text("]"))
//      case 3  => Seq(Text(")"))
//      case 4  => Seq(<span class="keyword">in</span>)
//      case 5  => Seq(Text(";"))
//      case 6  => Seq(<span class="name">{x.value}</span>)
//      case 7  => Seq(Text("("))
//      case 8  => Seq(<span class="keyword">let</span>)
//      case 9  => Seq(Text("["))
//      case 10 => Seq(<span class="integer">{x.value}</span>) 
//      case 11 => Seq(<span class="float">{x.value}</span>)
//      case 12 => Seq(<span class="string">{x.value}</span>)
//      case 13 => Seq(<span class="boolean">{x.value}</span>)
//      case 14 => Seq(<span class="boolean">{x.value}</span>)
//      case 15 => Seq(Text("="))
//    })
  }
  
  def colorizeExpr(s: Symbol): NodeSeq = s match {
    case AST.Ref(AST.Ident(name))             => Seq(<span class="vname">{name}</span>)
    case AST.CApp(AST.Ident(name), pl)        => Seq(<span class="cname">{name}</span>, Text("(")) ++ colorizeExpr(pl)
    case AST.ParamList(ap, np, isClosed)      => colorizeParams(ap, np) ++ (if(isClosed) Seq(Text(")")) else Seq())
    case AST.NParamWithEqual(AST.Ident(name)) => Seq(<span class="pname">{name}</span>, Text(" = "))
    case AST.NParamNoEqual(AST.Ident(name))   => Seq(<span class="pname">{name}</span>)
    case AST.NParam_(AST.Ident(name), v)      => Seq(<span class="pname">{name}</span>, Text(" = ")) ++ colorizeExpr(v)
    case AST.MissingElem()                    => Seq() 
    case AST.Integer(v)                       => Seq(<span class="integer">{v}</span>)
    case AST.Float(v)                         => Seq(<span class="float">{v}</span>)
    case AST.String(v)                        => Seq(<span class="string">"{v}"</span>)
    case AST.Bool(v)                          => Seq(<span class="boolean">{v}</span>)
    case AST.Seq(seq)                         => Seq(Text("[")) ++ splitSeq(seq, ",") ++ Seq(Text("]"))
    case AST.LocalValDefsNoIn(defs)           => Seq(<span class="keyword">let</span>, Text(" ")) ++ splitSeq(defs, ";")
    case AST.LocalValDefsWithIn(defs)         => colorizeExpr(AST.LocalValDefsNoIn(defs)) ++ Seq(Text(" "), <span class="keyword">in</span>)
    case AST.LocalValDefs(defs, expr)         => colorizeExpr(AST.LocalValDefsWithIn(defs)) ++ Seq(Text(" ")) ++ colorizeExpr(expr)   
    case AST.ValDefNoEqual(AST.Ident(name))   => Seq(<span class="vname">{name}</span>) 
    case AST.ValDefWithEqual(AST.Ident(name)) => Seq(<span class="vname">{name}</span>, Text(" = "))
    case AST.ValDef_(AST.Ident(name), expr)   => Seq(<span class="vname">{name}</span>, Text(" = ")) ++ colorizeExpr(expr)
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
}
