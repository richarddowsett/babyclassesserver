package model

import play.api.libs.functional.syntax._
import play.api.libs.json._

/**
  * Created by richarddowsett on 15/12/2016.
  */
case class Address(house: String, town: Option[String], city:String, postcode: String)

object Address{

  implicit val reads: Reads[Address] = ((JsPath \ "house").read[String] and
    (JsPath \ "town").readNullable[String] and
    (JsPath \ "city").read[String] and
    (JsPath \ "postcode").read[String]
    ).apply(Address.apply _)

  implicit val writes: Writes[Address] = ((JsPath \ "number").write[String] and
    (JsPath \ "town").writeNullable[String] and
    (JsPath \ "city").write[String] and
    (JsPath \ "postcode").write[String]
    )(unlift(Address.unapply))

}
