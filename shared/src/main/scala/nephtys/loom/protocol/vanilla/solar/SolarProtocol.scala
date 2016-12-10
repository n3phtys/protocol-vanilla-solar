package nephtys.loom.protocol.vanilla.solar

import nephtys.loom.protocol.shared.CharmRef
import org.nephtys.loom.generic.protocol.InternalStructures.{Email, EndpointRoot, ID}
import org.nephtys.loom.generic.protocol.Protocol

import scala.scalajs.js.annotation.JSExportAll
import scala.util.{Failure, Success, Try}

/**
  * Created by nephtys on 12/7/16.
  */
@JSExportAll
object SolarProtocol extends Protocol[Solar]{
  type Id = ID[Solar]
  override val endpointRoot: EndpointRoot = EndpointRoot("vanilla_solar")

  case class Create(owner : Email, id : ID[Solar]) extends Command {
    override protected def validateInternal(aggregate: _root_.nephtys.loom.protocol.vanilla.solar.SolarProtocol.Aggregates): Try[_root_.nephtys.loom.protocol.vanilla.solar.SolarProtocol.Event] = {
      if (aggregate.contains(id)) {
        Failure(new Exception("ID already in use"))
      } else {
        Success(Creation(owner, id))
      }
    }
  }
  case class Creation(owner : Email, id : ID[Solar]) extends Event {
    override def commit(aggregate: _root_.nephtys.loom.protocol.vanilla.solar.SolarProtocol.Aggregates): _root_.nephtys.loom.protocol.vanilla.solar.SolarProtocol.Aggregates = {
      aggregate.+((id, Characters.emptySolar(id, owner)))
    }
  }


  case class SetName(id : Id, name : String) extends Command {
    override protected def validateInternal(aggregate: _root_.nephtys.loom.protocol.vanilla.solar.SolarProtocol.Aggregates): Try[_root_.nephtys.loom.protocol.vanilla.solar.SolarProtocol.Event] = ???
  }
  case class NameChanged(id : Id, name : String) extends Event {
    override def commit(aggregate: _root_.nephtys.loom.protocol.vanilla.solar.SolarProtocol.Aggregates): _root_.nephtys.loom.protocol.vanilla.solar.SolarProtocol.Aggregates = ???
  }

  case class LeaveCharacterGeneration(id : Id) extends Command {
    override protected def validateInternal(aggregate: _root_.nephtys.loom.protocol.vanilla.solar.SolarProtocol.Aggregates): Try[_root_.nephtys.loom.protocol.vanilla.solar.SolarProtocol.Event] = ???
  }

  case class CharacterGenerationLeft(id : Id) extends Event {
    override def commit(aggregate: _root_.nephtys.loom.protocol.vanilla.solar.SolarProtocol.Aggregates): _root_.nephtys.loom.protocol.vanilla.solar.SolarProtocol.Aggregates = ???
  }

  case class PurchaseCharm(id : Id, charm : CharmRef) extends Command {
    override protected def validateInternal(aggregate: _root_.nephtys.loom.protocol.vanilla.solar.SolarProtocol.Aggregates): Try[_root_.nephtys.loom.protocol.vanilla.solar.SolarProtocol.Event] = ???
  }

  case class CharmPurchased(id : Id, charm : CharmRef) extends Event {
    override def commit(aggregate: _root_.nephtys.loom.protocol.vanilla.solar.SolarProtocol.Aggregates): _root_.nephtys.loom.protocol.vanilla.solar.SolarProtocol.Aggregates = ???
  }

  case class AddNote(id : Id, str : String, index : Int ) extends Command {
    override protected def validateInternal(aggregate: _root_.nephtys.loom.protocol.vanilla.solar.SolarProtocol.Aggregates): Try[_root_.nephtys.loom.protocol.vanilla.solar.SolarProtocol.Event] = {
      if (index >= 0 && aggregate.get(id).exists(s => s.notes.length >= index)) {
        Failure(new Exception("ID not known or index out of bounds"))
      } else {
        Success(NoteAdded(id, str, index))
      }
    }
  }
  case class NoteAdded(id : Id, str : String, index : Int) extends Event {
    override def commit(aggregate: _root_.nephtys.loom.protocol.vanilla.solar.SolarProtocol.Aggregates): _root_.nephtys.loom.protocol.vanilla.solar.SolarProtocol.Aggregates = {
      val oldchar = aggregate(id)
      val oldlist : List[String] = oldchar.notes
      val newlist : List[String] = oldlist.take(index).:+(str) ++ oldlist.drop(index)
      aggregate + ((id, oldchar.copy(notes = newlist)))
    }
  }
  case class RemoveNote(id : Id, index : Int ) extends Command {
    override protected def validateInternal(aggregate: _root_.nephtys.loom.protocol.vanilla.solar.SolarProtocol.Aggregates): Try[_root_.nephtys.loom.protocol.vanilla.solar.SolarProtocol.Event] = {
      if (index >= 0 && aggregate.get(id).exists(s => s.notes.length > index)) {
        Failure(new Exception("ID not known or index out of bounds"))
      } else {
        Success(NoteRemoved(id, index))
      }
    }
  }
  case class NoteRemoved(id : Id, index : Int) extends Event {
    override def commit(aggregate: _root_.nephtys.loom.protocol.vanilla.solar.SolarProtocol.Aggregates): _root_.nephtys.loom.protocol.vanilla.solar.SolarProtocol.Aggregates = {
      val oldchar = aggregate(id)
      val oldlist : List[String] = oldchar.notes
      val newlist : List[String] = oldlist.take(index) ++ oldlist.drop(index+1)
      aggregate + ((id, oldchar.copy(notes = newlist)))
    }
  }
}
