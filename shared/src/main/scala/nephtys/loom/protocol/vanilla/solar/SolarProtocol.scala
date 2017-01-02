package nephtys.loom.protocol.vanilla.solar

import nephtys.loom.protocol.shared.CharmRef
import nephtys.loom.protocol.vanilla.solar.Abilities.SpecialtyAble
import nephtys.loom.protocol.vanilla.solar.Intimacies.Intensity
import nephtys.loom.protocol.vanilla.solar.Merits.{Category, Merit}
import nephtys.loom.protocol.vanilla.solar.Misc.{Caste, Dots}
import org.nephtys.loom.generic.protocol.EventInput.{EventInput, Update}
import org.nephtys.loom.generic.protocol.InternalStructures.{Email, EndpointRoot, FailableList, ID}
import org.nephtys.loom.generic.protocol.{Backend, Protocol}
import upickle.default._

import scala.scalajs.js.annotation.JSExportAll
import scala.util.{Failure, Success, Try}

/**
  * Created by nephtys on 12/7/16.
  */
@JSExportAll
object SolarProtocol extends Protocol[Solar] with Backend[Solar] {
  type Id = ID[Solar]
  override val endpointRoot: EndpointRoot = EndpointRoot("vanillasolar")

  sealed trait SolarCommand extends Command {
  }

  sealed trait SolarEvent extends Event

  case class Create(owner : Email, id : ID[Solar]) extends SolarCommand {

    override def insert: Boolean = true

    override def checkCreatorIsAuthor(requester: Email): Boolean = owner.equals(requester)

    override protected def validateInternal(input: EventInput): Try[_root_.nephtys.loom.protocol.vanilla.solar.SolarProtocol.Event] = Success(Creation(owner, id))
  }
  case class Creation(owner : Email, id : ID[Solar]) extends SolarEvent {

    override def insert: Boolean = true

    override def createNew: Solar = Characters.emptySolar(id, owner)

    override def commitInternal(old: EventInput): Solar = throw new UnsupportedOperationException
  }


  case class Delete(id : ID[Solar]) extends SolarCommand {
    override def remove: Boolean = true

    override protected def validateInternal(input: EventInput): Try[_root_.nephtys.loom.protocol.vanilla.solar.SolarProtocol.Event] = Success(Deletion(id))
  }
  case class Deletion(id : ID[Solar]) extends SolarEvent {
    override def remove: Boolean = true
    override def changesAfterRemoval(aggregates: Aggregates) : Aggregates = aggregates

    override def commitInternal(old: EventInput): Solar = throw new UnsupportedOperationException
  }


  case class SetName(id : Id, name : String) extends SolarCommand {
    override protected def validateInternal(input: EventInput): Try[_root_.nephtys.loom.protocol.vanilla.solar.SolarProtocol.Event] = {
      println("Checking SetName Command internally")
      Success(NameChanged(id, name))
    }
  }
  case class NameChanged(id : Id, name : String) extends SolarEvent {
    override def commitInternal(old: EventInput): Solar = {
      println("Commiting SetName Event internally")
      old.get[Solar].copy(name = name)
    }
  }

  case class SetWillpower(id : Id, dots : Int) extends SolarCommand {
    override protected def validateInternal(input: EventInput): Try[_root_.nephtys.loom.protocol.vanilla.solar.SolarProtocol.Event] = ???
  }

  case class SetCaste(id : Id, caste : Caste) extends SolarCommand {
    override protected def validateInternal(input: EventInput): Try[_root_.nephtys.loom.protocol.vanilla.solar.SolarProtocol.Event] = if(input.get[Solar].stillInCharGen) Success(CasteChanged(id, caste)) else Failure(new Exception("Caste can only be changed during CharGen"))
  }

  case class CasteChanged(id : Id, caste : Caste) extends SolarEvent {
    override def commitInternal(old: EventInput): Solar = old.get[Solar].copy(caste = Some(caste))
  }

  case class SetAnima(id : Id, anima : String) extends SolarCommand {
    override protected def validateInternal(input: EventInput): Try[_root_.nephtys.loom.protocol.vanilla.solar.SolarProtocol.Event] = Success(AnimaChanged(id, anima))
  }

  case class AnimaChanged(id : Id, anima : String) extends SolarEvent {
    override def commitInternal(old: EventInput): Solar = old.get[Solar].copy(anima = anima)
  }

  case class SetConcept(id : Id, concept : String) extends SolarCommand {
    override protected def validateInternal(input: EventInput): Try[_root_.nephtys.loom.protocol.vanilla.solar.SolarProtocol.Event] = Success(ConceptChanged(id, concept))
  }

  case class ConceptChanged(id : Id, concept : String) extends SolarEvent {
    override def commitInternal(old: EventInput): Solar = old.get[Solar].copy(concept = concept)
  }

  case class SetLimitTrigger(id : Id, limitTrigger : String) extends SolarCommand {
    override protected def validateInternal(input: EventInput): Try[_root_.nephtys.loom.protocol.vanilla.solar.SolarProtocol.Event] = Success(LimitTriggerChanged(id, limitTrigger))
  }

  case class LimitTriggerChanged(id : Id, limitTrigger : String) extends SolarEvent {
    override def commitInternal(old: EventInput): Solar = old.get[Solar].copy(limitTrigger = limitTrigger)
  }

  case class SetPlayer(id : Id, player : String) extends SolarCommand {
    override protected def validateInternal(input: EventInput): Try[_root_.nephtys.loom.protocol.vanilla.solar.SolarProtocol.Event] = Success(PlayerChanged(id, player))
  }

