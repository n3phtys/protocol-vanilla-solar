package nephtys.loom.protocol.vanilla.solar

import scala.scalajs.js.annotation.JSExportAll

/**
  * Created by nephtys on 12/10/16.
  */
@JSExportAll
object Equipments {
  sealed trait ModifiesParry {
    def parryModifier : Int
  }



  case class Weapon() extends Equipment
  case class Armor() extends Equipment
  case class Other() extends Equipment

  sealed trait Equipment {
    def name : String = ???

    /**
      * none if not committed (can select per item if it is committed or not)
      * @return
      */
    def commitmentCost : Option[Int] = ???
  }
  def emptyEquipmentList : List[Equipment] = List.empty[Equipment]

}
