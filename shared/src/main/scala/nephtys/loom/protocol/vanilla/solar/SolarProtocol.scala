package nephtys.loom.protocol.vanilla.solar

import nephtys.loom.protocol.shared.CharmRef
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
    def internalSolarValidate(aggregate: _root_.nephtys.loom.protocol.vanilla.solar.SolarProtocol.Aggregates) : Try[SolarEvent] = validateInternal(aggregate).map(e => e.asInstanceOf[SolarEvent])
  }

  sealed trait SolarEvent extends Event

  case class Create(owner : Email, id : ID[Solar]) extends SolarCommand {
    override protected def validateInternal(aggregate: _root_.nephtys.loom.protocol.vanilla.solar.SolarProtocol.Aggregates): Try[_root_.nephtys.loom.protocol.vanilla.solar.SolarProtocol.Event] = {
      if (aggregate.contains(id)) {
        Failure(new Exception("ID already in use"))
      } else {
        Success(Creation(owner, id))
      }
    }
  }
  case class Creation(owner : Email, id : ID[Solar]) extends SolarEvent {
    override def commit(aggregate: _root_.nephtys.loom.protocol.vanilla.solar.SolarProtocol.Aggregates): _root_.nephtys.loom.protocol.vanilla.solar.SolarProtocol.Aggregates = {
      aggregate.+((id, Characters.emptySolar(id, owner)))
    }
  }


  case class Delete(id : ID[Solar]) extends SolarCommand {
    override protected def validateInternal(aggregate: _root_.nephtys.loom.protocol.vanilla.solar.SolarProtocol.Aggregates): Try[_root_.nephtys.loom.protocol.vanilla.solar.SolarProtocol.Event] = if(aggregate.contains(id)) {Success(Deletion(id))} else {Failure(new Exception("ID not found in aggregates"))}
  }
  case class Deletion(id : ID[Solar]) extends SolarEvent {
    override def commit(aggregate: _root_.nephtys.loom.protocol.vanilla.solar.SolarProtocol.Aggregates): _root_.nephtys.loom.protocol.vanilla.solar.SolarProtocol.Aggregates = aggregate.-(id)
  }


  case class SetName(id : Id, name : String) extends SolarCommand {
    override protected def validateInternal(aggregate: _root_.nephtys.loom.protocol.vanilla.solar.SolarProtocol.Aggregates): Try[_root_.nephtys.loom.protocol.vanilla.solar.SolarProtocol.Event] = {
      if (aggregate.contains(id)) {
        Success(NameChanged(id, name))
      } else {
        Failure(new Exception("Aggregate ID not found"))
      }
    }
  }
  case class NameChanged(id : Id, name : String) extends SolarEvent {
    override def commit(aggregate: _root_.nephtys.loom.protocol.vanilla.solar.SolarProtocol.Aggregates): _root_.nephtys.loom.protocol.vanilla.solar.SolarProtocol.Aggregates = {
      aggregate.+((id, aggregate(id).copy(name = name)))
    }
  }

  case class LeaveCharacterGeneration(id : Id) extends SolarCommand {
    override protected def validateInternal(aggregate: _root_.nephtys.loom.protocol.vanilla.solar.SolarProtocol.Aggregates): Try[_root_.nephtys.loom.protocol.vanilla.solar.SolarProtocol.Event] = ???
  }

  case class CharacterGenerationLeft(id : Id) extends SolarEvent {
    override def commit(aggregate: _root_.nephtys.loom.protocol.vanilla.solar.SolarProtocol.Aggregates): _root_.nephtys.loom.protocol.vanilla.solar.SolarProtocol.Aggregates = ???
  }

  case class PurchaseCharm(id : Id, charm : CharmRef) extends SolarCommand {
    override protected def validateInternal(aggregate: _root_.nephtys.loom.protocol.vanilla.solar.SolarProtocol.Aggregates): Try[_root_.nephtys.loom.protocol.vanilla.solar.SolarProtocol.Event] = ???
  }

  case class CharmPurchased(id : Id, charm : CharmRef) extends SolarEvent {
    override def commit(aggregate: _root_.nephtys.loom.protocol.vanilla.solar.SolarProtocol.Aggregates): _root_.nephtys.loom.protocol.vanilla.solar.SolarProtocol.Aggregates = ???
  }

  case class AddNote(id : Id, str : String, index : Int ) extends SolarCommand {
    override protected def validateInternal(aggregate: _root_.nephtys.loom.protocol.vanilla.solar.SolarProtocol.Aggregates): Try[_root_.nephtys.loom.protocol.vanilla.solar.SolarProtocol.Event] = {
      if (index >= 0 && aggregate.get(id).exists(s => s.notes.length >= index)) {
        Failure(new Exception("ID not known or index out of bounds"))
      } else {
        Success(NoteAdded(id, str, index))
      }
    }
  }
  case class NoteAdded(id : Id, str : String, index : Int) extends SolarEvent {
    override def commit(aggregate: _root_.nephtys.loom.protocol.vanilla.solar.SolarProtocol.Aggregates): _root_.nephtys.loom.protocol.vanilla.solar.SolarProtocol.Aggregates = {
      val oldchar = aggregate(id)
      val oldlist : List[String] = oldchar.notes
      val newlist : List[String] = oldlist.take(index).:+(str) ++ oldlist.drop(index)
      aggregate + ((id, oldchar.copy(notes = newlist)))
    }
  }
  case class RemoveNote(id : Id, index : Int ) extends SolarCommand {
    override protected def validateInternal(aggregate: _root_.nephtys.loom.protocol.vanilla.solar.SolarProtocol.Aggregates): Try[_root_.nephtys.loom.protocol.vanilla.solar.SolarProtocol.Event] = {
      if (index >= 0 && aggregate.get(id).exists(s => s.notes.length > index)) {
        Failure(new Exception("ID not known or index out of bounds"))
      } else {
        Success(NoteRemoved(id, index))
      }
    }
  }
  case class NoteRemoved(id : Id, index : Int) extends SolarEvent {
    override def commit(aggregate: _root_.nephtys.loom.protocol.vanilla.solar.SolarProtocol.Aggregates): _root_.nephtys.loom.protocol.vanilla.solar.SolarProtocol.Aggregates = {
      val oldchar = aggregate(id)
      val oldlist : List[String] = oldchar.notes
      val newlist : List[String] = oldlist.take(index) ++ oldlist.drop(index+1)
      aggregate + ((id, oldchar.copy(notes = newlist)))
    }
  }






  override def readCommands(json: String): Seq[_root_.nephtys.loom.protocol.vanilla.solar.SolarProtocol.Command] = read[Seq[SolarCommand]](json)

  override def writeAggregates(aggregates: Seq[Solar]): String = write(aggregates)

  override def writeFailableList(failableList: FailableList[_root_.nephtys.loom.protocol.vanilla.solar.SolarProtocol.Event]): String = write(failableList.asInstanceOf[FailableList[SolarEvent]])
}
