package controllers.database

import javax.inject.{Inject, Singleton}

import model.BabyClass
import org.mongodb.scala.{Document, MongoCollection, Observer}
import org.mongodb.scala.bson.BsonDocument
import org.mongodb.scala.bson.collection.immutable.Document

trait ClassesDaoLike {

  def loadAllClasses(): List[BabyClass]

  def addClass(babyClass: BabyClass): Unit

}

@Singleton
class ClassesDao @Inject()(collection: MongoCollection[BabyClass]) extends ClassesDaoLike {
  override def loadAllClasses(): List[BabyClass] = {
    val observer = new Observer[BabyClass] {
      var list = List.empty[BabyClass]
      var complete = false

      override def onError(e: Throwable): Unit = throw e

      override def onComplete(): Unit = complete = true

      override def onNext(result: BabyClass): Unit = {
        list = result +: list
      }
    }
    collection.find[BabyClass]().subscribe(observer)
    while (!observer.complete) { // change this to a future and a promise

    }
    observer.list
  }

  override def addClass(babyClass: BabyClass): Unit = ???
}
