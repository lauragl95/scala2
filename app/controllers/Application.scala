package controllers

import play.api._
import play.api.mvc._
import play.api.data.Form
import play.api.data.Forms._
import models.{Person,DB}
import play.api.libs.json.Json

class Application extends Controller {

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

    val formPerson: Form[Person] = Form {
      mapping (
        "name" -> text,
        "age" -> number
     )(Person.apply)(Person.unapply)
    }

   def addPerson = Action { implicit request =>
    val persons = formPerson.bindFromRequest.get
    DB.save(persons)
    Redirect(routes.Application.index)
}  

def getPersons = Action {
      val persons = DB.query[Person].fetch
      Ok(Json.toJson(persons))
}
    
}
