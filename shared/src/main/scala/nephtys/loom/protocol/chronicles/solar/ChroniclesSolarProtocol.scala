package nephtys.loom.protocol.chronicles.solar

import nephtys.loom.protocol.shared.CustomPowers.CustomPower
import nephtys.loom.protocol.vanilla.solar.Intimacies
import nephtys.loom.protocol.vanilla.solar.Experiences.ExperienceType
import nephtys.loom.protocol.vanilla.solar.Misc.Caste
import org.nephtys.loom.generic.protocol.EventInput.EventInput
import org.nephtys.loom.generic.protocol.InternalStructures.{Email, EndpointRoot, FailableList, ID}
import org.nephtys.loom.generic.protocol.{Backend, Protocol}
import upickle.default._

import scala.scalajs.js.annotation.JSExportAll
import scala.util.{Success, Try}

/**
  * Created by Christopher on 21.01.2017.
  */
@JSExportAll
object ChroniclesSolarProtocol extends Protocol[Solar] with Backend[Solar] {
  type Id = ID[Solar]

  sealed trait SolarCommand extends Command

  sealed trait SolarEvent extends Event

  override val endpointRoot: EndpointRoot = EndpointRoot("chroniclessolar")



  case class Create(owner : Email, id : ID[Solar]) extends SolarCommand {

    override def insert: Boolean = true

    override def checkCreatorIsAuthor(requester: Email): Boolean = owner.equals(requester)

    override protected def validateInternal(input: EventInput): Try[SolarEvent] = Success(Creation(owner, id))
  }
  case class Creation(owner : Email, id : ID[Solar]) extends SolarEvent {

    override def insert: Boolean = true

    override def createNew: Solar = CharacterFactory.emptyChroniclesSolar(id.id, owner)

    override def commitInternal(old: EventInput): Solar = throw new UnsupportedOperationException
  }


  case class Delete(id : ID[Solar]) extends SolarCommand {
    override def remove: Boolean = true

    override protected def validateInternal(input: EventInput): Try[SolarEvent] = Success(Deletion(id))
  }
  case class Deletion(id : ID[Solar]) extends SolarEvent {
    override def remove: Boolean = true
    override def changesAfterRemoval(aggregates: Aggregates) : Aggregates = aggregates

    override def commitInternal(old: EventInput): Solar = throw new UnsupportedOperationException
  }


