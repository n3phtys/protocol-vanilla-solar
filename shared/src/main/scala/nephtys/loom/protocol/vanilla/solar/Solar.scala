package nephtys.loom.protocol.vanilla.solar
import nephtys.loom.protocol.shared.CharmRef
import nephtys.loom.protocol.vanilla.solar.Equipments.Equipment
import nephtys.loom.protocol.vanilla.solar.Merits.Merit
import nephtys.loom.protocol.vanilla.solar.Misc._
import org.nephtys.loom.generic.protocol.Aggregate
import org.nephtys.loom.generic.protocol.InternalStructures.{Email, ID, IDable}

import scala.scalajs.js.annotation.{JSExport, JSExportAll}
import scala.util.Try

/**
  * Created by nephtys on 12/7/16.
  */
@JSExport
@JSExportAll
final case class Solar(
                      owner : Email, //TODO: needs command/event
                      readers : Set[Email], //TODO: needs command/event
                      public : Boolean,
                      id : ID[Solar],
                      stillInCharGen  : Boolean,
                      bonusPointsUnspent : Int, //TODO: needs command/event

                      name : String,
                      concept : String, //TODO: needs command/event
                      player : String, //TODO: needs command/event
                      anima : String, //TODO: needs command/event
                      caste : Option[Caste], //TODO: needs command/event

                      attributes : Attributes.AttributeBlock, //TODO: needs command/event
                      abilities : Abilities.AbilityMatrix, //TODO: needs command/event
                      merits : List[Merit], //TODO: needs command/event

                      willpowerDots : Int,  //TODO: needs command/event
                      essenceCommitted : Int,  //TODO: needs command/event
                      limitTrigger: String,  //TODO: needs command/event

                      experience : Experiences.ExperienceBox, //TODO: needs command/event

                      equipment : List[Equipment],  //TODO: needs command/event

                      intimacies : Map[String, Intimacies.Intensity], //TODO: needs command/event

                      charms : List[CharmRef],  //TODO: needs command/event

                      notes : List[String]

                      )
  extends org.nephtys.loom.generic.protocol.Aggregate[Solar]
with CharmLearnable{

  //calculate based on spent experience
  def essence : Essence = {
    Experiences.spentXPtoEssenceLevel(experience.generalXP.spent)
  }



  override def abilityRating(abilityName: String): Option[Int] = abilities.getRatingForCharms(abilityName)

  override def attributeRating(attributeName: String): Option[Int] = attributes.getRating(attributeName)


  override def canLearn(ct: CharmType): Boolean = ct match {
    case SiderealMartialArts => true
    case MartialArts => true
    case EclipseCharm => caste.contains(Eclipse)
    case SolarCharm => true
    case Evocation => true
    case TerestrialSpell => canCastTerrestrialCircle
    case CelestialSpell => canCastCelestialCircle
    case SolarCircleSpell => canCastSolarCircle
    case SpiritCharm => false
  }

  private def canCastTerrestrialCircle : Boolean = true

  //todo: check if terrestrial circle charm was bought and essence >= 3 (yes, that one!)
  private def canCastCelestialCircle : Boolean = ???

  //todo: check if celestial circle charm was bought and essence >= 5 (yes, that one!)
  private def canCastSolarCircle : Boolean = ???

  override def has(charm: CharmRef): Boolean = charms.contains(charm)

  override def reducedCost(abilityName: String): Boolean = abilities.getTypeForAbility(abilityName).exists(a => a != Abilities.Normal)

  override def ignoreEssence(abilityName: String): Boolean = abilities.getTypeForAbility(abilityName).exists(a => a == Abilities.Supernal)
}


trait CharmLearnable extends Essencable with Abilitable with Attributable with Learnable

trait Essencable {
  def essence : Essence
}

trait Abilitable {
  def abilityRating(abilityName : String) : Option[Int]
  def reducedCost(abilityName : String) : Boolean
  def ignoreEssence(abilityName : String) : Boolean
}

trait Attributable {
  def attributeRating(attributeName : String) : Option[Int]
}

trait Learnable {
  def canLearn(ct : CharmType) : Boolean

  def has(charm : CharmRef) : Boolean
}

sealed trait CharmType
case object SiderealMartialArts extends CharmType
case object MartialArts extends CharmType
case object SpiritCharm extends CharmType
case object EclipseCharm extends CharmType
case object SolarCharm extends CharmType
case object Evocation extends CharmType
case object TerestrialSpell extends CharmType
case object CelestialSpell extends CharmType
case object SolarCircleSpell extends CharmType