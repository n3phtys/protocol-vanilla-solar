package nephtys.loom.protocol.chronicles.solar

import nephtys.loom.protocol.shared.CustomPowers.FreePointCost
import nephtys.loom.protocol.shared.{CustomPowers, Power, Powers}
import nephtys.loom.protocol.vanilla.solar.Abilities.Type
import nephtys.loom.protocol.vanilla.solar.Equipments.Equipment
import nephtys.loom.protocol.vanilla.solar.Merits.Merit
import nephtys.loom.protocol.vanilla.solar.Misc.{Caste, Eclipse, Essence}
import nephtys.loom.protocol.vanilla.solar._
import org.nephtys.loom.generic.protocol.InternalStructures.{ID, MetaInfo}

import scala.scalajs.js.annotation.{JSExport, JSExportAll}



@JSExport
@JSExportAll
final case class DirectDotValues(
                                  willpowerDots : Int, //xp relevant
                                  essenceCommitted : Int,
                                  openMeritPoints : Int
                                )
/**
  * Created by Christopher on 21.01.2017.
  */

@JSExport
@JSExportAll
final case class Solar(

                  metaInfo : MetaInfo,
                  id : ID[Solar],
                  stillInCharGen  : Boolean,

                  directDotValues: DirectDotValues,

                  metaDescriptors: NamedSolarMetaDescriptors,

                  caste : Option[Caste], //xp relevant

                  attributes : Attributes.AttributeBlock, //xp relevant
                  abilities : Abilities.AbilitySet, //xp relevant
                  merits : List[Merit], //xp relevant



                  experience : Experiences.ExperienceBeatBox, //xp relevant

                  equipment : List[Equipment],

                  aspirations : IndexedSeq[Aspirations.Aspiration],

                  intimacies : Map[String, Intimacies.Intensity],

                  listedPowers : Set[Int], //xp relevant
                  customCharms : Seq[CustomPowers.CustomPower], //xp relevant


                  notes : List[String]

                )
  extends org.nephtys.loom.generic.protocol.Aggregate[Solar]
with CharmLearnable
{


  def name : String = metaDescriptors.name
  def casteString : String = caste.map(_.toString).getOrElse("Casteless")

  //own abilities (as martial arts and craft are flattened into one single ability each)

  //copy everything from vanilla solar
  //change following
  //experience different with beats
  //no bonus points (instead, start experience box with 15 general)
  //aspirations selector + list



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


  override def attributeRating(attributeName: String): Option[Int] = attributes.getRating(attributeName)

  override def essence: Essence = Misc.Essence(experience.essenceLevel)

  override def abilityRating(abilityName: String): Option[Int] = abilities.getRatingForAbility(abilityName)

  override def abilityType(abilityName: String): Option[Type] = abilities.getTypeForAbility(abilityName)

  override def reducedCost(abilityName: String): Boolean = abilities.getTypeForAbility(abilityName).exists(a => a != nephtys.loom.protocol.vanilla.solar.Abilities.Normal)

  override def ignoreEssence(abilityName: String): Boolean = abilities.getTypeForAbility(abilityName).exists(a => a == nephtys.loom.protocol.vanilla.solar.Abilities.Supernal)

  override def selectableAbilities: Seq[String] = abilities.abilities.map(_.name)

  override def countCharmPurchases: Int = listedPowers.size + customCharms.map(a => a.customCost match {
    case FreePointCost(i) => i
    case _ => 0
  }).sum
}
