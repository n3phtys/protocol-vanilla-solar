package nephtys.loom.protocol.vanilla.solar

import java.util.UUID

import org.nephtys.loom.generic.protocol.InternalStructures.{Email, ID}

/**
  * Created by nephtys on 12/7/16.
  */
object Main extends App{
println("Hello World")
  println("Generated character empty:")
  println(Characters.emptySolar(ID[Solar](UUID.randomUUID()), Email("someone@somewhere.com")))
}
