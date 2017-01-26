package nephtys.loom.protocol.zprotocols

import nephtys.loom.protocol.shared.CustomPowers.{BonusPointCost, CustomPower, ExperiencePointCost, FreePointCost}
import nephtys.loom.protocol.shared._
import nephtys.loom.protocol.vanilla.solar.Abilities._
import nephtys.loom.protocol.vanilla.solar.Attributes.{AttributeBlock, AttributeRating}
import nephtys.loom.protocol.vanilla.solar.Experiences.{ExperienceBox, ExperienceType}
import nephtys.loom.protocol.vanilla.solar.Intimacies.Intensity
import nephtys.loom.protocol.vanilla.solar.Merits.Merit
import nephtys.loom.protocol.vanilla.solar.Misc.{Caste, Dots}
import nephtys.loom.protocol.vanilla.solar._
import org.nephtys.loom.generic.protocol.EventInput.EventInput
import org.nephtys.loom.generic.protocol.InternalStructures.{Email, EndpointRoot, FailableList, ID}
import org.nephtys.loom.generic.protocol.{Backend, Protocol}
import upickle.default._

import scala.scalajs.js.annotation.JSExportAll
import scala.util.{Failure, Success, Try}

/**
  * Created by nephtys on 12/7/16.
  */
@JSExportAll
object ZSolarProtocol extends Protocol[Solar] with Backend[Solar] {
  type Id = ID[Solar]
  override val endpointRoot: EndpointRoot = EndpointRoot("vanillasolar")

  sealed trait SolarCommand extends Command {
  }

  sealed trait SolarEvent extends Event

  case class Create(owner : Email, id : ID[Solar]) extends SolarCommand {

    override def insert: Boolean = true

    override def checkCreatorIsAuthor(requester: Email): Boolean = owner.equals(requester)

    override protected def validateInternal(input: EventInput): Try[ZSolarProtocol.Event] = Success(Creation(owner, id))
  }
  case class Creation(owner : Email, id : ID[Solar]) extends SolarEvent {

    override def insert: Boolean = true

    override def createNew: Solar = Characters.emptySolar(id, owner)

    override def commitInternal(old: EventInput): Solar = throw new UnsupportedOperationException
  }


  case class Delete(id : ID[Solar]) extends SolarCommand {
    override def remove: Boolean = true

    override protected def validateInternal(input: EventInput): Try[ZSolarProtocol.Event] = Success(Deletion(id))
  }
  case class Deletion(id : ID[Solar]) extends SolarEvent {
    override def remove: Boolean = true
    override def changesAfterRemoval(aggregates: Aggregates) : Aggregates = aggregates

    override def commitInternal(old: EventInput): Solar = throw new UnsupportedOperationException
  }


  case class SetName(id : Id, name : String) extends SolarCommand {
    override protected def validateInternal(input: EventInput): Try[ZSolarProtocol.Event] = {
      println("Checking SetName Command internally")
      Success(NameChanged(id, name))
    }
  }
  case class NameChanged(id : Id, name : String) extends SolarEvent {
    override def commitInternal(old: EventInput): Solar = {
      println("Commiting SetName Event internally")
      val metaDescriptors = old.get[Solar].metaDescriptors.copy(name = name)
      old.get[Solar].copy(metaDescriptors = metaDescriptors)
    }
  }

  case class SetWillpower(id : Id, dots : Int) extends SolarCommand {
    override protected def validateInternal(input: EventInput): Try[ZSolarProtocol.Event] = {
      val solar : Solar = input.get
      val dif = dots - solar.willpowerDots
      if (dots < 5 || dots > 10 || (!solar.stillInCharGen && dif <= 0)) {
        Failure(new IllegalArgumentException)
      } else if ((solar.stillInCharGen && (solar.bonusPointsUnspent >= dif * 2)) || (!solar.stillInCharGen && (solar.experience.pointsLeftToSpend(false) >= dif * 8))) {
        Success(WillpowerChanged(id, dots))
      } else {
        Failure(Exceptions.missXP())
      }
    }
  }

  case class WillpowerChanged(id : Id, dots : Int) extends SolarEvent {
    override def commitInternal(input: EventInput): Solar = {
      val solar : Solar = input.get
      val dif = dots - solar.willpowerDots
      if (solar.stillInCharGen) {
        //if in chargen, change dot rating and bonuspointsunspent
        val bpcost = dif * 2
        solar.copy(willpowerDots = dots, bonusPointsUnspent = solar.bonusPointsUnspent - bpcost)
      } else {
        //if not in chargen, spend XP and change dot rating
        val xpcost = dif * 8
        solar.copy(willpowerDots = dots, experience = solar.experience.spendAmount(xpcost, solarCharm = false))
      }
    }
  }

