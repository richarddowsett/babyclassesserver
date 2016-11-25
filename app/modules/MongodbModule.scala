package modules

import java.util.concurrent.TimeUnit

import com.google.inject.name.{Named, Names}
import com.google.inject.{AbstractModule, TypeLiteral}
import com.mongodb.client.model.CreateCollectionOptions
import controllers.database.{ClassesDao, ClassesDaoLike}
import model.BabyClass
import org.bson.codecs.configuration.CodecRegistries
import org.mongodb.scala.{Completed, MongoClient, MongoCollection, WriteConcern}
import play.api.Configuration
import play.api.Environment

import scalaz.{-\/, \/, \/-}

/**
  * Created by richarddowsett on 22/11/2016.
  */
class MongodbModule(environment: Environment,configuration: Configuration) extends AbstractModule {

  val hostConfig = configuration.getString("mongodb.host")
  val portConfig = configuration.getString("mongodb.port")
val babyClassCodec = new BabyClassCodec()
  val babyClassCodecRegistry = CodecRegistries.fromCodecs(babyClassCodec)



  override def configure(): Unit = {
    val mongoClient = for {
      host <- hostConfig.toDisjunction("Cannot find host config")
      port <- portConfig.toDisjunction("Cannot find port config")
    }
      yield {
        MongoClient(s"mongodb://$host:$port")
      }
    val collectionDisj = mongoClient.map({c => val db = c.getDatabase("classfinder")
      var created = false
//      db.createCollection("classes").subscribe((c:Completed) => {
//          println("collection created")
//        created = true
//      })
//      while(!created){
//
//      }
      db.getCollection[BabyClass]("classes")}).map(c =>{
    c.withCodecRegistry(babyClassCodecRegistry).withWriteConcern(WriteConcern.UNACKNOWLEDGED
      .withJournal(false).withWTimeout(30, TimeUnit.SECONDS))
  })

    collectionDisj.fold(l => addError(l),
      collection => bind(new TypeLiteral[MongoCollection[BabyClass]]{}).toInstance(collection))

    bind(classOf[ClassesDaoLike]).to(classOf[ClassesDao])
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


