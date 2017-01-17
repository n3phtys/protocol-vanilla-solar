package nephtys.loom.protocol.shared

import nephtys.loom.protocol.shared.CharmDatastructures._
import nephtys.loom.protocol.shared.Powers.Evocations.BelovedAdorei.Evo
import nephtys.loom.protocol.shared.Powers.MartialArtsCharms.DreamingPearlCourtesanStyle.{InvokingTheChimerasCoils, SevenStormsEscapePrana}
import nephtys.loom.protocol.shared.Powers.SolarCharms.Occult.Sorcery.{CelestialCircleSorcery, TerrestrialCircleSorcery}

import scala.scalajs.js.annotation.{JSExport, JSExportAll}
import upickle.default._




@JSExportAll
sealed trait HasPrerequisite {
  def prerequisite : Set[Power with Product with Serializable]
}

@JSExportAll
sealed trait Power extends HasPrerequisite{
  def essence : EssenceDots
  def essenceInt : Int = essence.dots

  def keywords : Set[String]
  def cost : String
  def duration : String

}

@JSExportAll
sealed trait Charm extends Power {
  def charmType : CharmType
}

@JSExportAll
sealed trait MartialArtsCharm extends Charm {
  def martialArtsDotsRequired : Int
}

@JSExportAll
sealed trait EclipseCharm extends Charm {
  override def prerequisite: Set[Power with Product with Serializable] = Set.empty
}


@JSExportAll
sealed trait Evocation extends Charm {
  def artifactName : String

}

@JSExportAll
sealed trait SolarCharm extends Charm {
  def abilityRequirement : Ability
  val abilityString : String = abilityRequirement.ability
  val abilityInt : Int = abilityRequirement.dots
}

@JSExportAll
sealed trait Spell extends Power {
  def shapingMoteCost : Int
  def willpowerCost : Int
  def cost : String = s"$shapingMoteCost sm, $willpowerCost wp"
}



@JSExportAll
sealed trait TerrestrialCircleSpell extends Spell {
  override def willpowerCost = 2
  override def prerequisite: Set[Power with Product with Serializable] = Set.empty
  override def essence = Essence1
}

@JSExportAll
sealed trait CelestialCircleSpell extends Spell {
  override def willpowerCost = 2
  override def prerequisite = Set(TerrestrialCircleSorcery)
  override def essence = Essence3
}

@JSExportAll
sealed trait SolarCircleSpell extends Spell {
  override def willpowerCost = 3
  override def prerequisite = Set(CelestialCircleSorcery)
  override def essence = Essence5
}

/**
  * Created by Christopher on 12.01.2017.
  */
@JSExportAll
object Powers {

  @JSExportAll
  object SolarCharms {

    def solarCharms: Seq[SolarCharm with Product with Serializable] = Occult.occultCharms


    @JSExportAll
     object Occult {

       def occultCharms: Seq[SolarCharm with Product with Serializable] = Seq(SpiritDetectingGlance, SpiritCuttingAttack, SpiritDrainingStance, UncannyPerceptionTechnique, KeenUnnaturalEye) ++ Sorcery.sorceryCharms

       case object SpiritDetectingGlance extends SolarCharm {
         override def abilityRequirement: Ability = Ability("Occult", 1)

         override def charmType: CharmType = Reflexive

         override def duration: String = "One scene"

         override def cost: String = "3m"

         override def essence: EssenceDots = Essence1

         override def keywords: Set[String] = Set.empty

         override def prerequisite: Set[Power with Product with Serializable] = Set.empty
       }
       case object SpiritCuttingAttack extends SolarCharm {
         override def abilityRequirement: Ability = Ability("Occult", 2)

         override def charmType: CharmType = Supplemental

         override def duration: String = "Instant"

         override def cost: String = "1m"

         override def essence: EssenceDots = Essence1

         override def keywords: Set[String] = Set("Uniform")

         override def prerequisite: Set[Power with Product with Serializable] = Set(SpiritDetectingGlance)
       }
       case object SpiritDrainingStance extends SolarCharm {
         override def abilityRequirement: Ability = Ability("Occult", 3)

         override def charmType: CharmType = Reflexive

         override def duration: String = "One scene"

         override def cost: String = "5m"

         override def essence: EssenceDots = Essence1

         override def keywords: Set[String] = Set.empty

         override def prerequisite: Set[Power with Product with Serializable] = Set(SpiritCuttingAttack)
       }
       case object UncannyPerceptionTechnique extends SolarCharm {
         override def abilityRequirement: Ability = Ability("Occult", 2)

         override def charmType: CharmType = Reflexive

         override def duration: String = "Instant"

         override def cost: String = "-"

         override def essence: EssenceDots = Essence1

         override def keywords: Set[String] = Set.empty

         override def prerequisite: Set[Power with Product with Serializable] = Set(SpiritDetectingGlance)
       }
       case object KeenUnnaturalEye extends SolarCharm {
         override def abilityRequirement: Ability = Ability("Occult", 3)

         override def charmType: CharmType = Permanent

         override def duration: String = "Permanent"

         override def cost: String = "-"

         override def essence: EssenceDots = Essence1

         override def keywords: Set[String] = Set.empty

         override def prerequisite: Set[Power with Product with Serializable] = Set(UncannyPerceptionTechnique)
       }




