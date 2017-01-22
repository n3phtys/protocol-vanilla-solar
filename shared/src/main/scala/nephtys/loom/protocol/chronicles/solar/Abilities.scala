package nephtys.loom.protocol.chronicles.solar


import nephtys.loom.protocol.vanilla.solar.Abilities.{Ability, Normal, Type}

/**
  *
  * Created by Christopher on 21.01.2017.
  */
object Abilities {



  //can add abilities, set ratings, set types
  final case class AbilitySet(abilities : IndexedSeq[Ability], ratings : IndexedSeq[Int], types : IndexedSeq[Type], specialties : IndexedSeq[IndexedSeq[String]]) {
    def getTypeForAbility(abilityName : String) : Option[Type] = ???
    def getRatingForAbility(abilityName : String) : Option[Int] = ???
    def getAbilityToName(abilityName : String) : Option[Ability] = ???
    def getSpecialtiesToName(abilityName : String) : Option[IndexedSeq[String]] = ???

    def typeChangeWouldResultInSoManyXPCost(ability : Ability, typ : Type) : Option[Int] = ???
    def ratingChangeWouldResultInSoManyXPCost(ability : Ability, newRating : Int ) : Option[Int] = ???
    def specialtyAddOrRemoveChangeWouldResultInSoManyXPCost(ability : Ability, specialty : String, addSpecialty : Boolean) : Option[Int] = ???

    private val indices : Map[Ability, Int] = abilities.zipWithIndex.toMap

    //todo: count free dots and subtract from total points, multiplicate points by experience multiplicator
    //todo: also add specialties!
    def xpValue = ???

    //create new instances via:
    def addAbility(abilityName : String) : AbilitySet = ???
    def setRating(ability: Ability, rating : Int) : AbilitySet = ???
    def setType(ability: Ability, typ : Type) : AbilitySet = ???
  }

  def emptyMatrix : AbilitySet = {
    def l = nephtys.loom.protocol.vanilla.solar.Abilities.preprogrammed.flattenedList
    AbilitySet(l, l.map(_ => 0), l.map(_ => Normal), l.map(_ => IndexedSeq.empty))
  }



}
