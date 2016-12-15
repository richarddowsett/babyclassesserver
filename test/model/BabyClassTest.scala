package model

import org.scalatest.{FeatureSpec, FlatSpec, Matchers}
import play.api.libs.json._

/**
  * Created by richarddowsett on 01/12/2016.
  */
class BabyClassTest extends FeatureSpec with Matchers {

  scenario("baby class should parse from json"){
    val json = JsObject(Seq("category" -> JsString("Baby"), "activity" -> JsString("Massage"), "postcode" -> JsString("CM1")))
    json.validate[BabyClass] shouldBe a[JsSuccess[_]]
  }

  scenario("parse json String"){
    val json = Json.parse("""{"category":"Baby","activity":"Ballet","address":{"house":"5 Oakland Gardens","town":"Hutton","city":"Brentwood","postcode":"CM13 1EN"}}""")
    json.validate[BabyClass] shouldBe a[JsSuccess[_]]
  }

}
