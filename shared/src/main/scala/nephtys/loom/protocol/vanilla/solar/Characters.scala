package nephtys.loom.protocol.vanilla.solar

import nephtys.loom.protocol.shared.CharmRef
import nephtys.loom.protocol.vanilla.solar.Misc.{LimitTrigger, _}
import org.nephtys.loom.generic.protocol.InternalStructures.{Email, ID}

import scala.scalajs.js.annotation.JSExportAll

/**
  * Created by nephtys on 12/7/16.
  */
@JSExportAll
object Characters {

  def emptySolar(id : ID[Solar], owner : Email) : Solar = Solar(
    owner, readers = Set.empty, public = false, id, stillInCharGen = true, 15, (""), (""), (""),(""),
    caste = None,
     attributes = Attributes.emptyAttributeBlock,
    abilities = Abilities.emptyMatrix, Merits.emptyMeritsList,
    willpowerDots = (5), essenceCommitted =  0, limitTrigger = "",
    experience = Experiences.emptyBox,
    equipment = Equipments.emptyEquipmentList, intimacies = Map.empty[String, Intimacies.Intensity],
    charms = List.empty[CharmRef], notes =  List.empty[String]
  )
}
