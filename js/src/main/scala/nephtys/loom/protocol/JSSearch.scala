package nephtys.loom.protocol

import scala.scalajs.js
import scala.scalajs.js.annotation.JSExportAll

/**
  * Created by Christopher on 26.01.2017.
  */
@JSExportAll
object JSSearch {
  def binaryIndexOrElse[T](search : js.Array[T], toInt : T => Int, element: T, elseValue : Int) : Int = {
    Search.binaryIndex((i : Int) => search.apply(i), search.length, toInt, element).getOrElse(elseValue)
  }
}