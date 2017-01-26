package nephtys.loom.protocol

/**
  * Created by Christopher on 26.01.2017.
  */
object Search {


  def binaryIndex[T](arr : Int => T, length : Int, toInt : T => Int, element : T) : Option[Int] = {

    val elementInt : Int = toInt(element)

    //set one of those to reduce search space
    var min : Int = 0
    var max : Int = length - 1

    //return markers
    var notFound = true
    var foundIndex : Option[Int] = None

    //set within
    var lower : Int = -1
    def upper : Int = lower + 1
    def lowerElement : T = arr(lower) //should be lower-equal
    def lowerInt : Int = toInt(lowerElement)
    def upperElement : Option[T] = if (upper < length) Some(arr(upper)) else None //should be greater than or non exist
    def upperInt : Option[Int] = upperElement.map(t => toInt(t))

    while (notFound && min <= max) {
      println(s"Looping with min/max = [$min : $max]")
      lower = (min + max) / 2
      //first case: if lowerElement is lower equal and upperElement is greater or non existent, return this index
      if (lowerInt <= elementInt  && min != max) {
        if (upperInt.forall(i => i > elementInt)) {
          notFound = false
          foundIndex = Some(upper)
        } else {
          //second case: if both are lower equal, reduce search field (if it can be reduced)
          if (upper < length) {
            min = upper
            println("upper below length")
          } else {
            min = lower
            println("upper not below length")
          }
        }
      } else {
        if (lowerInt > elementInt && min != max) {
          //third case: if both are greater and lower exist, reduce search field
          max = lower
          println("lower larger than element")
        } else {
          //if lower does not exist, return none
          notFound = false
          foundIndex = None
        }
      }
    }
    foundIndex

  }


  def binaryIndexOrElse[T](search : Seq[T], toInt : T => Int, element: T, elseValue : Int) : Int = {
    binaryIndex((i : Int) => search.apply(i), search.length, toInt, element).getOrElse(elseValue)
  }
}
