package controllers

import java.util.concurrent.TimeUnit
import javax.inject.{Named => _, _}

import com.google.inject.name.Named
import controllers.database.ClassesDaoLike
import model.BabyClass
import org.mongodb.scala.MongoCollection
import org.mongodb.scala.bson.collection.immutable.Document
import play.api.libs.functional.syntax._
import play.api.libs.json._
import play.api.mvc._

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.util.{Failure, Random, Success, Try}

/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */
@Singleton
class HomeController @Inject()(dao: ClassesDaoLike) extends Controller {
  implicit def OptionWrites[T](implicit fmt: Writes[T]): Writes[Option[T]] = new Writes[Option[T]] {
    def writes(o: Option[T]) = o match {
      case Some(value) => fmt.writes(value)
      case None => JsNull
    }
  }

  implicit val BabyClassWrites: Writes[BabyClass] = (
    (JsPath \ "_id").write[String] and
    (JsPath \ "category").write[String] and
      (JsPath \ "activity").write[String] and
      (JsPath \ "postcode").write[String]
    ) (unlift(BabyClass.unapply))

  implicit val BabyClassReads: Reads[BabyClass] = (
    (JsPath \ "_id").read[String] and
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
      Try(Await.result(dao.addClass(r), Duration(10, TimeUnit.SECONDS))) match {
        case Success(result) => println(result)
          Ok(Json.obj("success" -> true))
        case Failure(f) => println(f)
          BadRequest(Json.obj("success" -> false, "error" -> f.getMessage))
      }
    })
  }
  }

  def classesForLatLong(lat: String, long: String) = Action { implicit request => {
    val ret = Json.toJson(List(new BabyClass("123", lat, long, "CM1")))
    Ok(ret)
  }
  }

  def classes = Action { implicit request => {
    Try(Await.result(dao.count(), Duration(10, TimeUnit.SECONDS))) match {
      case Success(result) => Ok(Json.obj("success" -> true, "count" -> result))
      case Failure(f) => BadRequest(Json.obj("success" -> false))
    }
  }
  }
}
