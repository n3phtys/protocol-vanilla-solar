import sbt.Keys._

name := "protocol-vanilla-solar"

scalaVersion in ThisBuild := "2.11.8"




val genericProtocolurl =  "https://github.com/n3phtys/cqrs-dual-frame.git"


lazy val genericProtocolProject = RootProject(uri(genericProtocolurl))

lazy val root = project.in(file(".")).
  dependsOn(genericProtocolProject).
  aggregate(solarprotocolJS, solarprotocolJVM).
  settings(
    publish := {},
    publishLocal := {}
  )



lazy val solarprotocol = crossProject.in(file(".")).
  settings(
    name := "protocol-vanilla-solar",
    organization := "org.bitbucket.nephtys",
    version := "0.0.0",

    libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.0",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.0" % "test",
    libraryDependencies += "com.lihaoyi" %% "upickle" % "0.4.3"

  )//.configureJVM(_.dependsOn(genericProtocolProject))
    //.configureJS(_.dependsOn(genericProtocolProject))
  .
  jvmSettings(
    // Add JVM-specific settings here
    mainClass in run := Some("nephtys.loom.protocol.vanilla.solar.Main"),
    libraryDependencies += "org.scala-js" %% "scalajs-stubs" % scalaJSVersion % "provided"
  ).
  jsSettings(
    // Add JS-specific settings here
  )

lazy val solarprotocolJVM = solarprotocol.jvm.dependsOn(genericProtocolProject)
lazy val solarprotocolJS = solarprotocol.js.dependsOn(genericProtocolProject)