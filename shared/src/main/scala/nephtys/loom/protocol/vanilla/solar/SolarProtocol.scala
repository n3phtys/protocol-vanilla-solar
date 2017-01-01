package nephtys.loom.protocol.vanilla.solar

import nephtys.loom.protocol.shared.CharmRef
import nephtys.loom.protocol.vanilla.solar.Abilities.SpecialtyAble
import nephtys.loom.protocol.vanilla.solar.Misc.Caste
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
    override protected def validateInternal(input: EventInput): Try[_root_.nephtys.loom.protocol.vanilla.solar.SolarProtocol.Event] = ???
  }

  case class CharacterGenerationLeft(id : Id) extends SolarEvent {
    override def commitInternal(old: EventInput): Solar = ???
  }

  case class PurchaseCustomCharm(id : Id) extends SolarCommand {
    //TODO: needs parameters and all
    override protected def validateInternal(input: EventInput): Try[_root_.nephtys.loom.protocol.vanilla.solar.SolarProtocol.Event] = ???
  }

  case class PurchaseListCharm(id : Id, charm : CharmRef) extends SolarCommand {
    override protected def validateInternal(input: EventInput): Try[_root_.nephtys.loom.protocol.vanilla.solar.SolarProtocol.Event] = ???
  }

  case class SetOwner(id : Id, owner : Email) extends SolarCommand {
    override protected def validateInternal(input: EventInput): Try[_root_.nephtys.loom.protocol.vanilla.solar.SolarProtocol.Event] = ???
  }

  case class SetReaders(id : Id, readers : Set[Email]) extends SolarCommand {
    override protected def validateInternal(input: EventInput): Try[_root_.nephtys.loom.protocol.vanilla.solar.SolarProtocol.Event] = ???
  }

  case class SetPublic(id : Id, public : Boolean) extends SolarCommand {
    override protected def validateInternal(input: EventInput): Try[_root_.nephtys.loom.protocol.vanilla.solar.SolarProtocol.Event] = ???
  }

  case class AddSpecialty(id : Id, specialtyAble: SpecialtyAble, title : String) extends SolarCommand {
    override protected def validateInternal(input: EventInput): Try[_root_.nephtys.loom.protocol.vanilla.solar.SolarProtocol.Event] = ???
  }

  case class RemoveSpecialty(id : Id, specialtyAble: SpecialtyAble, title : String) extends SolarCommand {
    override protected def validateInternal(input: EventInput): Try[_root_.nephtys.loom.protocol.vanilla.solar.SolarProtocol.Event] = ???
  }

  //includes delete
  case class SetIntimacy(id : Id, title : String, intensity : Option[Intimacies.Intensity]) extends SolarCommand {
    override protected def validateInternal(input: EventInput): Try[_root_.nephtys.loom.protocol.vanilla.solar.SolarProtocol.Event] = ???
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
