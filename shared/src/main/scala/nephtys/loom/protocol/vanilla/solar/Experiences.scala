package nephtys.loom.protocol.vanilla.solar

import nephtys.loom.protocol.vanilla.solar.Misc.Essence

import scala.scalajs.js.annotation.{JSExport, JSExportAll}
import scala.util.{Failure, Success, Try}

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


  def mockBox : ExperienceBox = emptyBox.addManualEntry(30, SolarXP, "some reason", 1234).get.addManualEntry(20, GeneralXP, "other reason", 1250).get

  @JSExportAll
  final case class ExperienceBox(categories : Map[ExperienceType, ExperienceCategory], manualEntries : List[ManualEntry]) {
    assert(categories.contains(SolarXP))
    assert(categories.contains(GeneralXP))
    def generalXP: ExperienceCategory = categories(GeneralXP)
    def solarXP : ExperienceCategory = categories(SolarXP)
    def specialXP: Option[ExperienceCategory] = categories.get(SpecialXP)

    def formattedLeft : Seq[(ExperienceType, Int)] = (if(generalXP.current > 0) {Seq(GeneralXP -> generalXP.current)} else {Seq.empty}) ++ (if(generalXP.current > 0) {Seq(SolarXP -> solarXP.current)} else {Seq.empty}) ++ specialXP.filter(_.current > 0).map(i => Seq(SpecialXP -> i.current)).getOrElse(Seq.empty)

    def essenceRating : Int = Experiences.spentXPtoEssenceLevel(generalXP.spent).rating.toInt

    def pointsLeftToSpend(solarCharm : Boolean) : Int = if (solarCharm) {categories.filter(_._1 != SolarXP).map(_._2.current).sum} else {categories.map(_._2.current).sum}

    /**
      * used for automated spending (nothing manual)
      */
    def spendAmount(amount : Int, solarCharm : Boolean) : ExperienceBox = {
      if (solarCharm) {
        val newcategories: Map[ExperienceType, ExperienceCategory] = {
          val spendOnTwo : Int = Math.max(0, Math.min( amount, specialXP.map(_.current).getOrElse(0)))
          val spendAsSpecial : Option[ExperienceCategory] = specialXP.map(t => t.copy(current = t.current - spendOnTwo))
          val leftAfterTwo : Int = amount - spendOnTwo
          val spendAsGeneral : ExperienceCategory = generalXP.copy(current = generalXP.current - leftAfterTwo)
          Map.apply[ExperienceType, ExperienceCategory](GeneralXP -> spendAsGeneral, SolarXP -> solarXP) ++ spendAsSpecial.map(a => (SpecialXP, a))
        }
        this.copy(categories = newcategories)
      } else {
        val newcategories: Map[ExperienceType, ExperienceCategory] = {
          val spendOnOne : Int = Math.max(0, Math.min( amount, solarXP.current))
          val spendAsSolar : ExperienceCategory = solarXP.copy(current = solarXP.current - spendOnOne)
          val leftAfterOne : Int = amount - spendOnOne
          val spendOnTwo : Int = Math.max(0, Math.min( leftAfterOne, specialXP.map(_.current).getOrElse(0)))
          val spendAsSpecial : Option[ExperienceCategory] = specialXP.map(t => t.copy(current = t.current - spendOnTwo))
          val leftAfterTwo : Int = leftAfterOne - spendOnTwo
          val spendAsGeneral : ExperienceCategory = generalXP.copy(current = generalXP.current - leftAfterTwo)
          Map.apply[ExperienceType, ExperienceCategory](GeneralXP -> spendAsGeneral, SolarXP -> spendAsSolar) ++ spendAsSpecial.map(a => (SpecialXP, a))
        }
        this.copy(categories = newcategories)
      }
    }

    def addManualEntry(amount : Int, typ : ExperienceType, note : String, timestampSec : Long) : Try[ExperienceBox] = {
      val increase = if(amount > 0) amount else 0
      val decrease = if(amount < 0) amount else 0
      val newEntry : ManualEntry = if (amount > 0) {ManualGain(Math.abs(amount), typ, note, timestampSec)} else {ManualSpending(Math.abs(amount), typ, note, timestampSec)}
      val old : ExperienceCategory = categories.getOrElse(typ, ExperienceCategory(0, 0))
      val newCat : ExperienceCategory = ExperienceCategory(current = old.current + decrease + increase, total =  old.total + increase)


      if (amount == 0) {
        Failure(new IllegalArgumentException)
      } else if (amount > 0) {
        Success(
          ExperienceBox(categories + ((typ, newCat)), newEntry :: manualEntries)
        )
      } else {
        //decrease, therefore check before allowing
        if(amount * -1 > old.current) {
          Failure(new IllegalStateException)
        } else {
          Success(ExperienceBox(categories + ((typ, newCat)), newEntry :: manualEntries))
        }
      }

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
