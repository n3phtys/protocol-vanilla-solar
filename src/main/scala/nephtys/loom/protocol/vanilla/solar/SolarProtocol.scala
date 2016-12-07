package nephtys.loom.protocol.vanilla.solar

import org.nephtys.loom.generic.protocol.InternalStructures.EndpointRoot
import org.nephtys.loom.generic.protocol.Protocol

/**
  * Created by nephtys on 12/7/16.
  */
object SolarProtocol extends Protocol[Solar]{
  override val endpointRoot: EndpointRoot = EndpointRoot("vanilla_solar")
}
