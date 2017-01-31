package nephtys.loom.protocol.zprotocols

import nephtys.loom.protocol.chronicles.solar.{CharacterFactory, Experiences, Solar}
import nephtys.loom.protocol.shared.CustomPowers.CustomPower
import nephtys.loom.protocol.vanilla.solar.Misc.Caste
import nephtys.loom.protocol.vanilla.solar.{Attributes, Exceptions, Intimacies}
import org.nephtys.loom.generic.protocol.EventInput.EventInput
import org.nephtys.loom.generic.protocol.InternalStructures.{Email, EndpointRoot, FailableList, ID}
import org.nephtys.loom.generic.protocol.{Backend, Protocol}
import upickle.default._

import scala.scalajs.js.annotation.JSExportAll
import scala.util.{Failure, Success, Try}

/**
  * Created by Christopher on 21.01.2017.
  */
@JSExportAll
object ZChroniclesSolarProtocol extends Protocol[Solar] with Backend[Solar] {
  type Id = ID[Solar]

  sealed trait SolarCommand extends Command

  sealed trait SolarEvent extends Event

  override val endpointRoot: EndpointRoot = EndpointRoot("chroniclessolar")



  case class Create(owner : Email, id : ID[Solar]) extends SolarCommand {

    override def insert: Boolean = true

    override def checkCreatorIsAuthor(requester: Email): Boolean = owner.equals(requester)

    override protected def validateInternal(input: EventInput): Try[SolarEvent] = Success(ECreate(owner, id))
  }
  case class ECreate(owner : Email, id : ID[Solar]) extends SolarEvent {

    override def insert: Boolean = true

    override def createNew: Solar = CharacterFactory.emptyChroniclesSolar(id.id, owner)

    override def commitInternal(old: EventInput): Solar = throw new UnsupportedOperationException
  }


  case class Delete(id : ID[Solar]) extends SolarCommand {
    override def remove: Boolean = true

    override protected def validateInternal(input: EventInput): Try[SolarEvent] = Success(EDelete(id))
  }
  case class EDelete(id : ID[Solar]) extends SolarEvent {
    override def remove: Boolean = true
    override def changesAfterRemoval(aggregates: Aggregates) : Aggregates = aggregates

    override def commitInternal(old: EventInput): Solar = throw new UnsupportedOperationException
  }


  case class SetName(id : Id, name : String) extends SolarCommand {
    override protected def validateInternal(input: EventInput): Try[SolarEvent] =  {
      if (name.length < 512) {
        Success(ESetName(id, name))
      } else {
        Failure(new IllegalArgumentException)
      }
    }
  }
  case class ESetName(id : Id, name : String) extends SolarEvent {
    override def commitInternal(old: EventInput): Solar = {
      val metaDescriptors = old.get[Solar].metaDescriptors.copy(name = name)
      old.get[Solar].copy(metaDescriptors = metaDescriptors)
    }
  }


