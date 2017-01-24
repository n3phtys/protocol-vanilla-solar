package nephtys.loom.protocol.vanilla.solar
import nephtys.loom.protocol.shared.CustomPowers.FreePointCost
import nephtys.loom.protocol.shared._
import nephtys.loom.protocol.vanilla.solar.Abilities.Type
import nephtys.loom.protocol.vanilla.solar.Equipments.Equipment
import nephtys.loom.protocol.vanilla.solar.Merits.Merit
import nephtys.loom.protocol.vanilla.solar.Misc._
import org.nephtys.loom.generic.protocol.Aggregate
import org.nephtys.loom.generic.protocol.InternalStructures.{Email, ID, IDable, MetaInfo}

import scala.scalajs.js.annotation.{JSExport, JSExportAll}
import scala.util.Try

@JSExport
@JSExportAll
final case class NamedSolarMetaDescriptors(name : String,
                                           concept : String,
                                           player : String,
                                           anima : String,
                                           limitTrigger: String)


/**
  * Created by nephtys on 12/7/16.
  */
@JSExport
@JSExportAll
final case class Solar(
                      metaInfo : MetaInfo,
                      id : ID[Solar],
                      stillInCharGen  : Boolean,
                      bonusPointsUnspent : Int,

                      metaDescriptors: NamedSolarMetaDescriptors,

                      caste : Option[Caste],

                      attributes : Attributes.AttributeBlock,
                      abilities : Abilities.AbilityMatrix,
                      merits : List[Merit],

                      willpowerDots : Int,
                      essenceCommitted : Int,

                      experience : Experiences.ExperienceBox,

                      equipment : List[Equipment],

                      intimacies : Map[String, Intimacies.Intensity],

                      listedPowers : Set[Int],
                      customCharms : Seq[CustomPowers.CustomPower],


                      notes : List[String]

                      )
  extends org.nephtys.loom.generic.protocol.Aggregate[Solar]
with CharmLearnable{

  //calculate based on spent experience
  def essence : Essence = {
    Experiences.spentXPtoEssenceLevel(experience.generalXP.spent)
  }


  def countCharmPurchases: Int = listedPowers.size + customCharms.map(a => a.customCost match {
    case FreePointCost(i) => i
    case _ => 0
  }).sum


  override def abilityRating(abilityName: String): Option[Int] = abilities.getRatingForCharms(abilityName)

  override def attributeRating(attributeName: String): Option[Int] = attributes.getRating(attributeName)


  override def canLearn(ct: CharmType): Boolean = ct match {
    case SiderealMartialArtsType => true
    case MartialArtsType => true
    case EclipseCharmType => caste.contains(Eclipse)
    case SolarCharmType => true
    case EvocationType => true
    case TerestrialSpellType => canCastTerrestrialCircle
    case CelestialSpellType => canCastCelestialCircle
    case SolarCircleSpellType => canCastSolarCircle
    case SpiritCharmType => false
  }

  private def canCastTerrestrialCircle : Boolean = true

  //todo: check if terrestrial circle charm was bought and essence >= 3 (yes, that one!)
  private def canCastCelestialCircle : Boolean = true

  //todo: check if celestial circle charm was bought and essence >= 5 (yes, that one!)
  private def canCastSolarCircle : Boolean = true

  override def has(charm: Power with Product with Serializable): Boolean = listedPowers.contains(Powers.powersIndexMap.getOrElse(charm, -1))

  override def reducedCost(abilityName: String): Boolean = abilities.getTypeForAbility(abilityName).exists(a => a != Abilities.Normal)

  override def ignoreEssence(abilityName: String): Boolean = abilities.getTypeForAbility(abilityName).exists(a => a == Abilities.Supernal)

  override def abilityType(abilityName: String): Option[Type] = abilities.getTypeForAbility(abilityName)
}


trait CharmLearnable extends Essencable with Abilitable with Attributable with Learnable

trait Essencable {
  def essence : Essence
}

trait Abilitable {
  def abilityRating(abilityName : String) : Option[Int]
  def abilityType(abilityName : String) : Option[Abilities.Type]
  def reducedCost(abilityName : String) : Boolean
  def ignoreEssence(abilityName : String) : Boolean
}

trait Attributable {
  def attributeRating(attributeName : String) : Option[Int]
}

trait Learnable {
  def canLearn(ct : CharmType) : Boolean

  def has(charm : Power with Product with Serializable) : Boolean
  def has(charmIndex : Int) : Boolean = has(Powers.powers(charmIndex))
}

sealed trait CharmType
case object SiderealMartialArtsType extends CharmType
case object MartialArtsType extends CharmType
case object SpiritCharmType extends CharmType
case object EclipseCharmType extends CharmType
case object SolarCharmType extends CharmType
case object EvocationType extends CharmType
case object TerestrialSpellType extends CharmType
case object CelestialSpellType extends CharmType
case object SolarCircleSpellType extends CharmType