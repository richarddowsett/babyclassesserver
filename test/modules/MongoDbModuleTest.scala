package modules

import com.google.inject.AbstractModule
import controllers.database.ClassesDaoLike
import model.BabyClass

import scala.concurrent.Future

/**
  * Created by richarddowsett on 25/11/2016.
  */
class MongoDbModuleTest extends AbstractModule {
  override def configure(): Unit = {
bind(classOf[ClassesDaoLike]).toInstance( MockClassesDao)
  }
}

object MockClassesDao extends ClassesDaoLike {
  override def count(): Future[Long] = ???

  override def addClass(babyClass: BabyClass): Future[String] = ???

  override def loadAllClasses(): List[BabyClass] = ???
}
