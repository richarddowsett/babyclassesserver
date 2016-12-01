package model

import play.api.libs.json.{JsString, Writes, _}
import syntax.TryOps._

import scala.util.{Failure, Success, Try}
import scalaz.{-\/, \/, \/-}

sealed trait Category {
  def category: String
}

object Category {

  val all = List(Pregnancy, Baby)

  def apply(category: String): Exception \/ Category = {
    all.find(_.category == category) match {
      case Some(c) => \/-(c)
      case None => -\/(new Exception(s"Cannot find category for value: ${category}"))
    }
  }

  object Pregnancy extends Category {
    override def category: String = "Pregnancy"
  }

  object Baby extends Category {
    override def category = "Baby"
  }

  implicit val reads: Reads[Category] = new Reads[Category] {
    override def reads(json: JsValue): JsResult[Category] = Try(json.asInstanceOf[JsString]).toDisj
      .flatMap(c => Category(c.toString().replaceAll("\"", "")))
      .fold(e => {
        println(s"Cannot read JsValue for $json from ${all.map(_.category)}")
        JsError(s"Cannot read jsonValue: $json")
      },
        c => JsSuccess(c))
  }

  implicit val writes: Writes[Category] = new Writes[Category] {
    override def writes(o: Category): JsValue = JsString(o.category)
  }

}
