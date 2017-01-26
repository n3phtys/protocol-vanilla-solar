package nephtys.loom.protocol

import org.scalatest._

/**
  * Created by Christopher on 26.01.2017.
  */
class SearchSpec extends FlatSpec with Matchers {

  "Binary Index Search" should "find a basic case" in {
    val element = 1
    val arr : Seq[Int] = Seq(0, 2, 3, 4)
    val shouldbe : Int = 1
    val is = Search.binaryIndexOrElse(arr, (a : (Int)) => a, element, 0)
    shouldbe should be (is)
  }

  it should "deal with ambiguity" in {
    val element = 2
    val arr : Seq[Int] = Seq(0, 2, 2, 2, 3, 3, 4)
    val shouldbe : Int = 4
    val is = Search.binaryIndexOrElse(arr, (a : (Int)) => a, element, 0)
    shouldbe should be (is)
  }
  it should "handle upper edge case" in {
    val element = 5
    val arr : Seq[Int] = Seq(0, 2, 3, 4)
    val shouldbe : Int = arr.length //elseValue only for lower edge
    assert(shouldbe == 4)
    val is = Search.binaryIndexOrElse(arr, (a : (Int)) => a, element, 0)
    shouldbe should be (is)
  }
  it should "handle lower edge case" in {
    val element = 0
    val arr : Seq[Int] = Seq(1, 2, 3, 4)
    val shouldbe : Int = 0
    val is = Search.binaryIndexOrElse(arr, (a : (Int)) => a, element, 0)
    shouldbe should be (is)
  }
  it should "work with more complex cases" in {
    val element : (Int, String) = ( 4, "This is an insertion")
    val map : Map[Int, String] = Map(1 -> "Hello", 3 -> "World", 5 -> "How are you", 2 -> "There")
    val arr : Seq[(Int, String)] = map.toSeq.sortBy(_._1)
    val shouldbe : Int = 3
    val is = Search.binaryIndexOrElse(arr, (a : (Int, String)) => a._1, element, 0)
    shouldbe should be (is)
  }


}
