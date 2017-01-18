package nephtys.loom.protocol.shared

import nephtys.loom.protocol.shared.CharmDatastructures.CharmType

import scala.scalajs.js.annotation.JSExportAll

/**
  * Created by Christopher on 17.01.2017.
  */
@JSExportAll
object CustomPowers {
  sealed trait CustomCost

  @JSExportAll
  final case class BonusPointCost(bp : Int) extends CustomCost

  @JSExportAll
  final case class ExperiencePointCost(xp : Int, useSolarXPToo : Boolean) extends CustomCost

  @JSExportAll
  final case class FreePointCost(freePoints : Int) extends CustomCost

  @JSExportAll
  sealed trait CustomPower {
    def essence : Int
    val name : String
    val description : String
    val cost : String
    val duration : String
    val keyword : Set[String]

    val customCost : CustomCost
  }

  @JSExportAll
  final case class CustomSolarCharm(essence : Int, ability : String, abilityRating : Int,
    charmtype : CharmType, name: String, description: String, cost: String,
                                    keyword: Set[String], duration: String, customCost : CustomCost

                                   ) extends CustomPower {
  }

  @JSExportAll
  final case class CustomEvocation(essence : Int, charmtype : CharmType,
                                   name: String, description: String,
                                   cost: String, keyword: Set[String],  duration: String, customCost : CustomCost
                                  ) extends CustomPower {

  }

  @JSExportAll
  final case class CustomSpell(circle : CharmDatastructures.Circle, name: String,
                               description: String, cost: String, keyword: Set[String],
                               duration: String, customCost : CustomCost
                              ) extends CustomPower {
    override def essence: Int = circle.essence
  }
}
