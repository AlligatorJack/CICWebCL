package controllers

import play.api._
import play.api.mvc._
import cmd.partial.PartialCmdParser

object Application extends Controller {
  
  def index(bla: String) = Action {
    Ok(views.html.index.render("Your new application is super ready."+bla, 5))
  }
  
}