  case class PlayerChanged(id : Id, player : String) extends SolarEvent {
    override def commitInternal(old: EventInput): Solar = old.get[Solar].copy(player = player)
  }

  case class LeaveCharacterGeneration(id : Id) extends SolarCommand {
    override protected def validateInternal(input: EventInput): Try[_root_.nephtys.loom.protocol.vanilla.solar.SolarProtocol.Event] = Success(CharacterGenerationLeft(id))
  }

  case class CharacterGenerationLeft(id : Id) extends SolarEvent {
    override def commitInternal(old: EventInput): Solar = old.get[Solar].copy(stillInCharGen = false)
  }

  case class PurchaseCustomCharm(id : Id) extends SolarCommand {
    //TODO: needs parameters and all
    override protected def validateInternal(input: EventInput): Try[_root_.nephtys.loom.protocol.vanilla.solar.SolarProtocol.Event] = ???
  }

  case class PurchaseListCharm(id : Id, charm : CharmRef) extends SolarCommand {
    override protected def validateInternal(input: EventInput): Try[_root_.nephtys.loom.protocol.vanilla.solar.SolarProtocol.Event] = ???
  }

  case class SetOwner(id : Id, owner : Email) extends SolarCommand {
    override protected def validateInternal(input: EventInput): Try[_root_.nephtys.loom.protocol.vanilla.solar.SolarProtocol.Event] = Success(OwnerChanged(id, owner))
  }

  case class OwnerChanged(id : Id, owner : Email) extends SolarEvent {
    override def commitInternal(old: EventInput): Solar = old.get[Solar].copy(owner = owner)
  }

  case class SetReaders(id : Id, readers : Set[Email]) extends SolarCommand {
    override protected def validateInternal(input: EventInput): Try[_root_.nephtys.loom.protocol.vanilla.solar.SolarProtocol.Event] = Success(ReadersChanged(id, readers))
  }

  case class ReadersChanged(id : Id, readers : Set[Email]) extends SolarEvent {
    override def commitInternal(old: EventInput): Solar = old.get[Solar].copy(readers = readers)
  }

  case class SetPublic(id : Id, public : Boolean) extends SolarCommand {
    override protected def validateInternal(input: EventInput): Try[_root_.nephtys.loom.protocol.vanilla.solar.SolarProtocol.Event] = Success(MadePublic(id, public))
  }

  case class MadePublic(id : Id, public : Boolean) extends SolarEvent {
    override def commitInternal(old: EventInput): Solar = old.get[Solar].copy(public = public)
  }

  case class AddSpecialty(id : Id, specialtyAble: SpecialtyAble, title : String) extends SolarCommand {
    override protected def validateInternal(input: EventInput): Try[_root_.nephtys.loom.protocol.vanilla.solar.SolarProtocol.Event] = ???
  }

  case class RemoveSpecialty(id : Id, specialtyAble: SpecialtyAble, title : String) extends SolarCommand {
    override protected def validateInternal(input: EventInput): Try[_root_.nephtys.loom.protocol.vanilla.solar.SolarProtocol.Event] = ???
  }

  //includes delete
  case class SetIntimacy(id : Id, title : String, intensity : Option[Intimacies.Intensity]) extends SolarCommand {
    override protected def validateInternal(input: EventInput): Try[_root_.nephtys.loom.protocol.vanilla.solar.SolarProtocol.Event] = Success(IntimacyChanged(id, title, intensity))
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
    override protected def validateInternal(input: EventInput): Try[_root_.nephtys.loom.protocol.vanilla.solar.SolarProtocol.Event] = Success(MeritAdded(id, title, category))
  }

  case class MeritAdded(id : Id, title : String, category: String) extends SolarEvent {
    override def commitInternal(old: EventInput): Solar = old.get[Solar].copy(merits = old.get[Solar].merits.:+(Merits.Merit(title, Dots(0), Merits.parseCategory(category))))
  }

  case class ChangeMerit(id : Id, index : Int, newrating : Option[Int]) extends SolarCommand {
    override protected def validateInternal(input: EventInput): Try[_root_.nephtys.loom.protocol.vanilla.solar.SolarProtocol.Event] = {
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
        val xp: Experiences.ExperienceBox = solar.experience.spendAmount(dif * 3, false)
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
    override protected def validateInternal(input: EventInput): Try[_root_.nephtys.loom.protocol.vanilla.solar.SolarProtocol.Event] = {
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
    override protected def validateInternal(input: EventInput): Try[_root_.nephtys.loom.protocol.vanilla.solar.SolarProtocol.Event] = {
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


  //does not deal with specialties
  def diff(a : Abilities.AbilityMatrix, b : Abilities.AbilityMatrix) : Seq[SolarCommand] = ???

  def diff(a : Attributes.AttributeBlock, b : Attributes.AttributeBlock) : Seq[SolarCommand] = ???

  def diff(a : Experiences.ExperienceBox, b : Experiences.ExperienceBox) : Seq[SolarCommand] = ???


  override def readCommands(json: String): Seq[_root_.nephtys.loom.protocol.vanilla.solar.SolarProtocol.Command] = read[Seq[SolarCommand]](json)

  override def writeAggregates(aggregates: Seq[Solar]): String = write(aggregates)

  override def writeFailableList(failableList: FailableList[_root_.nephtys.loom.protocol.vanilla.solar.SolarProtocol.Event]): String = write(failableList.asInstanceOf[FailableList[SolarEvent]])
}