      @JSExportAll
       object Sorcery {

         def sorceryCharms = Seq(TerrestrialCircleSorcery, CelestialCircleSorcery, SolarCircleSorcery)

         case object TerrestrialCircleSorcery extends SolarCharm {
         override def abilityRequirement: Ability = Ability("Occult", 3)

         override def essence = Essence1

         override def keywords: Set[String] = Set.empty

         override def prerequisite: Set[Power with Product with Serializable] = Set.empty

           override def charmType: CharmType = Permanent

           override def duration: String = "Permanent"

           override def cost: String = "-"
         }
         case object CelestialCircleSorcery extends SolarCharm {
           override def abilityRequirement: Ability = Ability("Occult", 4)

           override def essence = Essence3

           override def keywords: Set[String] = Set.empty

           override def prerequisite: Set[Power with Product with Serializable] = Set(TerrestrialCircleSorcery)

           override def charmType: CharmType = Permanent

           override def duration: String = "Permanent"

           override def cost: String = "-"
         }
         case object SolarCircleSorcery extends SolarCharm {
           override def abilityRequirement: Ability = Ability("Occult", 5)

           override def essence = Essence5

           override def keywords: Set[String] = Set.empty

           override def prerequisite: Set[Power with Product with Serializable] = Set(CelestialCircleSorcery)

           override def charmType: CharmType = Permanent

           override def duration: String = "Permanent"

           override def cost: String = "-"
         }
       }

     }
  }

  @JSExportAll
  object Spells {
    def spells: Seq[Spell with Product with Serializable] = TerrestrialCircle.terrestrialCircleSpells ++ CelestialCircle.celestialCircleSpells ++ SolarCircle.solarCircleSpells



    @JSExportAll
    object TerrestrialCircle {
      def terrestrialCircleSpells: Seq[TerrestrialCircleSpell with Product with Serializable]  = Seq()




    }

    @JSExportAll
    object CelestialCircle {
      def celestialCircleSpells: Seq[CelestialCircleSpell with Product with Serializable]  = Seq()

    }

    @JSExportAll
    object SolarCircle {
      def solarCircleSpells: Seq[SolarCircleSpell with Product with Serializable] = Seq(BenedictionOfArchgenesis, DeathRay, DemonOfTheThirdCircle, RainOfDoom)


      case object BenedictionOfArchgenesis extends SolarCircleSpell {
        override def shapingMoteCost: Int = 0

        override def duration: String = "Instant"

        override def keywords: Set[String] = Set.empty
      }
      case object DeathRay extends SolarCircleSpell {
        override def shapingMoteCost: Int = 25

        override def duration: String = "Instant or until ended"
        override def willpowerCost = 2

        override def keywords: Set[String] = Set("Aggravated", "Decisive-only", "Perilous")
      }
      case object DemonOfTheThirdCircle extends SolarCircleSpell {

        override def willpowerCost = 4

        override def shapingMoteCost: Int = 0

        override def duration: String = "Instant"

        override def keywords: Set[String] = Set.empty
      }
      case object RainOfDoom extends SolarCircleSpell {
        override def shapingMoteCost: Int = 40

        override def duration: String = "Until sunrise"

        override def keywords: Set[String] = Set("Aggravated")
      }
    }

  }