  case class SetWillpower(id : Id, dots : Int) extends SolarCommand {
    override protected def validateInternal(input: EventInput): Try[ZChroniclesSolarProtocol.Event] =  {
      if (dots < 5 || dots > 10) {
        Failure(new IllegalArgumentException)
      } else {
         if (input.get[Solar].directDotValues.willpowerDots >= dots && !input.get[Solar].stillInCharGen) {Failure(new IllegalArgumentException)} else {
           val xpcost: Int = (dots -  input.get[Solar].directDotValues.willpowerDots) * Experiences.Multiplicators.Willpower
           if (input.get[Solar].experience.beatsLeftForSpending / Experiences.Point.asBeats(1) >= xpcost) {
             Success(ESetWillpower(id, dots))
           } else {
             Failure(Exceptions.missXP())
           }
         }
      }
    }
  }
  case class ESetWillpower(id : Id, dots : Int) extends SolarEvent {
    override def commitInternal(input: EventInput): Solar = {
      val solar : Solar = input.get
      val xpcost: Int = (solar.directDotValues.willpowerDots - dots) * Experiences.Multiplicators.Willpower
      solar.copy(experience = solar.experience.modifyXP(xpcost).get, directDotValues = solar.directDotValues.copy(willpowerDots = dots))
    }
  }
  case class SetCaste(id : Id, caste : Caste) extends SolarCommand {
    override protected def validateInternal(input: EventInput): Try[ZChroniclesSolarProtocol.Event] = {
      val solar : Solar = input.get
      if(solar.stillInCharGen) {
        Success(ESetCaste(id, caste))
      } else {
        Failure(new IllegalStateException)
      }
    }
  }
  case class ESetCaste(id : Id, caste : Caste)  extends SolarEvent {
    override def commitInternal(input: EventInput): Solar = {
      //todo: recalculation of charms' xp value (as this would have changed for listed charms thanks to favored/unfavored split)
      //todo: can also make some combinations illegal ==> maybe this should just set listed charms back to zero to make sure
      ???
    }
  }
  case class AddManualExperienceChange(id : Id, amountAdded : Int, note : String) extends SolarCommand {
    override protected def validateInternal(input: EventInput): Try[ZChroniclesSolarProtocol.Event] = ???
  }
  case class EAddManualExperienceChange(id : Id, amountAdded : Int, note : String, timestamp : Long) extends SolarEvent {
    override def commitInternal(input: EventInput): Solar = ???
  }
  case class SetAnima(id : Id, anima : String) extends SolarCommand {
    override protected def validateInternal(input: EventInput): Try[ZChroniclesSolarProtocol.Event] = {
      if (anima.length < 512) {
        Success(ESetAnima(id, anima))
      } else {
        Failure(new IllegalArgumentException)
      }
    }
  }
  case class ESetAnima(id : Id, anima : String) extends SolarEvent {
    override def commitInternal(input: EventInput): Solar = {
      val metaDescriptors = input.get[Solar].metaDescriptors.copy(anima = anima)
      input.get[Solar].copy(metaDescriptors = metaDescriptors)
    }
  }
  case class SetConcept(id : Id, concept : String) extends SolarCommand {
    override protected def validateInternal(input: EventInput): Try[ZChroniclesSolarProtocol.Event] =  {
      if (concept.length < 512) {
        Success(ESetConcept(id, concept))
      } else {
        Failure(new IllegalArgumentException)
      }
    }
  }
  case class ESetConcept(id : Id, concept : String) extends SolarEvent {
    override def commitInternal(input: EventInput): Solar = {
      val metaDescriptors = input.get[Solar].metaDescriptors.copy(concept = concept)
      input.get[Solar].copy(metaDescriptors = metaDescriptors)
    }
  }
  case class SetLimitTrigger(id : Id, limitTrigger : String) extends SolarCommand {
    override protected def validateInternal(input: EventInput): Try[ZChroniclesSolarProtocol.Event] =  {
      if (limitTrigger.length < 512) {
        Success(ESetLimitTrigger(id, limitTrigger))
      } else {
        Failure(new IllegalArgumentException)
      }
    }
  }
  case class ESetLimitTrigger(id : Id, limitTrigger: String) extends SolarEvent {
    override def commitInternal(input: EventInput): Solar = {
      val metaDescriptors = input.get[Solar].metaDescriptors.copy(limitTrigger = limitTrigger)
      input.get[Solar].copy(metaDescriptors = metaDescriptors)
    }
  }
  case class SetPlayer(id : Id, player : String) extends SolarCommand {
    override protected def validateInternal(input: EventInput): Try[ZChroniclesSolarProtocol.Event] =  {
      if (player.length < 512) {
        Success(ESetPlayer(id, player))
      } else {
        Failure(new IllegalArgumentException)
      }
    }
  }
  case class ESetPlayer(id : Id, player : String) extends SolarEvent {
    override def commitInternal(input: EventInput): Solar = {
      val metaDescriptors = input.get[Solar].metaDescriptors.copy(player = player)
      input.get[Solar].copy(metaDescriptors = metaDescriptors)
    }
  }
  case class LeaveCG(id : Id) extends SolarCommand {
    override protected def validateInternal(input: EventInput): Try[ZChroniclesSolarProtocol.Event] = {
      val solar : Solar = input.get
      if (!solar.stillInCharGen || !solar.attributes.allFreePointsUsed || !solar.abilities.allFreePointsSpend) {
        Failure(new IllegalStateException())
      } else {
        Success(ELeaveCG(id))
      }
    }
  }
  case class ELeaveCG(id : Id) extends SolarEvent {
    override def commitInternal(input: EventInput): Solar = ???
  }
  case class PurchaseCustomCharm(id : Id, charm : CustomPower) extends SolarCommand {
    override protected def validateInternal(input: EventInput): Try[ZChroniclesSolarProtocol.Event] = ???
  }
  case class EPurchaseCustomCharm(id : Id, charm : CustomPower) extends SolarEvent {
    override def commitInternal(input: EventInput): Solar = ???
  }
  case class PurchaseListCharm(id : Id, charmIndex : Int) extends SolarCommand {
    override protected def validateInternal(input: EventInput): Try[ZChroniclesSolarProtocol.Event] = ???
  }
  case class EPurchaseListCharm(id : Id, charmIndex : Int) extends SolarEvent {
    override def commitInternal(input: EventInput): Solar = ???
  }
  case class SetOwner(id : Id, owner : Email) extends SolarCommand {
    override protected def validateInternal(input: EventInput): Try[ZChroniclesSolarProtocol.Event] = Success(ESetOwner(id, owner))
  }
  case class ESetOwner(id : Id, owner : Email) extends SolarEvent {
    override def commitInternal(input: EventInput): Solar = input.get[Solar].copy(metaInfo = input.get[Solar].metaInfo.copy( owner = owner))
  }
  case class SetReaders(id : Id, readers : Set[Email]) extends SolarCommand {
    override protected def validateInternal(input: EventInput): Try[ZChroniclesSolarProtocol.Event] = Success(ESetReaders(id, readers))
  }
  case class ESetReaders(id : Id, readers : Set[Email]) extends SolarEvent {
    override def commitInternal(input: EventInput): Solar = input.get[Solar].copy(metaInfo = input.get[Solar].metaInfo.copy( readers = readers))
  }
  case class SetPublic(id : Id, public : Boolean) extends SolarCommand {
    override protected def validateInternal(input: EventInput): Try[ZChroniclesSolarProtocol.Event] = Success(ESetPublic(id, public))
  }
  case class ESetPublic(id : Id, public : Boolean) extends SolarEvent {
    override def commitInternal(input: EventInput): Solar = input.get[Solar].copy(metaInfo = input.get[Solar].metaInfo.copy( public = public))
  }
  case class AddAbility(id : Id, abilityName : String) extends SolarCommand {
    override protected def validateInternal(input: EventInput): Try[ZChroniclesSolarProtocol.Event] = ???
  }
  case class EAddAbility(id : Id, abilityName : String) extends SolarEvent {
    override def commitInternal(input: EventInput): Solar = ???
  }
  case class SetAbilityRating(id : Id, abilityName : String, rating : Int) extends SolarCommand {
    override protected def validateInternal(input: EventInput): Try[ZChroniclesSolarProtocol.Event] = ???
  }
  case class ESetAbilityRating(id : Id, abilityName : String, rating : Int) extends SolarEvent {
    override def commitInternal(input: EventInput): Solar = ???
  }
  case class SetAbilityType(id : Id, abilityName : String, typ : nephtys.loom.protocol.vanilla.solar.Abilities.Type) extends SolarCommand {
    override protected def validateInternal(input: EventInput): Try[ZChroniclesSolarProtocol.Event] = ???
  }
  case class ESetAbilityType(id : Id, abilityName : String, typ : nephtys.loom.protocol.vanilla.solar.Abilities.Type) extends SolarEvent {
    override def commitInternal(input: EventInput): Solar = ???
  }
  case class AddOrRemoveSpecialty(id : Id, abilityName : String, title : String, add : Boolean) extends SolarCommand {
    override protected def validateInternal(input: EventInput): Try[ZChroniclesSolarProtocol.Event] = ???
  }
  case class EAddOrRemoveSpecialty(id : Id, abilityName : String, title : String, add : Boolean) extends SolarEvent {
    override def commitInternal(input: EventInput): Solar = ???
  }
  case class SetIntimacy(id : Id, title : String, intensity : Option[Intimacies.Intensity]) extends SolarCommand {
    override protected def validateInternal(input: EventInput): Try[ZChroniclesSolarProtocol.Event] = Success(ESetIntimacy(id, title, intensity))
  }
  case class ESetIntimacy(id : Id, title : String, intensity : Option[Intimacies.Intensity]) extends SolarEvent {
    override def commitInternal(input: EventInput): Solar = {
      val s = input.get[Solar]
      if (intensity.isDefined) {
        s.copy(intimacies = s.intimacies.+((title, intensity.get)))
      } else {
        s.copy(intimacies = s.intimacies.-(title))
      }
    }
  }
  case class SetAspiration(id : Id, index : Int, title : String) extends SolarCommand {
    override protected def validateInternal(input: EventInput): Try[ZChroniclesSolarProtocol.Event] = ???
  }
  case class ESetAspiration(id : Id, index : Int, title : String) extends SolarEvent {
    override def commitInternal(input: EventInput): Solar = ???
  }
  case class AddRemoveOrChangeMerit(id : Id, title : String, category : String, index : Int, add : Option[Boolean], rating : Int) extends SolarCommand {
    override protected def validateInternal(input: EventInput): Try[ZChroniclesSolarProtocol.Event] = ???
  }
  case class EAddRemoveOrChangeMerit(id : Id, title : String, category : String, index : Int, add : Option[Boolean], rating : Int) extends SolarEvent {
    override def commitInternal(input: EventInput): Solar = ???
  }
  case class AddNote(id : Id, str : String, index : Int ) extends SolarCommand {
    override protected def validateInternal(input: EventInput): Try[ZChroniclesSolarProtocol.Event] = if (input.get[Solar].notes.length >= index && index >= 0 ) {
      Success(EAddNote(id, str, index))
    } else {
      Failure(new IndexOutOfBoundsException)
    }
  }
  case class EAddNote(id : Id, str : String, index : Int ) extends SolarEvent {
    override def commitInternal(input: EventInput): Solar = {
      val oldlist : List[String] = input.get[Solar].notes
      val newlist : List[String] = oldlist.take(index).:+(str) ++ oldlist.drop(index)
      input.get[Solar].copy(notes = newlist)
    }
  }
  case class RemoveNote(id : Id, index : Int ) extends SolarCommand {
    override protected def validateInternal(input: EventInput): Try[ZChroniclesSolarProtocol.Event] = if (input.get[Solar].notes.length > index && index >= 0 ) {
      Success(ERemoveNote(id, index))
    } else {
      Failure(new IndexOutOfBoundsException)
    }
  }
  case class ERemoveNote(id : Id, index : Int ) extends SolarEvent {
    override def commitInternal(input: EventInput): Solar = {
      val oldlist : List[String] = input.get[Solar].notes
      val newlist : List[String] = oldlist.take(index) ++ oldlist.drop(index+1)
      input.get[Solar].copy(notes = newlist)
    }
  }
  case class SetAttribute(id : Id, attributeIndex : Int, rating : Int) extends SolarCommand {
    override protected def validateInternal(input: EventInput): Try[ZChroniclesSolarProtocol.Event] = ???
  }
  case class ESetAttribute(id : Id, attributeIndex : Int, rating : Int) extends SolarEvent {
    override def commitInternal(input: EventInput): Solar = ???
  }




  def diff(id : Id, from : Attributes.AttributeBlock, to : Attributes.AttributeBlock) : Seq[SolarCommand] = {
    from.block.indices.filter(i => from.block(i).dots.number != to.block(i).dots.number).map(i => {
      SetAttribute(id, i, to.block(i).dots.number)
    })
  }




  override def readCommands(json: String): Seq[ZChroniclesSolarProtocol.Command] =  read[Seq[SolarCommand]](json)

  override def writeAggregates(aggregates: Seq[Solar]): String =  write(aggregates)

  override def writeFailableList(failableList: FailableList[ZChroniclesSolarProtocol.Event]): String =  write(failableList.asInstanceOf[FailableList[SolarEvent]])




}
