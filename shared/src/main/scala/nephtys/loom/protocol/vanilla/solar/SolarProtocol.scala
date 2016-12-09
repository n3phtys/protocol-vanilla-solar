package nephtys.loom.protocol.vanilla.solar

import org.nephtys.loom.generic.protocol.InternalStructures.{Email, EndpointRoot, ID}
import org.nephtys.loom.generic.protocol.Protocol

import scala.scalajs.js.annotation.JSExportAll
import scala.util.{Failure, Success, Try}

/**
  * Created by nephtys on 12/7/16.
  */
@JSExportAll
object SolarProtocol extends Protocol[Solar]{
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
}