  case class SetCaste(id : Id, caste : Caste) extends SolarCommand {
    override protected def validateInternal(input: EventInput): Try[ZSolarProtocol.Event] = if(input.get[Solar].stillInCharGen) Success(CasteChanged(id, caste)) else Failure(new Exception("Caste can only be changed during CharGen"))
  }

  case class CasteChanged(id : Id, caste : Caste) extends SolarEvent {
    override def commitInternal(old: EventInput): Solar = old.get[Solar].copy(caste = Some(caste))
  }

  case class AddManualExperienceChange(id : Id, amountAdded : Int, typ : ExperienceType, note : String) extends SolarCommand {
    override protected def validateInternal(input: EventInput): Try[ZSolarProtocol.Event] = {
      input.get[Solar].experience.addManualEntry(amountAdded, typ, note, System.currentTimeMillis()).map(t => ExperienceChangedManually(id, amountAdded, typ, note, System.currentTimeMillis()))
    }
  }

  case class ExperienceChangedManually(id : Id, amountAdded : Int, typ : ExperienceType, note : String, timestamp : Long) extends SolarEvent {
    override def commitInternal(input: EventInput): Solar = {
      val s : Solar = input.get
      s.copy(experience = s.experience.addManualEntry(amountAdded, typ, note, timestamp).get)
    }
  }


  case class SetAnima(id : Id, anima : String) extends SolarCommand {
    override protected def validateInternal(input: EventInput): Try[ZSolarProtocol.Event] = Success(AnimaChanged(id, anima))
  }

  case class AnimaChanged(id : Id, anima : String) extends SolarEvent {
    override def commitInternal(old: EventInput): Solar = {

      val metaDescriptors = old.get[Solar].metaDescriptors.copy(anima = anima)
      old.get[Solar].copy(metaDescriptors = metaDescriptors)
    }
  }

  case class SetConcept(id : Id, concept : String) extends SolarCommand {
    override protected def validateInternal(input: EventInput): Try[ZSolarProtocol.Event] = Success(ConceptChanged(id, concept))
  }

  case class ConceptChanged(id : Id, concept : String) extends SolarEvent {
    override def commitInternal(old: EventInput): Solar = {

      val metaDescriptors = old.get[Solar].metaDescriptors.copy(concept = concept)
      old.get[Solar].copy(metaDescriptors = metaDescriptors)
    }
  }

  case class SetLimitTrigger(id : Id, limitTrigger : String) extends SolarCommand {
    override protected def validateInternal(input: EventInput): Try[ZSolarProtocol.Event] = Success(LimitTriggerChanged(id, limitTrigger))
  }

  case class LimitTriggerChanged(id : Id, limitTrigger : String) extends SolarEvent {
    override def commitInternal(old: EventInput): Solar = {

      val metaDescriptors = old.get[Solar].metaDescriptors.copy(limitTrigger = limitTrigger)
      old.get[Solar].copy(metaDescriptors = metaDescriptors)
    }
  }

  case class SetPlayer(id : Id, player : String) extends SolarCommand {
    override protected def validateInternal(input: EventInput): Try[ZSolarProtocol.Event] = Success(PlayerChanged(id, player))
  }

  case class PlayerChanged(id : Id, player : String) extends SolarEvent {
    override def commitInternal(old: EventInput): Solar = {
      val metaDescriptors = old.get[Solar].metaDescriptors.copy(player = player)
      old.get[Solar].copy(metaDescriptors = metaDescriptors)
    }
  }

  case class LeaveCharacterGeneration(id : Id) extends SolarCommand {
    override protected def validateInternal(input: EventInput): Try[ZSolarProtocol.Event] = Success(CharacterGenerationLeft(id))
  }

  case class CharacterGenerationLeft(id : Id) extends SolarEvent {
    override def commitInternal(old: EventInput): Solar = old.get[Solar].copy(stillInCharGen = false)
  }

