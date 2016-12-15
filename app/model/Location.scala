package model

import play.api.libs.functional.syntax._
import play.api.libs.json._

case class Location(lat: BigDecimal, lng: BigDecimal)

object Location {

  implicit val reads: Reads[Location] = ((JsPath \ "lat").read[BigDecimal] and
    (JsPath \ "lng").read[BigDecimal]).apply(Location.apply _)

  implicit val writes: Writes[Location] = ((JsPath \ "lat").write[BigDecimal] and
    (JsPath \ "lng").write[BigDecimal]) (unlift(Location.unapply))

}
