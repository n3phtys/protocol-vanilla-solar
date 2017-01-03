package nephtys.loom.protocol.vanilla.solar

import nephtys.loom.protocol.vanilla.solar.Misc._

import scala.scalajs.js.annotation.JSExportAll

/**
  * Created by nephtys on 12/7/16.
  */
@JSExportAll
object Abilities {

  sealed trait AbilityLike {
    def abilities : Set[Ability]
    def isCaste(caste: Caste) : Boolean
  }
  sealed trait SpecialtyAble {
    def name : String
  }

  sealed trait AbilityLikeSpecialtyAble extends AbilityLike with SpecialtyAble

  sealed trait Typeable {
    def typeabletitle : String
  }

  final case class Ability(name : String)

  final case class SingleAbility(ability : Ability) extends AbilityLikeSpecialtyAble with Typeable {
    override def abilities: Set[Ability] = Set(ability)

    override def name: String = ability.name

    override def isCaste(caste: Caste): Boolean = preprogrammed.casteAbility(caste).contains(ability)

    override def typeabletitle: String = ability.name
  }
  final case class Specialty(name : String) extends AnyVal
  final case class AbilityMatrix(abilities : Set[AbilityLikeSpecialtyAble], ratings : Map[Ability, Dots], types : Map[Typeable, Type], specialties : Map[SpecialtyAble, Set[Specialty]]) {
    def buildTypeableTree : Map[String, (Map[String, Int], Boolean, Abilities.Type)] = {
        val t : Set[(String, Boolean, Map[String, Int], Abilities.Type)] = abilities.map {
          case s: SingleAbility => {
            val p : (String, Boolean, Map[String, Int], Abilities.Type) = (s.typeabletitle, false, Map[String, Int](s.ability.name -> ratings(s.ability).number.toInt), types(s))
            p
          }
          case d: DuoAbilityGroup => {
            val p : (String, Boolean, Map[String, Int], Abilities.Type) = (d.typeabletitle, false, Map[String, Int](d.abilityA.name -> ratings(d.abilityA).number.toInt, d.abilityB.name -> ratings(d.abilityB).number.toInt), types(d))
            p
          }
          case f: AbilityFamily => {
            val p : (String, Boolean, Map[String, Int], Abilities.Type) = (f.typeabletitle, true, f.instances.map(i => (i.name, ratings(i).number.toInt)).toMap[String, Int], types(f))
            p
          }
        }
      t.map(k => (k._1, (k._3, k._2, k._4))).toMap
    }
    //TODO: brawl and martial arts can never be supernal at the same time

    def spentWithFreePoints : Int = {
      //sum number of points 1-3
      //return min with 28
      Math.min(28, ratings.values.map(i => Math.min(i.number, 3)).sum)
    }

    def numberOfSpecialties : Int = specialties.map(_._2.size).sum

    def addSpecialty(specialtyAble: AbilityLikeSpecialtyAble, title : String) : AbilityMatrix = copy(specialties = specialties.+((specialtyAble, specialties.getOrElse(specialtyAble, Set.empty).+(Specialty(title)))))

    def removeSpecialty(specialtyAble: AbilityLikeSpecialtyAble, title : String) : AbilityMatrix = copy(specialties = specialties.+((specialtyAble, specialties.getOrElse(specialtyAble, Set.empty).-(Specialty(title)))))

    //bpcost, xpcost, abilitymatrix with change
    def setRating(ability : Ability, rating : Int) : (Int, Int , AbilityMatrix) = {
      ???
    }

    def setType(typeable: Typeable, typ : Type) : AbilityMatrix = {
      ???
    }

    def addSubability(familyTitle : String, title : String) : AbilityMatrix = {
      copy(abilities = ???, ratings = ???)
      ???
    }

    def removeSubability(familyTitle : String, title : String) : AbilityMatrix = {
      copy(abilities = ???, ratings = ???)
      ???
    }





    def specialtyAbles : Seq[String] = abilities.map(_.name).toSeq.sorted

    def typeables : Set[String] = abilities.filter(_.isInstanceOf[Typeable]).map(_.asInstanceOf[Typeable].typeabletitle)
  }

  private val inverseSpecialtyMap : Map[String, AbilityLikeSpecialtyAble] = emptyMatrix.abilities.map(s => (s.toString, s)).toMap
  private val defaultSpecialty : AbilityLikeSpecialtyAble = emptyMatrix.abilities.head
  def specialtyAble(from : String) : AbilityLikeSpecialtyAble = inverseSpecialtyMap.getOrElse(from, defaultSpecialty)

  sealed trait Type
  case object Normal extends Type
  case object Caste extends Type
  case object Favored extends Type
  case object Supernal extends Type
  val types = IndexedSeq(Normal, Caste, Favored, Supernal)
  val strToTypes: Map[String, Type] = types.map(t => (t.toString, t)).toMap

