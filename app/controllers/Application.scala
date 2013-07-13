package controllers

import play.api._
import play.api.mvc._
import java.io.File

object Application extends Controller {
  
  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def upload(name: String)  = Action(parse.multipartFormData) { request =>
    request.body.file("file").map { document =>
      document.ref.moveTo(new File(name),true)
      Ok("file uploaded")
    } getOrElse {
      InternalServerError("file not uploaded")
    }
  }
  
}