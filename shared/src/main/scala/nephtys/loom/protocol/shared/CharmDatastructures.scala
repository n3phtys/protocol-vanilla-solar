package nephtys.loom.protocol.shared

/**
  * Created by Christopher on 12.01.2017.
  */
object CharmDatastructures {

  sealed abstract class EssenceDots(val dots : Int)
  case object Essence1 extends EssenceDots(1)
  case object Essence2 extends EssenceDots(2)
  case object Essence3 extends EssenceDots(3)
  case object Essence4 extends EssenceDots(4)
  case object Essence5 extends EssenceDots(5)
  def Essence(i : Int) : EssenceDots = i match {
    case 5 => Essence5
    case 4 => Essence4
    case 3 => Essence3
    case 2 => Essence2
    case _ => Essence1
  }


 final case class Ability(ability: String, dots : Int)

  sealed trait CharmType
  case object Supplemental extends CharmType
  case object Simple extends CharmType
  case object Permanent extends CharmType
  case object Reflexive extends CharmType
  case object Special extends CharmType

}