  case class PurchaseCustomCharm(id : Id, charm : CustomPower) extends SolarCommand {
    //TODO: check essence rating and/or circle!
    override protected def validateInternal(input: EventInput): Try[ZSolarProtocol.Event] = {
      val solar : Solar = input.get
      charm.customCost match {
        case BonusPointCost(cost) => if (solar.bonusPointsUnspent >= cost) Success(CustomCharmPurchased(id, charm)) else Failure(Exceptions.missBP())
        case ExperiencePointCost(costGeneral, useSolarXP) => if(solar.experience.pointsLeftToSpend(useSolarXP) >= costGeneral) Success(CustomCharmPurchased(id, charm)) else Failure(Exceptions.missXP())
        case FreePointCost(cost) => if (solar.countCharmPurchases < 15) Success(CustomCharmPurchased(id, charm)) else Failure(Exceptions.missFP())
      }
    }
  }

  case class CustomCharmPurchased(id : Id, charm : CustomPower) extends SolarEvent {
    override def commitInternal(input: EventInput): Solar = {
      val solar : Solar = input.get

      charm.customCost match {
        case BonusPointCost(cost) => {
          solar.copy(customCharms = solar.customCharms.+:(charm), bonusPointsUnspent = solar.bonusPointsUnspent - cost)
        }
        case ExperiencePointCost(costGeneral, useSolarXP) => {
          solar.copy(customCharms = solar.customCharms.+:(charm), experience = solar.experience.spendAmount(costGeneral, useSolarXP))
        }
        case FreePointCost(cost) => {
          solar.copy(customCharms = solar.customCharms.+:(charm))
        }
      }
    }
  }

  case class PurchaseListCharm(id : Id, charmIndex : Int) extends SolarCommand {
    //TODO: check essence rating and/or circle!
    override protected def validateInternal(input: EventInput): Try[ZSolarProtocol.Event] = {
      val solar : Solar = input.get
      //check charmindex legal

      //TODO: first check if the solar can even purchase this charm

      if (charmIndex >= Powers.powers.size ||charmIndex < 0 || solar.listedPowers.contains(charmIndex)) {
        Failure(new IllegalArgumentException)
        //check if CG
      } else if (solar.stillInCharGen) {
        val t = Powers.powers(charmIndex)
        val ability : Option[String] = if (t.isInstanceOf[SolarCharm]) Some(t.asInstanceOf[SolarCharm].abilityString) else if (t.isInstanceOf[Spell]) Some(Abilities.preprogrammed.Occult) else None
        val bpCostOne : Int = if (t.isInstanceOf[EvocationCharm] || ability.exists(a => solar.reducedCost(a))) {4} else {5}
        //if CG, check free dots and bonus point value
        solar.countCharmPurchases
        if (solar.countCharmPurchases < 15 || solar.bonusPointsUnspent >= bpCostOne) {
          Success(ListedCharmPurchased(id, charmIndex))
        } else {
          Failure(Exceptions.missBP())
        }
      } else {
        //if not, check experience
        val t = Powers.powers(charmIndex)
        val isSolarcharm : Boolean = t.isInstanceOf[SolarCharm]
        val ability : Option[String] = if (t.isInstanceOf[SolarCharm]) Some(t.asInstanceOf[SolarCharm].abilityString) else if (t.isInstanceOf[Spell]) Some(Abilities.preprogrammed.Occult) else if (t.isInstanceOf[MartialArtsCharm]) Some(Abilities.preprogrammed.BrawlMartialArtsComboLabel) else None
        val xpCostOne : Int = if (ability.exists(a => solar.reducedCost(a)) ) {8} else {10}

        if (xpCostOne <= solar.experience.pointsLeftToSpend(isSolarcharm)) {
            Success(ListedCharmPurchased(id, charmIndex))
        } else {
          Failure(Exceptions.missXP())
        }
      }
    }
  }

  case class ListedCharmPurchased(id : Id, charmIndex : Int) extends SolarEvent { //TODO: store real case object instead here? May be more efficient and would be evolution secure
    override def commitInternal(input: EventInput): Solar = {

      val solar : Solar = input.get
      val t = Powers.powers(charmIndex)

      val abilityBP : Option[String] = if (t.isInstanceOf[SolarCharm]) Some(t.asInstanceOf[SolarCharm].abilityString) else if (t.isInstanceOf[Spell]) Some(Abilities.preprogrammed.Occult) else None
      val bpCostOne : Int = if (t.isInstanceOf[EvocationCharm] || abilityBP.exists(a => solar.reducedCost(a))) {4} else {5}
      val abilityXP : Option[String] = if (t.isInstanceOf[SolarCharm]) Some(t.asInstanceOf[SolarCharm].abilityString) else if (t.isInstanceOf[Spell]) Some(Abilities.preprogrammed.Occult) else if (t.isInstanceOf[MartialArtsCharm]) Some(Abilities.preprogrammed.BrawlMartialArtsComboLabel) else None
      val xpCostOne : Int = if (abilityXP.exists(a => solar.reducedCost(a)) ) {8} else {10}

      val isSolarcharm : Boolean = t.isInstanceOf[SolarCharm]

      val newXPBox : ExperienceBox = if (solar.stillInCharGen) solar.experience else  solar.experience.spendAmount(xpCostOne, isSolarcharm)
      val newBPValue : Int = solar.bonusPointsUnspent - (if (solar.stillInCharGen && solar.countCharmPurchases >= 15) bpCostOne else 0)
      val newCharms : Set[Int] = solar.listedPowers + charmIndex

      solar.copy(experience = newXPBox, bonusPointsUnspent = newBPValue, listedPowers = newCharms)
    }
  }

