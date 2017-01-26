package nephtys.loom.protocol

import org.scalatest._

import scala.scalajs.js
import scala.scalajs.js.JSConverters._


/**
  * Created by Christopher on 26.01.2017.
  */
class SearchSpec extends FunSpec {

  describe("BinarySearch") {
    it("should find a basic case") {
      val element = 1
      val arr : js.Array[Int] = Seq(0, 2, 3, 4).toJSArray
      val shouldbe : Int = 1
      val is = Search.binaryIndexOrElse(arr, (a : (Int)) => a, element, 0)
      assert(shouldbe == is)
    }
    it("should deal with ambiguity") {
      val element = 2
      val arr : js.Array[Int] = Seq(0, 2, 2, 2, 3, 3, 4).toJSArray
      val shouldbe : Int = 4
      val is = Search.binaryIndexOrElse(arr, (a : (Int)) => a, element, 0)
      assert(shouldbe == is)
    }
    it("should handle upper edge case") {
      val element = 5
      val arr : js.Array[Int] = Seq(0, 2, 3, 4).toJSArray
      val shouldbe : Int = 1 //elseValue only for below
      val is = Search.binaryIndexOrElse(arr, (a : (Int)) => a, element, 0)
      assert(shouldbe == is)
    }
    it("should handle lower edge case") {
      val element = 0
      val arr : js.Array[Int] = Seq(1, 2, 3, 4).toJSArray
      val shouldbe : Int = 0
      val is = Search.binaryIndexOrElse(arr, (a : (Int)) => a, element, 0)
      assert(shouldbe == is)
    }
    it("should work with more complex cases") {
      val element : (Int, String) = ( 4, "This is an insertion")
      val map : Map[Int, String] = Map(1 -> "Hello", 3 -> "World", 5 -> "How are you", 2 -> "There")
      val arr : js.Array[(Int, String)] = map.toSeq.sortBy(_._1).toJSArray
      val shouldbe : Int = 3
      val is = Search.binaryIndexOrElse(arr, (a : (Int, String)) => a._1, element, 0)
      assert(shouldbe == is)
    }
  }
}
