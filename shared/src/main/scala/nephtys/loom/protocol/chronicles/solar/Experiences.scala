package nephtys.loom.protocol.chronicles.solar

import nephtys.loom.protocol.vanilla.solar.Experiences.ExperienceCategory

import scala.scalajs.js.annotation.{JSExport, JSExportAll}
import scala.util.{Failure, Success, Try}

/**
  * Created by Christopher on 21.01.2017.
  */
@JSExportAll
object Experiences {
  object Multiplicators {
    val CharmsReduced = 3
    val Evocations = 3
    val Spells = 3
    val CharmsFull = 4
    val Willpower = 2
    val AbilityDot = 1
    val Specialty = 1
    val MeritDot = 1
    val AttributeDot = 4
  }


  /*
  //removed from rules:
  sealed trait InvestmentType
  case object Solar extends InvestmentType
  case object General extends InvestmentType*/

  sealed trait Unit {
    def asPoints(amount : Int) : Int
    def asBeats(amount : Int) : Int
  }
  case object Point extends Unit {
    override def asPoints(amount: Int): Int = amount
    override def asBeats(amount: Int): Int = amount * 5
  }
  case object Beat extends Unit {
    override def asPoints(amount: Int): Int = amount / 5
    override def asBeats(amount: Int): Int = amount
  }
  val Units : IndexedSeq[Unit] = IndexedSeq(Beat, Point)
  final case class Investment(amount : Int, unit : Experiences.Unit
                              //removed from rules: , typ : InvestmentType
                             ) {}

  //can add a positive number of beats with a reason
  //can spend points or beats manually with a reason
  //can spend points for something specific automatically
  //can differentiate between solar beats and general beats
  //tracks current / total
  //calculate essence based on total spent

  def emptyBeatBoxWithFree(amount : Int = 0, unit : Experiences.Unit = Point) : ExperienceBeatBox = {
    val x = unit.asBeats(amount)
    val timestamp = System.currentTimeMillis()
    val comment = s"Starting with $x free Beats"
    val y = ExperienceBeatBox(beats = ExperienceCategory(0, 0), manualEntries = IndexedSeq.empty)
    y.modifyBeatsManually(x, timestamp, comment).getOrElse(y)
  }



  @JSExportAll
  final case class ManualEntry(
                              amountPositive : Int,
                              note : String,
                              timestamp : Long
                              ) {
    val stringText : String = s"""${if(amountPositive > 0) "+" else ""}$amountPositive for reason: $note"""

  }


  @JSExportAll
  final case class ExperienceBeatBox(
                                     beats : ExperienceCategory,
                                     manualEntries : IndexedSeq[ManualEntry]
                                    ) {

    val pointsLeft : Int  = Beat.asPoints(beats.current)
    val beatsLeft : Int = beats.current - Point.asBeats(pointsLeft)
    val pointsTotal : Int = Beat.asPoints(beats.total)
    val beatsTotal : Int = beats.total - pointsTotal
    val pointsSpent : Int = Beat.asPoints(beats.spent)


    def essenceLevel : Int = {
      if (beats.spent >= 150 * 5 ) {
        5
      } else if (beats.spent >= 100 * 5 ) {
        4
      } else if (beats.spent >= 60 * 5 ) {
        3
      } else if (beats.spent >= 30 * 5 ) {
        2
      } else {
        1
      }
    }
    def beatsLeftForSpending
    //removed from rules: (typ : InvestmentType)
    : Int = beats.current


    def modifyBeats(amountPositive : Int) : Try[ExperienceBeatBox] = if (amountPositive > 0) {
      val current : Int = beats.current + amountPositive //increase
      val total : Int = beats.total + amountPositive //increase
      Success(copy(beats = ExperienceCategory(current, total)))
     } else {
      val current : Int = beats.current + amountPositive //decrease
      val total : Int = beats.total //stay the same
      if (current >= 0) {
        Success(copy(beats = ExperienceCategory(current, total)))
      } else {
        Failure(new IllegalStateException())
      }
    }

    def modifyXP(amountPositive : Int) : Try[ExperienceBeatBox] = modifyBeats(Point.asBeats(amountPositive))

    def modifyXPManually(amountPositive : Int, timestamp : Long, comment : String) : Try[ExperienceBeatBox] = modifyBeatsManually(Point.asBeats(amountPositive), timestamp, comment)

    def modifyBeatsManually(amountPositive : Int, timestamp : Long, comment : String): Try[ExperienceBeatBox] = {
      val me = ManualEntry(amountPositive, comment, timestamp)
      val b = modifyBeats(amountPositive)
      b.map(_.copy(manualEntries = manualEntries.+:(me)))
    }

  }
}
