package nephtys.loom.protocol.shared

import scala.scalajs.js.annotation.JSExportAll

/**
  * Created by Christopher on 05.01.2017.
  */
@JSExportAll
object Charms {
  sealed trait PowerRef

  sealed trait Power
  sealed trait Charmlike extends Power
  sealed trait Charm extends Charmlike
  sealed trait SolarCharm extends Charm
  sealed trait Evocation extends Charm
  sealed trait MartialArts extends Charm
  sealed trait SpiritCharm extends Charm
  sealed trait Spelllike extends Power
  sealed trait Spell extends Spelllike

  final case class CustomSpell() extends Spell
  final case class PurchasedSpell(ref : SpellRef) extends Spelllike with PowerRef
  sealed trait ListedSpell extends Spell

  final case class CustomCharm() extends Charm
  final case class PurchasedCharm(ref : CharmRef) extends Charmlike with PowerRef
  final case class ListedSolarCharm(name : String, cost : String, typ : String, keywords : Set[String], duration : String, description : String, page : String, ability : String, abilityRating : Int, essenceRating : Int ) extends SolarCharm
  final case class ListedMartialArtsCharm() extends MartialArts
  final case class ListedEvocationCharm() extends Evocation

}
