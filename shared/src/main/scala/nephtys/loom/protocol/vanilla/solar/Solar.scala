package nephtys.loom.protocol.vanilla.solar
import nephtys.loom.protocol.vanilla.solar.Misc._
import org.nephtys.loom.generic.protocol.Aggregate
import org.nephtys.loom.generic.protocol.InternalStructures.{Email, ID, IDable}

/**
  * Created by nephtys on 12/7/16.
  */
final case class Solar(
                      owner : Email,
                      id : ID[Solar],
                      stillInCharGen  : Boolean,

                      name : Name,
                      concept : Concept,
                      anima : Anima,
                      player : Player,

                      limitTrigger: LimitTrigger,
                      essence: Essence,
                      caste : Option[Caste],

                      willpower : WillpowerDots,
                      attributes : Attributes.AttributeBlock

                      ) extends org.nephtys.loom.generic.protocol.Aggregate[Solar] {
}