  case class SetOwner(id : Id, owner : Email) extends SolarCommand {
    override protected def validateInternal(input: EventInput): Try[ZSolarProtocol.Event] = Success(OwnerChanged(id, owner))
  }

  case class OwnerChanged(id : Id, owner : Email) extends SolarEvent {
    override def commitInternal(old: EventInput): Solar = old.get[Solar].copy(metaInfo = old.get[Solar].metaInfo.copy( owner = owner))
  }

  case class SetReaders(id : Id, readers : Set[Email]) extends SolarCommand {
    override protected def validateInternal(input: EventInput): Try[ZSolarProtocol.Event] = Success(ReadersChanged(id, readers))
  }

  case class ReadersChanged(id : Id, readers : Set[Email]) extends SolarEvent {
    override def commitInternal(old: EventInput): Solar = old.get[Solar].copy(metaInfo = old.get[Solar].metaInfo.copy( readers = readers))
  }

  case class SetPublic(id : Id, public : Boolean) extends SolarCommand {
    override protected def validateInternal(input: EventInput): Try[ZSolarProtocol.Event] = Success(MadePublic(id, public))
  }

  case class MadePublic(id : Id, public : Boolean) extends SolarEvent {
    override def commitInternal(old: EventInput): Solar = old.get[Solar].copy(metaInfo = old.get[Solar].metaInfo.copy( public = public))
  }


  case class SetAbilityRating(id : Id, ability : Ability, rating : Int) extends SolarCommand {
    override protected def validateInternal(input: EventInput): Try[ZSolarProtocol.Event] = {
      val solar : Solar = input.get
      val dif : Int = rating - solar.abilities.ratings(ability).number
      if (solar.abilities.rateables.contains(ability.name) && rating >= 0 && rating <= 5) {
        val (bpcost, xpcost, _) = solar.abilities.setRating(ability, rating)
        if (solar.stillInCharGen) {
          if (bpcost <= solar.bonusPointsUnspent) {
            Success(AbilityRatingChanged(id, ability, rating))
          } else {
            Failure(Exceptions.missBP())
          }
        } else {
          if (dif > 0 && xpcost <= solar.experience.pointsLeftToSpend(false)) {
            Success(AbilityRatingChanged(id, ability, rating))
          } else {
            Failure(Exceptions.missXP())
          }
        }
      } else {
        Failure(new IllegalArgumentException)
      }

    }
  }

  case class AbilityRatingChanged(id : Id, ability : Ability, rating : Int) extends SolarEvent {
    override def commitInternal(input: EventInput): Solar = {
      val solar : Solar = input.get
      val (bpcost, xpcost, newability) = solar.abilities.setRating(ability, rating)
      if (solar.stillInCharGen) {
        solar.copy(abilities = newability, bonusPointsUnspent = solar.bonusPointsUnspent - bpcost)
      } else {
        solar.copy(abilities = newability, experience = solar.experience.spendAmount(xpcost, solarCharm = false))
      }
    }
  }

  case class AddToAbilityFamily(id : Id, familyTitle : String, title : String) extends SolarCommand {
    override protected def validateInternal(input: EventInput): Try[ZSolarProtocol.Event] = {
      val solar : Solar = input.get
      if(solar.abilities.rateables.contains(title) || !solar.abilities.families.contains(familyTitle)) {
        println("AddToAbilityFamily failed")
        Failure(new IllegalArgumentException)
      } else {
        println("AddToAbilityFamily succeeded")
        Success(AbilityFamilyAdded(id, familyTitle, title))
      }
    }
  }

  case class AbilityFamilyAdded(id : Id, familyTitle : String, title : String) extends SolarEvent {
    override def commitInternal(input: EventInput): Solar = {
      val solar : Solar = input.get
      val s = solar.copy(abilities = solar.abilities.addSubability(familyTitle, title))
      println("AbilityFamilyAdded returned!")
      s
    }
  }