  //there is a hierarchy to enable special behavior for Craft
  //and Brawl / Martial Arts split
  //a sealed trait hierarchy of the possible concepts

  final case class DuoAbilityGroup(abilityA : Ability, abilityB : Ability, title : String) extends AbilityLikeSpecialtyAble with Typeable {
    override def abilities: Set[Ability] = Set(abilityA, abilityB)

    override def isCaste(caste: Caste): Boolean = preprogrammed.casteAbility(caste).contains(abilityA) || preprogrammed.casteAbility(caste).contains(abilityA)

    override def typeabletitle: String = title

    override def name: String = title
  }
  final case class AbilityFamily(instances : Set[Ability], familityName : String) extends AbilityLikeSpecialtyAble with Typeable{
    override def abilities: Set[Ability] = instances

    override def isCaste(caste: Caste): Boolean = preprogrammed.casteAbility(caste).contains(Ability(familityName))

    override def typeabletitle: String = familityName

    override def name: String = familityName

    def add(title : String) : AbilityFamily = ???

    def remove(title : String) : AbilityFamily = ???
  }

  def emptyMatrix : AbilityMatrix = {
    val ab : Set[AbilityLikeSpecialtyAble] = preprogrammed.list
    val dots : Map[Ability, Dots] = ab.flatMap(_.abilities).map(a => (a, Dots(0))).toMap
    val types : Map[Typeable, Type] = ab.filter(_.isInstanceOf[Typeable]).map(_.asInstanceOf[Typeable]).map(t => (t, Normal)).toMap
    val specialties : Map[SpecialtyAble, Set[Specialty]] = Map.empty
    AbilityMatrix(ab, dots, types, specialties)
  }

  object preprogrammed {
    def isCasteAbility(caste: Option[Caste], title: String) : Boolean = {
      //println(s"comparing title \n$title as caste ability against \n$BrawlMartialArtsComboLabel")
      title match {
        case CraftsLabel => caste.contains(Twilight)
        case BrawlMartialArtsComboLabel => caste.contains(Dawn)
        case s : String => caste.isDefined && casteAbility(caste.get).exists(_.name == s)
      }
    }

    val Archery = "Archery"
    val Athletics = "Athletics"
    val Awareness = "Awareness"
    val Brawl = "Brawl"
    val Bureaucracy = "Bureaucracy"
    val Craft = "Craft"
    val Dodge = "Dodge"
    val Integrity = "Integrity"
    val Investigation = "Investigation"
    val Larceny = "Larceny"
    val Linguistics = "Linguistics"
    val Lore = "Lore"
    val MartialArts = "Martial Arts"
    val Medicine = "Medicine"
    val Melee = "Melee"
    val Occult = "Occult"
    val Performance = "Performance"
    val Presence = "Presence"
    val Resistance = "Resistance"
    val Ride = "Ride"
    val Sail = "Sail"
    val Socialize = "Socialize"
    val Stealth = "Stealth"
    val Survival = "Survival"
    val Thrown = "Thrown"
    val War = "War"

    val BrawlMartialArtsComboLabel : String = Brawl + " / " + MartialArts
    val CraftsLabel : String = Craft

    private def singlesAb = {
      val str = Set[String](
        Archery, Athletics, Awareness, Bureaucracy, Dodge, Integrity, Investigation, Larceny, Linguistics,
        Lore,Medicine,Melee,Occult,Performance,Presence,Resistance,Ride,Sail,Socialize,
        Stealth,Survival,Thrown,War
      )
      val r : Set[Ability] = str.map(s => Ability(s))
      r
    }
    private def singles : Set[AbilityLikeSpecialtyAble] = singlesAb.map(s => SingleAbility(s))

    def list : Set[AbilityLikeSpecialtyAble] = singles + DuoAbilityGroup(Ability(Brawl), Ability(MartialArts), BrawlMartialArtsComboLabel) + AbilityFamily(Set(Ability(Craft + " Weaponsmithing")), CraftsLabel)

    def casteAbility(caste : Caste) : Set[Ability] = (caste match {
      case Dawn => Set(Archery, Awareness, Brawl, MartialArts, Dodge, Melee, Resistance, Thrown, War)
      case Zenith => Set(Athletics, Integrity, Performance, Lore, Presence, Resistance, Survival, War)
      case Twilight => Set(Bureaucracy, Craft, Integrity, Investigation, Linguistics, Lore, Medicine, Occult)
      case Night => Set(Athletics, Awareness,Dodge,Investigation,Larceny,Ride, Stealth, Socialize)
      case Eclipse => Set(Bureaucracy, Larceny, Linguistics, Occult, Presence, Ride, Sail, Socialize)
    }).map(Ability)
  }
}
