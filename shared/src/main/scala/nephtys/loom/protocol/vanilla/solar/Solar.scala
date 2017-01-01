package nephtys.loom.protocol.vanilla.solar
import nephtys.loom.protocol.shared.CharmRef
import nephtys.loom.protocol.vanilla.solar.Equipments.Equipment
import nephtys.loom.protocol.vanilla.solar.Merits.Merit
import nephtys.loom.protocol.vanilla.solar.Misc._
import org.nephtys.loom.generic.protocol.Aggregate
import org.nephtys.loom.generic.protocol.InternalStructures.{Email, ID, IDable}

import scala.scalajs.js.annotation.{JSExport, JSExportAll}
import scala.util.Try

/**
  * Created by nephtys on 12/7/16.
  */
@JSExport
@JSExportAll
final case class Solar(
                      owner : Email, //TODO: needs command/event
                      readers : Set[Email], //TODO: needs command/event
                      public : Boolean,
                      id : ID[Solar],
                      stillInCharGen  : Boolean,
                      bonusPointsUnspent : Int, //TODO: needs command/event

                      name : String,
                      concept : String, //TODO: needs command/event
                      player : String, //TODO: needs command/event
                      anima : String, //TODO: needs command/event
                      caste : Option[Caste], //TODO: needs command/event

                      attributes : Attributes.AttributeBlock, //TODO: needs command/event
                      abilities : Abilities.AbilityMatrix, //TODO: needs command/event
                      merits : List[Merit], //TODO: needs command/event

                      willpowerDots : Int,  //TODO: needs command/event
                      essenceCommitted : Int,  //TODO: needs command/event
                      limitTrigger: String,  //TODO: needs command/event

                      experience : Experiences.ExperienceBox, //TODO: needs command/event

                      equipment : List[Equipment],  //TODO: needs command/event

                      intimacies : Map[String, Intimacies.Intensity], //TODO: needs command/event

                      charms : List[CharmRef],  //TODO: needs command/event

                      notes : List[String]

                      ) extends org.nephtys.loom.generic.protocol.Aggregate[Solar] {

  //calculate based on spent experience
  def essence : Essence = {
    Experiences.spentXPtoEssenceLevel(experience.generalXP.spent)
  }


  //list all commands that need to be executed to translate this solar aggregate instance the other aggregate, or failed if impossible
  def diff(other: Solar) : Try[Seq[SolarProtocol.Command]] = ???

}
