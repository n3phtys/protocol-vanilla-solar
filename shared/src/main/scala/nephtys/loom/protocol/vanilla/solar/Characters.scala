package nephtys.loom.protocol.vanilla.solar

import nephtys.loom.protocol.vanilla.solar.Misc._
import org.nephtys.loom.generic.protocol.InternalStructures.{Email, ID}

import scala.scalajs.js.annotation.JSExportAll

/**
  * Created by nephtys on 12/7/16.
  */
@JSExportAll
object Characters {

  def emptySolar(id : ID[Solar], owner : Email) : Solar = Solar(
    owner, id, stillInCharGen = true, Name(""), Concept(""),Anima(""), Player(""),
    LimitTrigger(""), Essence(1), None, WillpowerDots(5), Attributes.emptyAttributeBlock
  )
}
