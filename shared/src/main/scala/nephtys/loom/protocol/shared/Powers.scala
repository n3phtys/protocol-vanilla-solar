package nephtys.loom.protocol.shared

import nephtys.loom.protocol.shared.CharmDatastructures._
import nephtys.loom.protocol.shared.Powers.SolarCharms.Occult.Sorcery.{CelestialCircleSorcery, TerrestrialCircleSorcery}

/**
  * Created by Christopher on 12.01.2017.
  */
object Powers {

  sealed trait Power {
    def essence : EssenceDots
    def keywords : Set[String]
    def prerequisite : Set[Power]
  }

  sealed trait Charm extends Power {
    def charmType : CharmType
    def duration : String
    def cost : String
  }

  object SolarCharms {
    sealed trait SolarCharm extends Charm {
      def abilityRequirement : Ability
    }
     object Occult {

       case object SpiritDetectingGlance extends SolarCharm {
         override def abilityRequirement: Ability = Ability("Occult", 1)

         override def charmType: CharmType = Reflexive

         override def duration: String = "One scene"

         override def cost: String = "3m"

         override def essence: EssenceDots = Essence1

         override def keywords: Set[String] = Set.empty

         override def prerequisite: Set[Power] = Set.empty
       }
       case object SpiritCuttingAttack extends SolarCharm {
         override def abilityRequirement: Ability = Ability("Occult", 2)

         override def charmType: CharmType = Supplemental

         override def duration: String = "Instant"

         override def cost: String = "1m"

         override def essence: EssenceDots = Essence1

         override def keywords: Set[String] = Set("Uniform")

         override def prerequisite: Set[Power] = Set(SpiritDetectingGlance)
       }
       case object SpiritDrainingStance extends SolarCharm {
         override def abilityRequirement: Ability = Ability("Occult", 3)

         override def charmType: CharmType = Reflexive

         override def duration: String = "One scene"

         override def cost: String = "5m"

         override def essence: EssenceDots = Essence1

         override def keywords: Set[String] = Set.empty

         override def prerequisite: Set[Power] = Set(SpiritCuttingAttack)
       }
       case object UncannyPerceptionTechnique extends SolarCharm {
         override def abilityRequirement: Ability = Ability("Occult", 2)

         override def charmType: CharmType = Reflexive

         override def duration: String = "Instant"

         override def cost: String = "-"

         override def essence: EssenceDots = Essence1

         override def keywords: Set[String] = Set.empty

         override def prerequisite: Set[Power] = Set(SpiritDetectingGlance)
       }
       case object KeenUnnaturalEye extends SolarCharm {
         override def abilityRequirement: Ability = Ability("Occult", 3)

         override def charmType: CharmType = Permanent

         override def duration: String = "Permanent"

         override def cost: String = "-"

         override def essence: EssenceDots = Essence1

         override def keywords: Set[String] = Set.empty

         override def prerequisite: Set[Power] = Set(UncannyPerceptionTechnique)
       }




       object Sorcery {

         case object TerrestrialCircleSorcery extends SolarCharm {
         override def abilityRequirement: Ability = Ability("Occult", 3)

         override def essence = Essence1

         override def keywords: Set[String] = Set.empty

         override def prerequisite: Set[Power] = Set.empty

           override def charmType: CharmType = Permanent

           override def duration: String = "Permanent"

           override def cost: String = "-"
         }
         case object CelestialCircleSorcery extends SolarCharm {
           override def abilityRequirement: Ability = Ability("Occult", 4)

           override def essence = Essence3

           override def keywords: Set[String] = Set.empty

           override def prerequisite: Set[Power] = Set(TerrestrialCircleSorcery)

           override def charmType: CharmType = Permanent

           override def duration: String = "Permanent"

           override def cost: String = "-"
         }
         case object SolarCircleSorcery extends SolarCharm {
           override def abilityRequirement: Ability = Ability("Occult", 5)

           override def essence = Essence5

           override def keywords: Set[String] = Set.empty

           override def prerequisite: Set[Power] = Set(CelestialCircleSorcery)

           override def charmType: CharmType = Permanent

           override def duration: String = "Permanent"

           override def cost: String = "-"
         }
       }

     }
  }

  object Spells {
    sealed trait Spell extends Power {
      def shapingMoteCost : Int
      def willpowerCost : Int
      def duration : String
    }

    object TerrestrialCircle {
      sealed trait TerrestrialCircleSpell extends Spell {
        override def willpowerCost = 2
        override def prerequisite: Set[Power] = Set.empty
        override def essence = Essence1
      }



    }

    object CelestialCircle {
      sealed trait CelestialCircleSpell extends Spell {
        override def willpowerCost = 2
        override def prerequisite = Set(TerrestrialCircleSorcery)
        override def essence = Essence3
      }
    }

    object SolarCircle {
      sealed trait SolarCircleSpell extends Spell {
        override def willpowerCost = 3
        override def prerequisite = Set(CelestialCircleSorcery)
        override def essence = Essence5
      }

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

  object Evocations {
      sealed trait Evocation extends Charm {
        def artifactName : String

      }

