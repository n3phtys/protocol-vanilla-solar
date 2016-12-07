name := "protocol-vanilla-solar"

version := "0.0.0"


mainClass in run := Some("nephtys.loom.protocol.vanilla.solar.Main")

scalaVersion := "2.11.8"

resolvers += "softprops-maven" at "http://dl.bintray.com/content/softprops/maven"


val genericProtocolurl =  "https://github.com/n3phtys/cqrs-dual-frame.git"


lazy val genericProtocolProject = RootProject(uri(genericProtocolurl))


// Library dependencies
lazy val root = Project("protocol-vanilla-solar", file("."))
  .settings(
    publish := {},
    publishLocal := {}
  )
  .dependsOn(genericProtocolProject)
  .settings(
    libraryDependencies ++= Seq(
    ))

