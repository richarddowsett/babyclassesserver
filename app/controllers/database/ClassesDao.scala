package controllers.database

import javax.inject.{Inject, Singleton}

import model.BabyClass
import org.mongodb.scala.{Completed, Document, MongoCollection, Observer}
import org.mongodb.scala.bson.BsonDocument
import org.mongodb.scala.bson.collection.immutable.Document

import scala.concurrent.{Future, Promise}

trait ClassesDaoLike {
  def count():Future[Long]


  def loadAllClasses(): List[BabyClass]

  def addClass(babyClass: BabyClass): Future[String]

}

@Singleton
class ClassesDao @Inject()(collection: MongoCollection[BabyClass]) extends ClassesDaoLike {

  var list:List[BabyClass] = List.empty[BabyClass]

  override def loadAllClasses(): List[BabyClass] = {
    list
  }

  override def addClass(babyClass: BabyClass): Future[String] = {
    list = babyClass +: list
    Future.successful("Add new baby class")
  }

//  override def loadAllClasses(): List[BabyClass] = {
//    val observer = new Observer[BabyClass] {
//      var list = List.empty[BabyClass]
//      var complete = false
//
//      override def onError(e: Throwable): Unit = throw e
//
//      override def onComplete(): Unit = complete = true
//
//      override def onNext(result: BabyClass): Unit = {
//        list = result +: list
//      }
//    }
//    collection.find[BabyClass]().subscribe(observer)
//    while (!observer.complete) {
//      // change this to a future and a promise
//
//    }
//    observer.list
//  }
//
//  override def addClass(babyClass: BabyClass): Future[String] = {
//    println(s"starting to insert: $babyClass")
//    val promise = Promise[String]()
//    collection.insertOne(babyClass).subscribe((c: Completed) => {
//      promise.success("baby class inserted")
//    })
//    promise.future
//
//  }

  override def count(): Future[Long] = collection.count().head()
}