  case class AbilityFamilyRemoved(id : Id, familyTitle : String, title : String) extends SolarEvent {
    override def commitInternal(input: EventInput): Solar = {
      val solar : Solar = input.get
      solar.copy(abilities = solar.abilities.removeSubability(familyTitle, title))
    }
  }

  case class RemoveFromAbilityFamily(id : Id, familyTitle : String, title : String) extends SolarCommand {
    override protected def validateInternal(input: EventInput): Try[ZSolarProtocol.Event] = {
      val solar : Solar = input.get
      if(solar.abilities.getSubability(familyTitle, title).forall(a => solar.abilities.ratings(a).number == 0)) {
        Failure(new IllegalArgumentException)
      } else {
        Success(AbilityFamilyRemoved(id, familyTitle, title))
      }
    }
  }

  case class SetAbilityType(id : Id, typeableTitle : String, typ : Abilities.Type) extends SolarCommand {
    override protected def validateInternal(input: EventInput): Try[ZSolarProtocol.Event] = {
      val solar : Solar = input.get
      if (solar.abilities.typeables.contains(typeableTitle) && solar.stillInCharGen) {
        if (typ match {
          case Supernal => solar.abilities.numberOfSupernals < 1
          case Favored => solar.abilities.numberOfFavoreds < 5
          case Caste => solar.abilities.numberOfCastes < 4
          case _ => true
        }) {
          Success(AbilityTypeChanged(id, typeableTitle, typ))
        } else {
          Failure(new Exception("Illegal number of Ability Types"))
        }
      } else {
        Failure(new IllegalArgumentException)
      }
    }
  }

  case class AbilityTypeChanged(id : Id, typeableTitle : String, typ : Abilities.Type) extends SolarEvent {
    override def commitInternal(input: EventInput): Solar = {
      val solar : Solar = input.get
      val c = solar.abilities.setType(solar.abilities.parseTypeable(typeableTitle), typ)
      solar.copy(abilities = c._2, bonusPointsUnspent = solar.bonusPointsUnspent - c._1)
    }
  }

  case class AddSpecialty(id : Id, specialtyAble: AbilityLikeSpecialtyAble, title : String) extends SolarCommand {
    override protected def validateInternal(input: EventInput): Try[ZSolarProtocol.Event] = {
      val solar : Solar = input.get
      val xpcost : Int = 3
      val bpcost : Int = 1
      if (( (solar.stillInCharGen && (solar.abilities.numberOfSpecialties < xpcost || solar.bonusPointsUnspent >= bpcost)) || (!solar.stillInCharGen && solar.experience.pointsLeftToSpend(false) >= 3) ) && solar.abilities.abilities.contains(specialtyAble) && !solar.abilities.specialties.get(specialtyAble).exists(_.exists(_.name == title))) {
        println("Succeed AddSpecialty")
        Success(SpecialtyAdded(id, specialtyAble, title))
      } else {
        println("Failed AddSpecialty")
        Failure(new IllegalArgumentException)
      }
    }
  }

  case class RemoveSpecialty(id : Id, specialtyAble: AbilityLikeSpecialtyAble, title : String) extends SolarCommand {
    override protected def validateInternal(input: EventInput): Try[ZSolarProtocol.Event] = {
      val solar : Solar = input.get
      if (solar.stillInCharGen && solar.abilities.abilities.contains(specialtyAble) && solar.abilities.specialties.get(specialtyAble).exists(_.exists(_.name == title))) {
        Success(SpecialtyRemoved(id, specialtyAble, title))
      } else {
        Failure(new IllegalArgumentException)
      }
    }
  }

  case class SpecialtyRemoved(id : Id, specialtyAble: AbilityLikeSpecialtyAble, title : String) extends SolarEvent {
    override def commitInternal(input: EventInput): Solar = {
      val solar : Solar = input.get
      val xpcost : Int = -3
      val bpcost : Int = if (solar.abilities.numberOfSpecialties <= 4) 0 else -1
      if (solar.stillInCharGen) {
        solar.copy(abilities = solar.abilities.removeSpecialty(specialtyAble, title), bonusPointsUnspent = solar.bonusPointsUnspent - bpcost)
      } else {
        solar.copy(abilities = solar.abilities.removeSpecialty(specialtyAble, title), experience = solar.experience.spendAmount(xpcost, solarCharm = false))
      }
    }
  }

