package model

import play.api.libs.functional.syntax._
import play.api.libs.json._
import play.api.libs.json.JsError

import scalaz.{-\/, \/, \/-}

/**
  * Created by richarddowsett on 23/11/2016.
  */
case class BabyClass(_id: String, category: Category, activity: String, postcode: String) {
}

object BabyClass {
  def applyJson(category: Category, activity: String, postcode: String) = {
    new BabyClass("unappled", category, activity, postcode)
  }

  implicit val BabyClassWrites: Writes[BabyClass] = (
    (JsPath \ "_id").write[String] and
      (JsPath \ "category").write[Category] and
      (JsPath \ "activity").write[String] and
      (JsPath \ "postcode").write[String]
    ) (unlift(BabyClass.unapply))

  implicit val BabyClassReads: Reads[BabyClass] = (
    (JsPath \ "category").read[Category] and
      (JsPath \ "activity").read[String] and
      (JsPath \ "postcode").read[String]
    ).apply(BabyClass.applyJson _)
}



