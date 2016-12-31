package nephtys.loom.protocol.vanilla.solar

/**
  * Created by nephtys on 12/10/16.
  */
object Intimacies {

  val values = Seq(Defining, Major, Minor)

  private val definingstring : String = Defining.toString
  private val majorstring : String = Major.toString

  def parse(s : String) : Intensity = s match {
    case `definingstring` => Defining
    case `majorstring` => Major
    case _ => Minor
  }

  sealed trait Intensity {
    val modifier : Int
  }
  case object Minor extends Intensity {
    override val modifier: Int = 1
  }
  case object Major extends Intensity {
    override val modifier: Int = 2
  }
  case object Defining extends Intensity {
    override val modifier: Int = 4
  }
}
