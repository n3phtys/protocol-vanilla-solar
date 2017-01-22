package nephtys.loom.protocol.chronicles.solar

/**
  * Created by Christopher on 21.01.2017.
  */
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


  sealed trait InvestmentType
  case object Solar extends InvestmentType
  case object General extends InvestmentType

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
  final case class Investment(amount : Int, unit : Experiences.Unit, typ : InvestmentType) {}

  //can add a positive number of beats with a reason
  //can spend points or beats manually with a reason
  //can spend points for something specific automatically
  //can differentiate between solar beats and general beats
  //tracks current / total
  //calculate essence based on total spent

  def emptyBeatBoxWithFree(amount : Int, unit : Experiences.Unit) : ExperienceBeatBox = {
      ???
  }

  final case class ExperienceBeatBox(
                                    ) {
    def essenceLevel : Int = ???
    def beatsLeftForSpending(typ : InvestmentType) : Int = ???
  }
}
