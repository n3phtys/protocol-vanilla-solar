package nephtys.loom.protocol

import scala.scalajs.js
import scala.scalajs.js.annotation.JSExportAll

/**
  * Created by Christopher on 26.01.2017.
  */
@JSExportAll
object Search {


  /**
    * returns the index to insert a given element so the ordering is kept
    * if no element is smaller, None is returned
    */
  def binaryIndex[T](search : js.Array[T], toInt : T => Int, element : T) : Option[Int] =  {

    //TODO: this is broken and leads to an inf loop


    //assume: search is sorted

    //now find the right index to insert 'element' while keping the ordering

    //do this whole thing in binary search

    val elementInt : Int = toInt(element)

    //set one of those to reduce search space
    var min : Int = 0
    var max : Int = search.length - 1

    //return markers
    var notFound = true
    var foundIndex : Option[Int] = None

    //set within
    var lower : Int = -1
    def upper : Int = lower + 1
    def lowerElement : T = search(lower) //should be lower-equal
    def lowerInt : Int = toInt(lowerElement)
    def upperElement : Option[T] = if (upper < search.length) Some(search(upper)) else None //should be greater than or non exist
    def upperInt : Option[Int] = upperElement.map(t => toInt(t))

    while (notFound && min <= max) {
      lower = (min + max) / 2
      //first case: if lowerElement is lower equal and upperElement is greater or non existent, return this index
      if (lowerInt <= elementInt ) {
        if (upperInt.forall(i => i > elementInt)) {
          notFound = false
          foundIndex = Some(upper)
        } else {
          //second case: if both are lower equal, reduce search field (if it can be reduced)
          if (upper <= search.length) {
            min = upper
          } else {
            min = lower
          }
        }
      } else {
        if (lowerInt > elementInt) {
          //third case: if both are greater and lower exist, reduce search field
          max = lower
        } else {
          //if lower does not exist, return none
          notFound = false
          foundIndex = None
        }
      }
    }
    foundIndex
  }

  def binaryIndexOrElse[T](search : js.Array[T], toInt : T => Int, element: T, elseValue : Int) : Int = {
    //val cmp : (T, T) => Int = (a, b) => toInt(a).compareTo(toInt(b))
    binaryIndex(search, toInt, element).getOrElse(elseValue)
  }
}