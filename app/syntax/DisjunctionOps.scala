package syntax

import scalaz.{-\/, \/, \/-}


object DisjunctionOps {
  implicit class DisjunctionOps1[E <: Exception, T](disj: E \/ T) {

    def getOrThrow(): T = {
      disj match {
        case \/-(t) => t
        case -\/(e) => throw e
      }
    }

  }
}


