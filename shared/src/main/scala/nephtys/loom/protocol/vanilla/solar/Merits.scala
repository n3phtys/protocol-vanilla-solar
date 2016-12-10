package nephtys.loom.protocol.vanilla.solar

import nephtys.loom.protocol.vanilla.solar.Misc.Dots

import scala.scalajs.js.annotation.JSExportAll

/**
  * Created by nephtys on 12/10/16.
  */
@JSExportAll
object Merits {
  case class Category(string: String) extends AnyVal
  final case class Merit(name : String, rating : Dots, category : Option[Category])

  def emptyMeritsList : List[Merit] = List.empty
}
