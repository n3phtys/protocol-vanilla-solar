package nephtys.loom.protocol.vanilla.solar

import nephtys.loom.protocol.vanilla.solar.Attributes.types.{Archetype, Nature}
import nephtys.loom.protocol.vanilla.solar.Misc.Dots

import scala.scalajs.js.annotation.JSExportAll

/**
  * Created by nephtys on 12/7/16.
  */
@JSExportAll
object Attributes {
  final case class AttributeRating(attribute: Attribute, dots: Dots) {
    assert(dots.number >= 1)
    assert(dots.number <= 10)
  }

  final case class AttributeBlock(block : IndexedSeq[AttributeRating]) {
    assert(block.size == Attributes.size)
    assert(block.zipWithIndex.forall(a => a._1.attribute == Attributes(a._2)))

  }
  def emptyAttributeBlock : AttributeBlock = AttributeBlock(Attributes.map(c => AttributeRating(c, Dots(1))))

  object types {

    sealed trait Nature
    case object Physical extends Nature
    case object Social extends Nature
    case object Mental extends Nature

    sealed trait Archetype
    case object Power extends Archetype
    case object Finesse extends Archetype
    case object Resistance extends Archetype

  }

  import types._

  sealed trait Attribute {
    def archetype : Archetype
    def nature : Nature
  }
  val Attributes : IndexedSeq[Attribute] = IndexedSeq(Strength, Dexterity, Stamina, Charisma, Manipulation, Appearance, Perception, Intelligence, Wits)
  case object Strength extends Attribute {
    override def archetype: Archetype = Power

    override def nature: Nature = Physical
  }
  case object Dexterity extends Attribute {
    override def archetype: Archetype = Finesse

    override def nature: Nature = Physical
  }
  case object Stamina extends Attribute {
    override def archetype: Archetype = Resistance

    override def nature: Nature = Physical
  }
  case object Charisma extends Attribute {
    override def archetype: Archetype = Power

    override def nature: Nature = Social
  }
  case object Manipulation extends Attribute {
    override def archetype: Archetype = Finesse

    override def nature: Nature = Social
  }
  case object Appearance extends Attribute {
    override def archetype: Archetype = Resistance

    override def nature: Nature = Social
  }
  case object Perception extends Attribute {
    override def archetype: Archetype = Power

    override def nature: Nature = Mental
  }
  case object Intelligence extends Attribute {
    override def archetype: Archetype = Finesse

    override def nature: Nature = Mental
  }
  case object Wits extends Attribute {
    override def archetype: Archetype = Resistance

    override def nature: Nature = Mental
  }



}
