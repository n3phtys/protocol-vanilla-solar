package nephtys.loom.protocol.vanilla.solar

import nephtys.loom.protocol.vanilla.solar.Attributes.types._
import nephtys.loom.protocol.vanilla.solar.Misc.Dots

import scala.scalajs.js.annotation.JSExportAll

/**
  * Created by nephtys on 12/7/16.
  */
@JSExportAll
object Attributes {
  final case class AttributeRating(attribute: Attribute, dots: Dots) {
    assert(dots.number >= 1)
    assert(dots.number <= 5)

    def xpValue : Int = dots.number match {
      case 5 => 40
      case 4 => 24
      case 3 => 12
      case 2 => 4
      case 1 => 0
      case _ => throw new IllegalStateException()
    }
  }

  sealed trait AttributeOrder {
    def toInt : Int
    def bpWeight : Int
  }
  case object Primary extends AttributeOrder {
    override def toInt: Int = 0
    override def bpWeight: Int = 4
  }
  case object Secondary extends AttributeOrder {
    override def toInt: Int = 1
    override def bpWeight: Int = 4
  }
  case object Ternary extends AttributeOrder {
    override def toInt: Int = 2
    override def bpWeight: Int = 3
  }

  //ordering = 8 is default, each other value equals one of 6 possibilities, given by 0 - 5
  final case class AttributeBlock(block : IndexedSeq[AttributeRating], ordering : Int ) {
    def allFreePointsUsed : Boolean = (primaryDots >= 11) || (secondaryDots >= 9) || (ternaryDots >= 7)

    //information about primary/secondary/terniary via indices is saved in ordering

    assert(block.size == Attributes.size)
    assert(block.zipWithIndex.forall(a => a._1.attribute == Attributes(a._2)))

    def mentals : IndexedSeq[AttributeRating] = block.filter(_.attribute.nature == Mental)
    def socials : IndexedSeq[AttributeRating] = block.filter(_.attribute.nature == Social)
    def physicals : IndexedSeq[AttributeRating] = block.filter(_.attribute.nature == Physical)

    def dotsPhysical : Int = physicals.map(_.dots.number).sum
    def dotsSocial : Int = socials.map(_.dots.number).sum
    def dotsMental : Int = mentals.map(_.dots.number).sum

    def copyWithOrdering : AttributeBlock = {
      val md : Int = mentals.map(_.dots.number).sum
      val sd : Int = socials.map(_.dots.number).sum
      val pd : Int = physicals.map(_.dots.number).sum
      val order : Int = if (md >= sd) {
        if (pd >= md) {
          1
        } else if (pd >= sd) {
          5
        } else {
          4
        }
      } else {
        if (pd >= sd) {
          0
        } else if (pd >= md) {
          2
        } else {
          3
        }
      }
      copy(ordering = order)
    }


    def bonusPointsInvested : Int = {
      val sum = Math.max(0, primaryDots - 8 - 3) + Math.max(0, secondaryDots - 6 - 3) + Math.max(0, ternaryDots - 4 - 3)
      sum * 4
    }

    def copyWithChange(index : Int, rating : Int) : AttributeBlock = {
      AttributeBlock(block.take(index) ++ IndexedSeq(block(index).copy(dots = Dots(rating))) ++ block.drop(index + 1), ordering)
    }

    def primary: IndexedSeq[AttributeRating] = order(0)
    def secondary: IndexedSeq[AttributeRating] = order(1)
    def ternary: IndexedSeq[AttributeRating] = order(2)

    def primaryDots: Int = primary.map(_.dots.number).sum
    def secondaryDots: Int =secondary.map(_.dots.number).sum
    def ternaryDots: Int = ternary.map(_.dots.number).sum


    def getRating(attributeName : String) : Option[Int] = this.block.find(a => a.attribute.toString.equalsIgnoreCase(attributeName)).map(_.dots.number)

    def order(i : Int) : IndexedSeq[AttributeRating] = (ordering, i) match {
      case (0, 0) => physicals
      case (0, 1) => socials
      case (0, 2) => mentals

      case (1, 0) => physicals
      case (1, 1) => mentals
      case (1, 2) => socials

      case (2, 0) => socials
      case (2, 1) => physicals
      case (2, 2) => mentals

      case (3, 0) => socials
      case (3, 1) => mentals
      case (3, 2) => physicals

      case (4, 0) => mentals
      case (4, 1) => socials
      case (4, 2) => physicals

      case (5, 0) => mentals
      case (5, 1) => physicals
      case (5, 2) => socials

      case (_, 0) => physicals
      case (_, 1) => socials
      case (_, _) => mentals
    }

    def getOrder(index : Int) : AttributeOrder = (ordering, index) match {
      case (x, a) if index >= 0 && index < 3 && (x == 0 || x == 1) => Primary
      case (x, a) if index >= 0 && index < 3 && (x == 2 || x == 5) => Secondary
      case (x, a) if index >= 0 && index < 3 && (x == 3 || x == 4) => Ternary

      case (x, a) if index >= 3 && index < 6 && (x == 2 || x == 3) => Primary
      case (x, a) if index >= 3 && index < 6 && (x == 0 || x == 4) => Secondary
      case (x, a) if index >= 3 && index < 6 && (x == 1 || x == 5) => Ternary

      case (x, a) if index >= 6 && index < 9 && (x == 4 || x == 5) => Primary
      case (x, a) if index >= 6 && index < 9 && (x == 1 || x == 3) => Secondary
      case (x, a) if index >= 6 && index < 9 && (x == 0 || x == 2) => Ternary
      case _=> Ternary
    }
  }

  def emptyAttributeBlock : AttributeBlock = AttributeBlock(Attributes.map(c => AttributeRating(c, Dots(1))), 0)

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
