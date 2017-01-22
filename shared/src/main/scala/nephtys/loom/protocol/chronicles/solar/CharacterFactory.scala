package nephtys.loom.protocol.chronicles.solar

import java.util.UUID

import nephtys.loom.protocol.chronicles.solar.Experiences.Point
import nephtys.loom.protocol.vanilla.solar.{Attributes, Merits, NamedSolarMetaDescriptors, Intimacies, Equipments}
import org.nephtys.loom.generic.protocol.InternalStructures.{Email, ID, MetaInfo}

/**
  * Created by Christopher on 21.01.2017.
  */
object CharacterFactory {
  def emptyChroniclesSolar(id : UUID, owner : Email) : Solar = {
    nephtys.loom.protocol.chronicles.solar.Solar(metaInfo = MetaInfo(owner = owner, readers = Set.empty, public = false)
      , ID[Solar](id), stillInCharGen = true, NamedSolarMetaDescriptors(limitTrigger = "",name = "",  player = "", concept =  "", anima = ""),
      caste = None,
      attributes = Attributes.emptyAttributeBlock,
      abilities = Abilities.emptyMatrix, Merits.emptyMeritsList,
      willpowerDots = 5, essenceCommitted =  0,
      experience = Experiences.emptyBeatBoxWithFree(10, Point),
      aspirations = IndexedSeq.empty,
      equipment = Equipments.emptyEquipmentList, intimacies = Map.empty[String, Intimacies.Intensity],
      listedPowers = Set.empty,
      customCharms = Seq.empty,
      notes =  List.empty)
  }

  val defaultEmail : Email = Email("N/A")
  def emptyChroniclesSolar : Solar = emptyChroniclesSolar(UUID.randomUUID(), defaultEmail)
}
