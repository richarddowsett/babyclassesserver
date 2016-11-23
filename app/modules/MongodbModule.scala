package modules

import com.google.inject.name.{Named, Names}
import com.google.inject.{AbstractModule, TypeLiteral}
import org.mongodb.scala.bson.collection.immutable.Document
import org.mongodb.scala.{MongoClient, MongoCollection}
import play.api.Configuration
import play.api.Environment

import scalaz.{-\/, \/, \/-}

/**
  * Created by richarddowsett on 22/11/2016.
  */
class MongodbModule(environment: Environment,configuration: Configuration) extends AbstractModule {

  val hostConfig = configuration.getString("mongodb.host")
  val portConfig = configuration.getString("mongodb.port")


  override def configure(): Unit = {
    val mongoClient = for {
      host <- hostConfig.toDisjunction("Cannot find host config")
      port <- portConfig.toDisjunction("Cannot find port config")
    }
      yield {
        MongoClient(s"mongodb://$host:$port")
      }

    val collectionDisj = mongoClient.map(c => c.getDatabase("classfinder").getCollection("classes"))

    collectionDisj.fold(l => addError(l),
      collection => bind(new TypeLiteral[MongoCollection[Document]]{}).toInstance(collection))
  }

  implicit class Disjunc[T](opt: Option[T]) {
    def toDisjunction(e: String): String \/ T = {
      opt match {
        case Some(t) => \/-(t)
        case None => -\/(e)
      }
    }
  }

}


