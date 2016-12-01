package syntax

import scala.util.{Failure, Success, Try}
import scalaz.{-\/, \/-}


object TryOps {

  implicit class TryOps1[T](t: Try[T]) {

    def toDisj = {
      t match {
        case Success(t) => \/-(t)
        case failure @ Failure(f) => println(s"wanted a success, but found: $failure")
          -\/(f)
      }
    }

  }

}