  @JSExportAll
  object Evocations {
    def evocations: Seq[Evocation with Product with Serializable] = BelovedAdorei.evocations


    @JSExportAll
      object BelovedAdorei {
        def evocations = Seq(HeartKnowingBlade, NoOtherBlade, MagnanimousSunfireBlast, HolyMiracleStrike, BattleDanceOfTheWarriorWed)

        sealed trait Evo extends Evocation{
          override def artifactName = "Beloved Adorei"
        }
        case object HeartKnowingBlade extends Evo {
          override def charmType: CharmType = Supplemental

          override def duration: String = "Instant"

          override def cost: String = "3m"

          override def essence = Essence1

          override def keywords: Set[String] = Set("Decisive-only")

          override def prerequisite: Set[Power with Product with Serializable] = Set.empty
        }
        case object NoOtherBlade extends Evo {
          override def charmType: CharmType = Supplemental

          override def duration: String = "Instant"

          override def cost: String = "1m"

          override def essence: EssenceDots = Essence1

          override def keywords: Set[String] = Set("Uniform")

          override def prerequisite: Set[Power with Product with Serializable] = Set(HeartKnowingBlade)
        }
        case object MagnanimousSunfireBlast extends Evo {
          override def charmType: CharmType = Reflexive

          override def duration: String = "Instant"

          override def cost: String = "1m per revealed Intimacy"

          override def essence: EssenceDots = Essence2

          override def keywords: Set[String] = Set("Decisive-only")

          override def prerequisite: Set[Power with Product with Serializable] = Set(HeartKnowingBlade)
        }
        case object HolyMiracleStrike extends Evo {
          override def charmType: CharmType = Simple

          override def duration: String = "Instant"

          override def cost: String = "10m, 1wp"

          override def essence: EssenceDots = Essence3

          override def keywords: Set[String] = Set("Uniform")

          override def prerequisite: Set[Power with Product with Serializable] = Set(HeartKnowingBlade)
        }
        case object BattleDanceOfTheWarriorWed extends Evo {
          override def charmType: CharmType = Reflexive

          override def duration: String = "One combat"

          override def cost: String = "-"

          override def essence: EssenceDots = Essence3

          override def keywords: Set[String] = Set("Uniform")

          override def prerequisite: Set[Power with Product with Serializable] = Set(HolyMiracleStrike, MagnanimousSunfireBlast, NoOtherBlade)
        }
      }
  }

  @JSExportAll
  object EclipseCharms {
    def eclipseCharms = Seq(SeductiveShapechange, NightBlackCarapace, StormStirringLash)


    case object SeductiveShapechange extends EclipseCharm {
      override def charmType: CharmType = Simple

      override def duration: String = "One scene"

      override def cost: String = "8m"

      override def essence: EssenceDots = Essence1

      override def keywords: Set[String] = Set("Eclipse")
    }
    case object NightBlackCarapace extends EclipseCharm {
      override def charmType: CharmType = Simple

      override def duration: String = "One scene"

      override def cost: String = "5m, 1wp"

      override def essence: EssenceDots = Essence4

      override def keywords: Set[String] = Set("Eclipse")
    }
    case object StormStirringLash extends EclipseCharm {
      override def charmType: CharmType = Simple

      override def duration: String = "Essence hours"

      override def cost: String = "15m, 1wp"

      override def essence: EssenceDots = Essence3

      override def keywords: Set[String] = Set("Eclipse")
    }


  }

  @JSExportAll
  object MartialArtsCharms {
    def martialArtsCharms: Seq[MartialArtsCharm with Product with Serializable] = DreamingPearlCourtesanStyle.charms


    @JSExportAll
    object DreamingPearlCourtesanStyle {
      def charms = Seq(DemureCarpFeint, ElegantWeaponRepertoire, PearlescentFiligreeDefense, DreamingPearlCourtesanForm,
        FlurryOfAugustLeaves, VindictiveConcubinesPillowBook, FragmentPetalFascinationKata, SevenStormsEscapePrana, InvokingTheChimerasCoils
      )