  case class SpecialtyAdded(id : Id, specialtyAble: AbilityLikeSpecialtyAble, title : String) extends SolarEvent {
    override def commitInternal(input: EventInput): Solar = {
      val solar : Solar = input.get
      val xpcost : Int = 3
      val bpcost : Int = if (solar.abilities.numberOfSpecialties < 4) 0 else 1
      if (solar.stillInCharGen) {
        solar.copy(abilities = solar.abilities.addSpecialty(specialtyAble, title), bonusPointsUnspent = solar.bonusPointsUnspent - bpcost)
      } else {
        solar.copy(abilities = solar.abilities.addSpecialty(specialtyAble, title), experience = solar.experience.spendAmount(xpcost, solarCharm = false))
      }
    }
  }

  //includes delete
  case class SetIntimacy(id : Id, title : String, intensity : Option[Intimacies.Intensity]) extends SolarCommand {
    override protected def validateInternal(input: EventInput): Try[ZSolarProtocol.Event] = Success(IntimacyChanged(id, title, intensity))
  }

  case class IntimacyChanged(id : Id, title : String, intensity: Option[Intensity]) extends SolarEvent {
    override def commitInternal(old: EventInput): Solar = {
    val s = old.get[Solar]
    if (intensity.isDefined) {
      s.copy(intimacies = s.intimacies.+((title, intensity.get)))
    } else {
      s.copy(intimacies = s.intimacies.-(title))
    }
  }
  }

  case class AddMerit(id : Id, title : String, category : String) extends SolarCommand {
    override protected def validateInternal(input: EventInput): Try[ZSolarProtocol.Event] = Success(MeritAdded(id, title, category))
  }

  case class MeritAdded(id : Id, title : String, category: String) extends SolarEvent {
    override def commitInternal(old: EventInput): Solar = old.get[Solar].copy(merits = old.get[Solar].merits.:+(Merits.Merit(title, Dots(0), Merits.parseCategory(category))))
  }

  case class ChangeMerit(id : Id, index : Int, newrating : Option[Int]) extends SolarCommand {
    override protected def validateInternal(input: EventInput): Try[ZSolarProtocol.Event] = {
      val solar : Solar = input.get[Solar]
      if (newrating.forall(a => 0 <= a && a <= 5) && index >= 0 && solar.merits.size > index) {
        println("Passing newrating check")
        if (newrating.isEmpty) {
          if (solar.merits(index).rating.number == 0 || solar.stillInCharGen) {
            println("Passing inner empty check")
            Success(MeritChanged(id, index, newrating))
          } else {
            println("Failing inner empty check")
            Failure(new Exception("Cannot remove non-empty merits after chargen"))
          }
        } else {
          val dif: Int = newrating.get - solar.merits(index).rating.number
          if (solar.stillInCharGen) {
            val dif: Int = newrating.get - solar.merits(index).rating.number

            val oldSum = solar.merits.map(_.rating.number).sum
            val newSum: Int = oldSum + dif

            //only 10 free points or via bonus points (1 bp per dot) or with split at 10

            val oldMeritBP: Int = oldSum - FreeMeritPoints
            val newMeritBP: Int = newSum - FreeMeritPoints

            val belowLimit: Int = newMeritBP - oldMeritBP
            val aboveLimit: Int = dif - belowLimit

            if (oldSum + dif <= FreeMeritPoints || aboveLimit <= solar.bonusPointsUnspent) {
              Success(MeritChanged(id, index, newrating))
            } else {
              Failure(Exceptions.missBP())
            }
          } else {
            //find rating-dif and calculate XP cost (3 per dot!)
            if (dif > 0 && solar.experience.pointsLeftToSpend(false) >= (dif * 3)) {
              Success(MeritChanged(id, index, newrating))
            } else {
              Failure(Exceptions.missXP())
            }
          }
        }
      } else {
        println("IllegalArgumentException in ChangeMerit")
        Failure(new IllegalArgumentException())
      }
    }
  }

  val FreeMeritPoints : Int = 10

