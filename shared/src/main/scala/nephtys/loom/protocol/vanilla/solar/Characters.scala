package nephtys.loom.protocol.vanilla.solar

import nephtys.loom.protocol.shared.CharmRef
import nephtys.loom.protocol.vanilla.solar.Misc.{LimitTrigger, _}
import org.nephtys.loom.generic.protocol.InternalStructures.{Email, ID, MetaInfo}

import scala.scalajs.js.annotation.JSExportAll

/**
  * Created by nephtys on 12/7/16.
  */
@JSExportAll
object Characters {

  def emptySolar(id : ID[Solar], owner : Email) : Solar = Solar(metaInfo = MetaInfo(owner = owner, readers = Set.empty, public = false)
    , id, stillInCharGen = true, 15, NamedSolarMetaDescriptors(limitTrigger = "",name = "",  player = "", concept =  "", anima = ""),
    caste = None,
     attributes = Attributes.emptyAttributeBlock,
    abilities = Abilities.emptyMatrix, Merits.emptyMeritsList,
    willpowerDots = 5, essenceCommitted =  0,
    experience = Experiences.emptyBox,
    equipment = Equipments.emptyEquipmentList, intimacies = Map.empty[String, Intimacies.Intensity],
    //listedCharms = Set.empty,
    //listedSpells = Set.empty,
    customCharms = Seq.empty,
    notes =  List.empty[String]
  )
}