      case object DemureCarpFeint extends MartialArtsCharm {
        override def charmType: CharmType = Reflexive

        override def duration: String = "Instant"

        override def cost: String = "3m"

        override def essence: EssenceDots = Essence1

        override def keywords: Set[String] = Set("Mastery", "Uniform")

        override def prerequisite: Set[Power with Product with Serializable] = Set.empty

        override def martialArtsDotsRequired: Int = 3
      }
      case object ElegantWeaponRepertoire extends MartialArtsCharm {
        override def martialArtsDotsRequired: Int = 3

        override def charmType: CharmType = Supplemental

        override def duration: String = "Instant"

        override def cost: String = "3m"

        override def essence: EssenceDots = Essence1

        override def keywords: Set[String] = Set("Dual", "Mastery")

        override def prerequisite: Set[Power with Product with Serializable] = Set.empty
      }
      case object PearlescentFiligreeDefense extends MartialArtsCharm {
        override def charmType: CharmType = Reflexive

        override def duration: String = "One scene"

        override def cost: String = "1m"

        override def essence: EssenceDots = Essence1

        override def keywords: Set[String] = Set("Mastery")

        override def prerequisite: Set[Power with Product with Serializable] = Set.empty

        override def martialArtsDotsRequired: Int = 2
      }
      case object DreamingPearlCourtesanForm extends MartialArtsCharm {
        override def martialArtsDotsRequired: Int = 4

        override def charmType: CharmType = Simple

        override def duration: String = "One scene"

        override def cost: String = "8m"

        override def essence: EssenceDots = Essence2

        override def keywords: Set[String] = Set("Form")

        override def prerequisite: Set[Power with Product with Serializable] = Set(DemureCarpFeint, ElegantWeaponRepertoire, PearlescentFiligreeDefense)
      }
      case object FlurryOfAugustLeaves extends MartialArtsCharm {
        override def martialArtsDotsRequired: Int = 4

        override def charmType: CharmType = Supplemental

        override def duration: String = "Instant"

        override def cost: String = "3m, 1wp"

        override def essence: EssenceDots = Essence2

        override def keywords: Set[String] = Set("Decisive-only")

        override def prerequisite: Set[Power with Product with Serializable] = Set(DreamingPearlCourtesanForm)
      }
      case object VindictiveConcubinesPillowBook extends MartialArtsCharm {
        override def martialArtsDotsRequired: Int = 5

        override def charmType: CharmType = Reflexive

        override def duration: String = "Instant"

        override def cost: String = "7m"

        override def essence: EssenceDots = Essence3

        override def keywords: Set[String] = Set("Decisive-only", "Terrestrial")

        override def prerequisite: Set[Power with Product with Serializable] = Set(FlurryOfAugustLeaves)
      }
      case object FragmentPetalFascinationKata extends MartialArtsCharm {
        override def martialArtsDotsRequired: Int = 4

        override def charmType: CharmType = Supplemental

        override def duration: String = "Instant"

        override def cost: String = "4m"

        override def essence: EssenceDots = Essence2

        override def keywords: Set[String] = Set.empty

        override def prerequisite: Set[Power with Product with Serializable] = Set(DreamingPearlCourtesanForm)
      }
      case object SevenStormsEscapePrana extends MartialArtsCharm {
        override def martialArtsDotsRequired: Int = 4

        override def charmType: CharmType = Reflexive

        override def duration: String = "Instant"

        override def cost: String = "4m, 2i"

        override def essence: EssenceDots = Essence2

        override def keywords: Set[String] = Set("Mastery")

        override def prerequisite: Set[Power with Product with Serializable] = Set(FragmentPetalFascinationKata)
      }
      case object InvokingTheChimerasCoils extends MartialArtsCharm {
        override def martialArtsDotsRequired: Int = 5

        override def charmType: CharmType = Permanent

        override def duration: String = "Permanent"

        override def cost: String = "- (+8m, 1wp)"

        override def essence: EssenceDots = Essence3

        override def keywords: Set[String] = Set("Mastery", "Terrestrial")

        override def prerequisite: Set[Power with Product with Serializable] = Set(SevenStormsEscapePrana, VindictiveConcubinesPillowBook)
      }

    }
  }


  val x : Seq[Charm] = Seq(InvokingTheChimerasCoils, SevenStormsEscapePrana)

  write(x)
  //println(s"SolarCharms serialized: ${
  //}")
}