  case class MeritChanged(id : Id, index : Int, newrating : Option[Int]) extends SolarEvent {
    override def commitInternal(input: EventInput): Solar = {
      val solar : Solar = input.get[Solar]
      if (solar.stillInCharGen) {
        //allow split of free points vs bonus points
        //create new list
        val merits : List[Merit] = if (newrating.isDefined) {
          val merit: Merit = solar.merits(index).copy(rating = Dots(newrating.getOrElse(0)))
          solar.merits.updated(index, merit)
        } else { solar.merits.take(index) ++ solar.merits.drop(index+1) }

        val overlimitbefore: Int = solar.merits.map(_.rating.number).sum - FreeMeritPoints
        val overlimitnow: Int = merits.map(_.rating.number).sum - FreeMeritPoints

        if (overlimitbefore >= 0) {
          if (overlimitnow >= 0) {
            //use full bonus points
            val dif = overlimitnow - overlimitbefore
            solar.copy(bonusPointsUnspent = solar.bonusPointsUnspent - dif, merits = merits)
          } else {
            //don't use bonus points, instead give them back
            solar.copy(bonusPointsUnspent = solar.bonusPointsUnspent + overlimitbefore, merits = merits)
          }
        } else {
          if (overlimitnow >= 0) {
            //take partial bonus points
            solar.copy(bonusPointsUnspent = solar.bonusPointsUnspent - overlimitnow, merits = merits)
          } else {
            //don't use bonus points
            solar.copy(merits = merits)
          }
        }
      } else {
        //add merit, decrease current xp, potential delete
        val dif = newrating.getOrElse(0) - solar.merits(index).rating.number
        val xp: Experiences.ExperienceBox = solar.experience.spendAmount(dif * 3, solarCharm = false)
        val merits: List[Merit] = if (newrating.isEmpty) {
          solar.merits.take(index) ++ solar.merits.drop(index+1)
        } else {
          solar.merits.updated(index, solar.merits(index).copy(rating = Dots(newrating.getOrElse(0))))
        }
        solar.copy(experience = xp, merits = merits)
      }
    }
  }

  case class AddNote(id : Id, str : String, index : Int ) extends SolarCommand {
    override protected def validateInternal(input: EventInput): Try[ZSolarProtocol.Event] = {
      if (input.get[Solar].notes.length >= index && index >= 0 ) {
        Success(NoteAdded(id, str, index))
      } else {
        Failure(new IndexOutOfBoundsException)
      }
    }
  }
  case class NoteAdded(id : Id, str : String, index : Int) extends SolarEvent {
    override def commitInternal(old: EventInput): Solar = {
      val oldlist : List[String] = old.get[Solar].notes
      val newlist : List[String] = oldlist.take(index).:+(str) ++ oldlist.drop(index)
      old.get[Solar].copy(notes = newlist)
    }
  }
  case class RemoveNote(id : Id, index : Int ) extends SolarCommand {
    override protected def validateInternal(input: EventInput): Try[ZSolarProtocol.Event] = {
      if (input.get[Solar].notes.length > index && index >= 0 ) {
        Success(NoteRemoved(id, index))
      } else {
        Failure(new IndexOutOfBoundsException)
      }
    }
  }
  case class NoteRemoved(id : Id, index : Int) extends SolarEvent {
    override def commitInternal(old: EventInput): Solar = {
      val oldlist : List[String] = old.get[Solar].notes
      val newlist : List[String] = oldlist.take(index) ++ oldlist.drop(index+1)
      old.get[Solar].copy(notes = newlist)
    }
  }

  case class SetAttribute(id : Id, attributeIndex : Int, rating : Int) extends SolarCommand {
    override protected def validateInternal(input: EventInput): Try[ZSolarProtocol.Event] = {
      val solar : Solar = input.get[Solar]
      //are rating and attribute in range?
      if (attributeIndex < 0 || attributeIndex >= 9 || rating < 1 || rating > 5) {
        println(s"Failing $this with IllegalArgumentException")
        Failure(new IllegalArgumentException)
      } else if (solar.stillInCharGen) {
        //do we have either 8/6/4 free points above 1 or 4/3 bonus points per dif dots or (current rating x4 XP)


        //TODO: weight ternary attributes with only 3 BP

        val beforeBonusPointsInThat : Int = solar.attributes.bonusPointsInvested
        val afterBonusPointsInThat : Int = solar.attributes.copyWithChange(attributeIndex, rating).bonusPointsInvested

        if (afterBonusPointsInThat - beforeBonusPointsInThat <= solar.bonusPointsUnspent) {
          println(s"Succeeding $this with AttributeChanged")
          Success(AttributeChanged(id, attributeIndex, rating))
        } else {
          println(s"Failing $this with missBP Exception")
          Failure(Exceptions.missBP())
        }
      } else {
        //after chargen use xp:
        val oldValue : AttributeRating = solar.attributes.block(attributeIndex)
        val newValue : AttributeRating = AttributeRating(oldValue.attribute, Dots(rating))
        val oldXP : Int = oldValue.xpValue
        val newXP  : Int = newValue.xpValue
        //val difDots : Int = rating - solar.attributes.block(0).dots.number
        //val difXp : Int = difDots * 4
        val xpDif = newXP - oldXP
        if (oldValue.dots.number < rating && solar.experience.pointsLeftToSpend(false) >= xpDif) {
          println(s"Succeeding $this with AttributeChanged")
          Success(AttributeChanged(id, attributeIndex, rating))
        } else {
          println(s"Failing $this with missXP Exception")
          Failure(Exceptions.missXP())
        }

      }
    }
  }

