package controllers

import java.util.concurrent.TimeUnit

import akka.stream.scaladsl.Source
import modules.{MongoDbModuleTest, MongodbModule}
import org.scalatest.{Matchers, TestData}
import org.scalatestplus.play._
import play.api.Application
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.libs.json.{JsArray, JsObject, JsString}
import play.api.test._
import play.api.test.Helpers._

import scala.concurrent.Await
import scala.concurrent.duration.Duration

/**
  * Add your spec here.
  * You can mock out a whole application including requests, plugins etc.
  *
  * For more information, see https://www.playframework.com/documentation/latest/ScalaTestingWithScalaTest
  */
class HomeControllerSpec extends PlaySpec with OneAppPerTest {

  override def newAppForTest(testData: TestData): Application = new GuiceApplicationBuilder().build()

  "HomeController PUT" should {

    val sampleJson = JsObject(Seq("category"->JsString("Baby"), "activity"->JsString("yoga"), "postcode"->JsString("CM1")))

    "add a babyClass to the store" in {
      val controller = app.injector.instanceOf[HomeController]
      val response = controller.addClass().apply(FakeRequest().withBody(sampleJson))
      //      status(response.run()) mustBe OK
      val index = controller.index().apply(FakeRequest())
      status(index) mustBe OK
      val indexResult = Await.result(index, Duration(5, TimeUnit.SECONDS))
      contentAsJson(index) mustBe a[JsArray]
      val array = contentAsJson(index).asInstanceOf[JsArray]
      array.value must have size 1
    }

  }

  "HomeController GET" should {

    "render the index page from a new instance of controller" in {
      // val controller = new HomeController
      // val home = controller.index().apply(FakeRequest())

      //status(home) mustBe OK
      // contentType(home) mustBe Some("text/html")
      // contentAsString(home) must include ("Welcome to Play")
    }

    "render the index page from the application" in {
      val controller = app.injector.instanceOf[HomeController]
      val home = controller.index().apply(FakeRequest())

      status(home) mustBe OK
      contentType(home) mustBe Some("application/json")
    }


  }
}
