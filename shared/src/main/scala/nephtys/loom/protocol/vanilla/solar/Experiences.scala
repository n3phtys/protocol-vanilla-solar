package nephtys.loom.protocol.vanilla.solar

import nephtys.loom.protocol.vanilla.solar.Misc.Essence

import scala.scalajs.js.annotation.JSExportAll

/**
  * Created by nephtys on 12/10/16.
  */
@JSExportAll
object Experiences {
  val types : Seq[ExperienceType] = Seq(GeneralXP, SolarXP, SpecialXP)
  def parseType(s : String) : ExperienceType = types.find(t => t.toString.equals(s)).getOrElse(GeneralXP)

  val breakpoints = Seq(50, 125, 200, 300)

  def spentXPtoEssenceLevel(xp : Int) : Essence = xp match {
    case x if x < breakpoints.head => Essence(1)
    case x if x > breakpoints(3) => Essence(5)
    case x if x >= breakpoints.head && x < breakpoints(1) => Essence(2)
    case x if x >= breakpoints(1) && x < breakpoints(2) => Essence(3)
    case x if x >= breakpoints(2) && x < breakpoints(3) => Essence(4)
  }


  def emptyBox : ExperienceBox = ExperienceBox(Map(SolarXP -> ExperienceCategory((0), (0)), GeneralXP -> ExperienceCategory((0), (0))), List.empty)


  def mockBox : ExperienceBox = emptyBox.addManualEntry(30, SolarXP, "some reason", 1234).addManualEntry(20, GeneralXP, "other reason", 1250)

  @JSExportAll
  final case class ExperienceBox(categories : Map[ExperienceType, ExperienceCategory], manualEntries : List[ManualEntry]) {
    assert(categories.contains(SolarXP))
    assert(categories.contains(GeneralXP))
    def generalXP: ExperienceCategory = categories(GeneralXP)
    def solarXP : ExperienceCategory = categories(SolarXP)
    def specialXP: Option[ExperienceCategory] = categories.get(SpecialXP)


    def essenceRating : Int = Experiences.spentXPtoEssenceLevel(generalXP.spent).rating.toInt

    /**
      * used for automated spending (nothing manual)
      * @param amount
      * @param typ
      * @return
      */
    def spendAmount(amount : Int, typ : ExperienceType) : ExperienceBox = {
      ???
    }

    def addManualEntry(amount : Int, typ : ExperienceType, note : String, timestampSec : Long) : ExperienceBox = {
      assert(amount != 0)
      val increase = if(amount > 0) amount else 0
      val decrease = if(amount < 0) amount else 0
      val newEntry : ManualEntry = if (amount > 0) {ManualGain(Math.abs(amount), typ, note, timestampSec)} else {ManualSpending(Math.abs(amount), typ, note, timestampSec)}
      val old : ExperienceCategory = categories.getOrElse(typ, ExperienceCategory(0, 0))
      val newCat : ExperienceCategory = ExperienceCategory(old.current + decrease + increase, old.total + increase)
      ExperienceBox(categories + ((typ, newCat)), newEntry :: manualEntries)
    }
    /*
    def removeManualEntry(index : Int) : Option[ExperienceBox] = if (manualEntries.size > index) Some({
      val toR : ManualEntry = manualEntries(index)
      val typ : ExperienceType = toR.typ
      val old : ExperienceCategory = categories.getOrElse(typ, ExperienceCategory(Current(0), Total(0)))
      val amount : Int =  toR.amount
      val newCat : ExperienceCategory = if (toR.isIncrease) {ExperienceCategory(old.current, Total(old.total.amount - amount))} else {ExperienceCategory(Current(old.current.amount + amount), Total(old.total.amount - amount))}
      ExperienceBox(categories + ((typ,newCat)), manualEntries.take(index) ++ manualEntries.drop(index+1))
    }) else None*/
  }

  @JSExportAll
  final case class ExperienceCategory(current: Int, total: Int) {
    def spent: Int = total - current
  }

  sealed trait ExperienceType

  sealed trait IncreasesEssence
  sealed trait UsefulForSolarCharms
  sealed trait UsefulForAnythingOtherThanCharms
  sealed trait ShownByDefault

  case object SolarXP extends ExperienceType with UsefulForAnythingOtherThanCharms
  case object GeneralXP extends ExperienceType with UsefulForSolarCharms with UsefulForAnythingOtherThanCharms with IncreasesEssence
  case object SpecialXP extends ExperienceType with UsefulForAnythingOtherThanCharms with UsefulForSolarCharms



  @JSExportAll
  sealed trait ManualEntry {
    //this amount is always positive
    def amount : Int
    def typ : ExperienceType
    def note : String
    def timestampSec : Long
    def isIncrease : Boolean

    def stringText : String = {
      (if (isIncrease) "+" else "-") + " " + amount.toString + " " + typ.toString + Some(note).filter(_.nonEmpty).map(s => s" for reason: $s").getOrElse(" - no reason given")
    }
  }

  @JSExportAll
  final case class ManualSpending(amount : Int, typ : ExperienceType, note : String, timestampSec : Long) extends ManualEntry {
    assert(amount > 0)

    override def isIncrease: Boolean = false
  }

  @JSExportAll
  final case class ManualGain(amount : Int, typ : ExperienceType, note : String, timestampSec : Long) extends  ManualEntry {
    assert(amount > 0)

    override def isIncrease: Boolean = true
  }
}
