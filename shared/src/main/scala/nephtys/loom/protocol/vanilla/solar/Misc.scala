package nephtys.loom.protocol.vanilla.solar

import scala.scalajs.js.annotation.JSExportAll

/**
  * Created by nephtys on 12/7/16.
  */
@JSExportAll
object Misc {
  final case class Name(name : String) extends AnyVal
  final case class Player(player : String) extends AnyVal
  final case class Concept(concept : String) extends AnyVal
  final case class Anima(anima : String) extends AnyVal

  final case class Dots(number : Byte) extends AnyVal
  final case class LimitTrigger(trigger : String) extends AnyVal

  final case class Essence(rating : Byte) extends AnyVal
  final case class WillpowerPoints(points : Byte) extends AnyVal
  final case class WillpowerDots(points : Byte) extends AnyVal


  sealed trait Caste
  val Castes = IndexedSeq(Dawn, Zenith, Twilight, Night, Eclipse)
  case object Dawn extends Caste
  case object Zenith extends Caste
  case object Twilight extends Caste
  case object Night extends Caste
  case object Eclipse extends Caste
}
