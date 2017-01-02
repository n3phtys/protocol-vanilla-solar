package nephtys.loom.protocol.vanilla.solar

/**
  * Created by nephtys on 1/2/17.
  */
object Exceptions {
  class MissingFreePointsException() extends Exception
  class MissingExperienceException() extends Exception
  class MissingBonusPointsException() extends Exception

  def missXP() = new MissingExperienceException()

  def missFP() = new MissingExperienceException()

    def missBP() = new MissingBonusPointsException()

}