      object BelovedAdorei {
        sealed trait Evo extends Evocation{
          override def artifactName = "Beloved Adorei"
        }
        case object HeartKnowingBlade extends Evo {
          override def charmType: CharmType = Supplemental

          override def duration: String = "Instant"

          override def cost: String = "3m"

          override def essence = Essence1

          override def keywords: Set[String] = Set("Decisive-only")

          override def prerequisite: Set[Power] = Set.empty
        }
        case object NoOtherBlade extends Evo {
          override def charmType: CharmType = Supplemental

          override def duration: String = "Instant"

          override def cost: String = "1m"

          override def essence: EssenceDots = Essence1

          override def keywords: Set[String] = Set("Uniform")

          override def prerequisite: Set[Power] = Set(HeartKnowingBlade)
        }
        case object MagnanimousSunfireBlast extends Evo {
          override def charmType: CharmType = Reflexive

          override def duration: String = "Instant"

          override def cost: String = "1m per revealed Intimacy"

          override def essence: EssenceDots = Essence2

          override def keywords: Set[String] = Set("Decisive-only")

          override def prerequisite: Set[Power] = Set(HeartKnowingBlade)
        }
        case object HolyMiracleStrike extends Evo {
          override def charmType: CharmType = Simple

          override def duration: String = "Instant"

          override def cost: String = "10m, 1wp"

          override def essence: EssenceDots = Essence3

          override def keywords: Set[String] = Set("Uniform")

          override def prerequisite: Set[Power] = Set(HeartKnowingBlade)
        }
        case object BattleDanceOfTheWarriorWed extends Evo {
          override def charmType: CharmType = Reflexive

          override def duration: String = "One combat"

          override def cost: String = "-"

          override def essence: EssenceDots = Essence3

          override def keywords: Set[String] = Set("Uniform")

          override def prerequisite: Set[Power] = Set(HolyMiracleStrike, MagnanimousSunfireBlast, NoOtherBlade)
        }
      }
  }

  object EclipseCharms {
    sealed trait EclipseCharm extends Charm {
      override def prerequisite: Set[Power] = Set.empty
    }

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

  object MartialArtsCharms {
    sealed trait MartialArtsCharm extends Charm {
      def martialArtsDotsRequired : Int
    }

    object DreamingPearlCourtesanStyle {
      case object DemureCarpFeint extends MartialArtsCharm {
        override def charmType: CharmType = Reflexive

        override def duration: String = "Instant"

        override def cost: String = "3m"

        override def essence: EssenceDots = Essence1

        override def keywords: Set[String] = Set("Mastery", "Uniform")

        override def prerequisite: Set[Power] = Set.empty

        override def martialArtsDotsRequired: Int = 3
      }
      case object ElegantWeaponRepertoire extends MartialArtsCharm {
        override def martialArtsDotsRequired: Int = 3

        override def charmType: CharmType = Supplemental

        override def duration: String = "Instant"

        override def cost: String = "3m"

        override def essence: EssenceDots = Essence1

        override def keywords: Set[String] = Set("Dual", "Mastery")

        override def prerequisite: Set[Power] = Set.empty
      }
      case object PearlescentFiligreeDefense extends MartialArtsCharm {
        override def charmType: CharmType = Reflexive

        override def duration: String = "One scene"

        override def cost: String = "1m"

        override def essence: EssenceDots = Essence1

        override def keywords: Set[String] = Set("Mastery")

        override def prerequisite: Set[Power] = Set.empty

        override def martialArtsDotsRequired: Int = 2
      }
      case object DreamingPearlCourtesanForm extends MartialArtsCharm {
        override def martialArtsDotsRequired: Int = 4

        override def charmType: CharmType = Simple

        override def duration: String = "One scene"

        override def cost: String = "8m"

        override def essence: EssenceDots = Essence2

        override def keywords: Set[String] = Set("Form")

        override def prerequisite: Set[Power] = Set(DemureCarpFeint, ElegantWeaponRepertoire, PearlescentFiligreeDefense)
      }
      case object FlurryOfAugustLeaves extends MartialArtsCharm {
        override def martialArtsDotsRequired: Int = 4

        override def charmType: CharmType = Supplemental

        override def duration: String = "Instant"

        override def cost: String = "3m, 1wp"

        override def essence: EssenceDots = Essence2

        override def keywords: Set[String] = Set("Decisive-only")

        override def prerequisite: Set[Power] = Set(DreamingPearlCourtesanForm)
      }
      case object VindictiveConcubinesPillowBook extends MartialArtsCharm {
        override def martialArtsDotsRequired: Int = 5

        override def charmType: CharmType = Reflexive

        override def duration: String = "Instant"

        override def cost: String = "7m"

        override def essence: EssenceDots = Essence3

        override def keywords: Set[String] = Set("Decisive-only", "Terrestrial")

        override def prerequisite: Set[Power] = Set(FlurryOfAugustLeaves)
      }
      case object FragmentPetalFascinationKata extends MartialArtsCharm {
        override def martialArtsDotsRequired: Int = 4

        override def charmType: CharmType = Supplemental

        override def duration: String = "Instant"

        override def cost: String = "4m"

        override def essence: EssenceDots = Essence2

        override def keywords: Set[String] = Set.empty

        override def prerequisite: Set[Power] = Set(DreamingPearlCourtesanForm)
      }
      case object SevenStormsEscapePrana extends MartialArtsCharm {
        override def martialArtsDotsRequired: Int = 4

        override def charmType: CharmType = Reflexive

        override def duration: String = "Instant"

        override def cost: String = "4m, 2i"

        override def essence: EssenceDots = Essence2

        override def keywords: Set[String] = Set("Mastery")

        override def prerequisite: Set[Power] = Set(FragmentPetalFascinationKata)
      }
      case object InvokingTheChimerasCoils extends MartialArtsCharm {
        override def martialArtsDotsRequired: Int = 5

        override def charmType: CharmType = Permanent

        override def duration: String = "Permanent"

        override def cost: String = "- (+8m, 1wp)"

        override def essence: EssenceDots = Essence3

        override def keywords: Set[String] = Set("Mastery", "Terrestrial")

        override def prerequisite: Set[Power] = Set(SevenStormsEscapePrana, VindictiveConcubinesPillowBook)
      }

    }
  }

}
