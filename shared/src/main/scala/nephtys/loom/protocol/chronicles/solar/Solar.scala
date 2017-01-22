package nephtys.loom.protocol.chronicles.solar

import nephtys.loom.protocol.shared.{CustomPowers, Power, Powers}
import nephtys.loom.protocol.vanilla.solar.Abilities.Type
import nephtys.loom.protocol.vanilla.solar.Equipments.Equipment
import nephtys.loom.protocol.vanilla.solar.Merits.Merit
import nephtys.loom.protocol.vanilla.solar.Misc.{Caste, Eclipse, Essence}
import nephtys.loom.protocol.vanilla.solar._
import org.nephtys.loom.generic.protocol.InternalStructures.{ID, MetaInfo}

/**
  * Created by Christopher on 21.01.2017.
  */
case class Solar(

                  metaInfo : MetaInfo,
                  id : ID[Solar],
                  stillInCharGen  : Boolean,

                  metaDescriptors: NamedSolarMetaDescriptors,

                  caste : Option[Caste],

                  attributes : Attributes.AttributeBlock,
                  abilities : Abilities.AbilitySet,
                  merits : List[Merit],

                  willpowerDots : Int,
                  essenceCommitted : Int,

                  experience : Experiences.ExperienceBeatBox,

                  equipment : List[Equipment],

                  aspirations : IndexedSeq[Aspirations.Aspiration],

                  intimacies : Map[String, Intimacies.Intensity],

                  listedPowers : Set[Int],
                  customCharms : Seq[CustomPowers.CustomPower],


                  notes : List[String]

                )
  extends org.nephtys.loom.generic.protocol.Aggregate[Solar]
with CharmLearnable
{

  //TODO: own abilities (as martial arts and craft are flattened into one single ability each)

  //copy everything from vanilla solar
  //todo: change following
  //todo: experience different with beats
  //todo: no bonus points (instead, start experience box with 10 general)
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


  override def attributeRating(attributeName: String): Option[Int] = ???

  override def essence: Essence = Misc.Essence(experience.essenceLevel)

  override def abilityRating(abilityName: String): Option[Int] = ???

  override def abilityType(abilityName: String): Option[Type] = abilities.getTypeForAbility(abilityName)

  override def reducedCost(abilityName: String): Boolean = abilities.getTypeForAbility(abilityName).exists(a => a != nephtys.loom.protocol.vanilla.solar.Abilities.Normal)

  override def ignoreEssence(abilityName: String): Boolean = abilities.getTypeForAbility(abilityName).exists(a => a == nephtys.loom.protocol.vanilla.solar.Abilities.Supernal)
}
