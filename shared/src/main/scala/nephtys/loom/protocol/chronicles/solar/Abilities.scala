package nephtys.loom.protocol.chronicles.solar


import nephtys.loom.protocol.vanilla.solar.Abilities.{Ability, Normal, Specialty, Type}

/**
  *
  * Created by Christopher on 21.01.2017.
  */
object Abilities {



  //can add abilities, set ratings, set types
  final case class AbilitySet(abilities : IndexedSeq[Ability], ratings : IndexedSeq[Int], types : IndexedSeq[Type], specialties : IndexedSeq[IndexedSeq[String]]) {
    def getTypeForAbility(abilityName : String) : Option[Type] = abilitiesMap.get(abilityName).flatMap(a => indicesMap.get(a)).map(i => types(i))
    def getRatingForAbility(abilityName : String) : Option[Int] = abilitiesMap.get(abilityName).flatMap(a => indicesMap.get(a)).map(i => ratings(i))
    def getAbilityToName(abilityName : String) : Option[Ability] = abilitiesMap.get(abilityName)
    def getSpecialtiesToName(abilityName : String) : Option[IndexedSeq[String]] = abilitiesMap.get(abilityName).flatMap(a => indicesMap.get(a)).map(i => specialties(i))

    def ratingChangeWouldResultInSoManyXPCost(ability : Ability, newRating : Int ) : Option[Int] = indicesMap.get(ability).map(i => {
      val dx : Int = newRating - ratings(i)
      dx * Experiences.Multiplicators.AbilityDot
    })
    def specialtyAddOrRemoveChangeWouldResultInSoManyXPCost(ability : Ability, specialty : String, addSpecialty : Boolean) : Option[Int] = indicesMap.get(ability).map(i => {
      val list = specialties(i)
      if (addSpecialty && !list.contains(specialty)) {
        +1 * Experiences.Multiplicators.Specialty
      } else if (!addSpecialty && list.contains(specialty)) {
        -1 * Experiences.Multiplicators.Specialty
      } else {
        0 * Experiences.Multiplicators.Specialty
      }
    })

    private val indicesMap : Map[Ability, Int] = abilities.zipWithIndex.toMap
    private val abilitiesMap : Map[String, Ability] = abilities.map(s => (s.name, s)).toMap

    val xpValue : Int = {
      val breaker : Int = 3
      val pointsOver3 : Int = ratings.map(i => Math.max(0, i - breaker)).sum
      val pointsUpTo3 : Int = ratings.map(i => Math.min(breaker, i)).sum
      val specialties : Int = this.specialties.map(_.size).sum
      ((Math.max(0, pointsUpTo3 - 28) + pointsOver3) * Experiences.Multiplicators.AbilityDot) + (specialties * Experiences.Multiplicators.Specialty)
    }

    def contains(abilityName : String): Boolean = getAbilityToName(abilityName).isDefined

    //create new instances via:
    def addAbility(abilityName : String) : AbilitySet = {
      val a : Ability = Ability(abilityName)
      val elementsBeforeIndex : Int = abilities.indices.find(i => {
        //special case first
        if (i == 0) {
          abilities(i).after(abilityName)
        } else if (i == abilities.size - 1) {
          //special case last
          abilities(i).before(abilityName)
        } else {
          //normal case
          abilities(i).before(abilityName) && abilities(i+1).after(abilityName)
        }
      }).getOrElse(0)
      val newRating = 0
      val newSpecialties = IndexedSeq.empty
      val newType = Normal
      copy(
        abilities = abilities.take(elementsBeforeIndex).+:(a) ++ abilities.drop(elementsBeforeIndex),
        ratings = ratings.take(elementsBeforeIndex).+:(newRating) ++ ratings.drop(elementsBeforeIndex),
        types = types.take(elementsBeforeIndex).+:(newType) ++ types.drop(elementsBeforeIndex),
        specialties = specialties.take(elementsBeforeIndex).+:(newSpecialties) ++ specialties.drop(elementsBeforeIndex)
      )
    }
    def setRating(ability: Ability, rating : Int) : AbilitySet = {
      val index = indicesMap.getOrElse(ability, 0)
      val llist : IndexedSeq[Int] = this.ratings.patch(from = index, patch = IndexedSeq(rating), replaced = 1)
      copy(ratings = llist)
    }
    def setType(ability: Ability, typ : Type) : AbilitySet = {
      val index = indicesMap.getOrElse(ability, 0)
      val llist : IndexedSeq[Type] = this.types.patch(from = index, patch = IndexedSeq(typ), replaced = 1)
      copy(types = llist)
    }
    def addSpecialty(ability: Ability, specialty: String) : AbilitySet = {
        val index = indicesMap.getOrElse(ability, 0)
      val list : IndexedSeq[String] = this.specialties(index).+:(specialty)
      val llist : IndexedSeq[IndexedSeq[String]] = this.specialties.patch(from = index, patch = IndexedSeq(list), replaced = 1)
        copy(specialties = llist)
    }
  }

  def emptyMatrix : AbilitySet = {
    def l = nephtys.loom.protocol.vanilla.solar.Abilities.preprogrammed.flattenedList
    AbilitySet(l, l.map(_ => 0), l.map(_ => Normal), l.map(_ => IndexedSeq.empty))
  }



}
