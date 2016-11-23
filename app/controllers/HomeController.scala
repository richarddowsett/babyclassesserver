package controllers

import javax.inject.{Named => _, _}

import com.google.inject.name.Named
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
class HomeController @Inject() (collection: MongoCollection[Document]) extends Controller {

  case class BabyClass(category: String, activity: String, postcode: String)

  implicit val BabyClassWrites: Writes[BabyClass] = (
    (JsPath \ "category").write[String] and
      (JsPath \ "activity").write[String] and
      (JsPath \ "postcode").write[String]
    )(unlift(BabyClass.unapply))

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index = Action { implicit request => {
    val ret = Json.toJson(new BabyClass("Baby", "Swimming", "CM1"))
    Ok(ret)
  }
  }

  def classesForLatLong(lat:String, long:String) = Action {implicit request => {
    val ret = Json.toJson(List(new BabyClass(lat, long, "CM1")))
    Ok(ret)
  }}

  def classes = Action { implicit request => {
    val ret = Json.toJson(List(new BabyClass("Baby", "Swimming", "CM1"),new BabyClass("Baby", "Massage", "CM1")) )
    Ok(ret)
  }
  }
}
