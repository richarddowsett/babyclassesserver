package controllers

import javax.inject.{Named => _, _}

import com.google.inject.name.Named
import controllers.database.ClassesDaoLike
import model.BabyClass
import org.mongodb.scala.MongoCollection
import org.mongodb.scala.bson.collection.immutable.Document
import play.api.libs.functional.syntax._
import play.api.libs.json._
import play.api.mvc._

/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */
@Singleton
class HomeController @Inject()(dao: ClassesDaoLike) extends Controller {


  implicit val BabyClassWrites: Writes[BabyClass] = (
    (JsPath \ "category").write[String] and
      (JsPath \ "activity").write[String] and
      (JsPath \ "postcode").write[String]
    ) (unlift(BabyClass.unapply))

  implicit val BabyClassReads: Reads[BabyClass] = (
    (JsPath \ "category").read[String] and
      (JsPath \ "activity").read[String] and
      (JsPath \ "postcode").read[String]
    ).apply(BabyClass.apply _)

  /**
    * Create an Action to render an HTML page.
    *
    * The configuration in the `routes` file means that this method
    * will be called when the application receives a `GET` request with
    * a path of `/`.
    */
  def index = Action { implicit request => {
    val ret = Json.toJson(dao.loadAllClasses())
    Ok(ret)
  }
  }

  def addClass() = Action(BodyParsers.parse.json) { implicit request => {
    request.body.validate[BabyClass].fold(e => {
      println(s"found an error while parsing json: $e")
      BadRequest(Json.obj("error" -> "parsing error", "success" -> false))
    }, r => {
      dao.addClass(r)
      Ok(Json.obj("success" -> true))
    })
  }
  }

  def classesForLatLong(lat: String, long: String) = Action { implicit request => {
    val ret = Json.toJson(List(new BabyClass(lat, long, "CM1")))
    Ok(ret)
  }
  }

  def classes = Action { implicit request => {
    val ret = Json.toJson(List(new BabyClass("Baby", "Swimming", "CM1"), new BabyClass("Baby", "Massage", "CM1")))
    Ok(ret)
  }
  }
}
