package model

import play.api.libs.functional.syntax._
import play.api.libs.json._
import play.api.libs.json.JsError

import scalaz.{-\/, \/, \/-}

/**
  * Created by richarddowsett on 23/11/2016.
  */
case class BabyClass(_id: String, category: Category, activity: String, address: Address,
                     location: Location) {
}

object BabyClass {
  def applyJson(category: Category, activity: String, address: Address, location: Location) = {
    new BabyClass("unapplied", category, activity, address, location)
  }

  implicit val BabyClassWrites: Writes[BabyClass] = (
    (JsPath \ "_id").write[String] and
      (JsPath \ "category").write[Category] and
      (JsPath \ "activity").write[String] and
      (JsPath \ "address").write[Address] and
      (JsPath \ "location").write[Location]
    ) (unlift(BabyClass.unapply))

  implicit val BabyClassReads: Reads[BabyClass] = (
    (JsPath \ "category").read[Category] and
      (JsPath \ "activity").read[String] and
      (JsPath \ "address").read[Address] and
      (JsPath \ "location").read[Location]
    ).apply(BabyClass.applyJson _)
}