  case class AttributeChanged(id : Id, attributeIndex : Int, rating : Int) extends SolarEvent {
    override def commitInternal(old: EventInput): Solar = {
      val solar : Solar = old.get[Solar]
      if (solar.stillInCharGen) {
        val newBlock : AttributeBlock = solar.attributes.copyWithChange(attributeIndex, rating).copyWithOrdering
        val bpBefore : Int = solar.attributes.bonusPointsInvested
        val bpNow : Int = newBlock.bonusPointsInvested
        val bpNeededToSpend : Int = bpNow - bpBefore

        solar.copy(bonusPointsUnspent = solar.bonusPointsUnspent - bpNeededToSpend, attributes = newBlock)
      } else {
        val newBlock : AttributeBlock = solar.attributes.copyWithChange(attributeIndex, rating).copyWithOrdering
        val xpCost : Int = newBlock.block(attributeIndex).xpValue - solar.attributes.block(attributeIndex).xpValue

        solar.copy(attributes = newBlock, experience = solar.experience.spendAmount(xpCost, solarCharm = false))
      }

    }
  }


  //does not deal with specialties
  def diff(id : Id, a : Abilities.AbilityMatrix, b : Abilities.AbilityMatrix) : Seq[SolarCommand] = {
    //this includes new ability family changes and removing old ones changes
    val changingOldVsAbilities : Seq[SolarCommand] = {
      println("diff up to here")
      val s1 : Seq[AbilityLikeSpecialtyAble] = a.abilities.toSeq.sortBy(_.name)
      val s2 : Seq[AbilityLikeSpecialtyAble] = b.abilities.toSeq.sortBy(_.name)

      val oldToNewFamily : Seq[(AbilityFamily, AbilityFamily)] = s1.indices.filter(i => !s1(i).equals(s2(i))).map(i => (s1(i).asInstanceOf[AbilityFamily], s2(i).asInstanceOf[AbilityFamily]))

      val adds : Seq[AddToAbilityFamily] = oldToNewFamily.flatMap(f => f._2.instances.filterNot(c => f._1.instances.exists(_.equals(c))).map(c => (f._1, c))).map(a => AddToAbilityFamily(id, familyTitle = a._1.familityName, a._2.name))

      val removes : Seq[RemoveFromAbilityFamily] = oldToNewFamily.flatMap(f => f._1.instances.filterNot(c => f._2.instances.exists(_.equals(c))).map(c => (f._1, c))).map(a => RemoveFromAbilityFamily(id, familyTitle = a._1.familityName, a._2.name))

      println("diff up to there")

      removes ++ adds
    }
    //type changes
    val typeChanges : Seq[SetAbilityType] = a.types.keys.filter(k => !a.types.get(k).equals(b.types.get(k))).toSeq.map(k => SetAbilityType(id, k.typeabletitle , b.types.getOrElse(k, Normal) ))


    println("typeChanges also")
    //rating changes
    val ratingChanges : Seq[SetAbilityRating] = b.ratings.keys.filter(k => b.ratings.get(k) != a.ratings.get(k)).toSeq.map(k => SetAbilityRating(id, k, b.ratings.get(k).map(_.number).getOrElse(0)))
    println(s"B = $b")


    val all = changingOldVsAbilities ++ typeChanges ++ ratingChanges

    println(s"All the ability changes : $all")

    all

  }

  def diff(id : Id, a : Attributes.AttributeBlock, b : Attributes.AttributeBlock) : Seq[SolarCommand] = {
    println(s"diffing attributes $a vs $b")
    a.block.indices.filter(i => a.block(i).dots.number != b.block(i).dots.number).map(i => {
      SetAttribute(id, i, b.block(i).dots.number)
    })
  }



  override def readCommands(json: String): Seq[ZSolarProtocol.Command] = read[Seq[SolarCommand]](json)

  override def writeAggregates(aggregates: Seq[Solar]): String = write(aggregates)

  override def writeFailableList(failableList: FailableList[ZSolarProtocol.Event]): String = write(failableList.asInstanceOf[FailableList[SolarEvent]])
}