  case class SetName(id : Id, name : String) extends SolarCommand {
    override protected def validateInternal(input: EventInput): Try[SolarEvent] = {
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
    override protected def validateInternal(input: EventInput): Try[_root_.nephtys.loom.protocol.chronicles.solar.ChroniclesSolarProtocol.Event] = ???
  }
  case class SetCaste(id : Id, caste : Caste) extends SolarCommand {
    override protected def validateInternal(input: EventInput): Try[_root_.nephtys.loom.protocol.chronicles.solar.ChroniclesSolarProtocol.Event] = ???
  }
  case class AddManualExperienceChange(id : Id, amountAdded : Int, typ : ExperienceType, note : String) extends SolarCommand {
    override protected def validateInternal(input: EventInput): Try[_root_.nephtys.loom.protocol.chronicles.solar.ChroniclesSolarProtocol.Event] = ???
  }
  case class SetAnima(id : Id, anima : String) extends SolarCommand {
    override protected def validateInternal(input: EventInput): Try[_root_.nephtys.loom.protocol.chronicles.solar.ChroniclesSolarProtocol.Event] = ???
  }
  case class SetConcept(id : Id, concept : String) extends SolarCommand {
    override protected def validateInternal(input: EventInput): Try[_root_.nephtys.loom.protocol.chronicles.solar.ChroniclesSolarProtocol.Event] = ???
  }
  case class SetLimitTrigger(id : Id, limitTrigger : String) extends SolarCommand {
    override protected def validateInternal(input: EventInput): Try[_root_.nephtys.loom.protocol.chronicles.solar.ChroniclesSolarProtocol.Event] = ???
  }
  case class SetPlayer(id : Id, player : String) extends SolarCommand {
    override protected def validateInternal(input: EventInput): Try[_root_.nephtys.loom.protocol.chronicles.solar.ChroniclesSolarProtocol.Event] = ???
  }
  case class LeaveCharacterGeneration(id : Id) extends SolarCommand {
    override protected def validateInternal(input: EventInput): Try[_root_.nephtys.loom.protocol.chronicles.solar.ChroniclesSolarProtocol.Event] = ???
  }
  case class PurchaseCustomCharm(id : Id, charm : CustomPower) extends SolarCommand {
    override protected def validateInternal(input: EventInput): Try[_root_.nephtys.loom.protocol.chronicles.solar.ChroniclesSolarProtocol.Event] = ???
  }
  case class PurchaseListCharm(id : Id, charmIndex : Int) extends SolarCommand {
    override protected def validateInternal(input: EventInput): Try[_root_.nephtys.loom.protocol.chronicles.solar.ChroniclesSolarProtocol.Event] = ???
  }
  case class SetOwner(id : Id, owner : Email) extends SolarCommand {
    override protected def validateInternal(input: EventInput): Try[_root_.nephtys.loom.protocol.chronicles.solar.ChroniclesSolarProtocol.Event] = ???
  }
  case class SetReaders(id : Id, readers : Set[Email]) extends SolarCommand {
    override protected def validateInternal(input: EventInput): Try[_root_.nephtys.loom.protocol.chronicles.solar.ChroniclesSolarProtocol.Event] = ???
  }
  case class SetPublic(id : Id, public : Boolean) extends SolarCommand {
    override protected def validateInternal(input: EventInput): Try[_root_.nephtys.loom.protocol.chronicles.solar.ChroniclesSolarProtocol.Event] = ???
  }
  case class AddAbility(id : Id, abilityName : String) extends SolarCommand {
    override protected def validateInternal(input: EventInput): Try[_root_.nephtys.loom.protocol.chronicles.solar.ChroniclesSolarProtocol.Event] = ???
  }
  case class SetAbilityRating(id : Id, abilityName : String, rating : Int) extends SolarCommand {
    override protected def validateInternal(input: EventInput): Try[_root_.nephtys.loom.protocol.chronicles.solar.ChroniclesSolarProtocol.Event] = ???
  }
  case class SetAbilityType(id : Id, abilityName : String, typ : nephtys.loom.protocol.vanilla.solar.Abilities.Type) extends SolarCommand {
    override protected def validateInternal(input: EventInput): Try[_root_.nephtys.loom.protocol.chronicles.solar.ChroniclesSolarProtocol.Event] = ???
  }
  case class AddOrRemoveSpecialty(id : Id, abilityName : String, title : String, add : Boolean) extends SolarCommand {
    override protected def validateInternal(input: EventInput): Try[_root_.nephtys.loom.protocol.chronicles.solar.ChroniclesSolarProtocol.Event] = ???
  }
  case class SetIntimacy(id : Id, title : String, intensity : Option[Intimacies.Intensity]) extends SolarCommand {
    override protected def validateInternal(input: EventInput): Try[_root_.nephtys.loom.protocol.chronicles.solar.ChroniclesSolarProtocol.Event] = ???
  }
  case class SetAspiration(id : Id, index : Int, title : String) extends SolarCommand {
    override protected def validateInternal(input: EventInput): Try[_root_.nephtys.loom.protocol.chronicles.solar.ChroniclesSolarProtocol.Event] = ???
  }
  case class AddRemoveOrChangeMerit(id : Id, title : String, category : String, add : Option[Boolean], rating : Int) extends SolarCommand {
    override protected def validateInternal(input: EventInput): Try[_root_.nephtys.loom.protocol.chronicles.solar.ChroniclesSolarProtocol.Event] = ???
  }
  case class AddNote(id : Id, str : String, index : Int ) extends SolarCommand {
    override protected def validateInternal(input: EventInput): Try[_root_.nephtys.loom.protocol.chronicles.solar.ChroniclesSolarProtocol.Event] = ???
  }
  case class RemoveNote(id : Id, index : Int ) extends SolarCommand {
    override protected def validateInternal(input: EventInput): Try[_root_.nephtys.loom.protocol.chronicles.solar.ChroniclesSolarProtocol.Event] = ???
  }
  case class SetAttribute(id : Id, attributeIndex : Int, rating : Int) extends SolarCommand {
    override protected def validateInternal(input: EventInput): Try[_root_.nephtys.loom.protocol.chronicles.solar.ChroniclesSolarProtocol.Event] = ???
  }























  override def readCommands(json: String): Seq[_root_.nephtys.loom.protocol.chronicles.solar.ChroniclesSolarProtocol.Command] = read[Seq[SolarCommand]](json)

  override def writeAggregates(aggregates: Seq[Solar]): String = write(aggregates)

  override def writeFailableList(failableList: FailableList[_root_.nephtys.loom.protocol.chronicles.solar.ChroniclesSolarProtocol.Event]): String = write(failableList.asInstanceOf[FailableList[SolarEvent]])




}
