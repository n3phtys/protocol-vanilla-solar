import sbt.Keys._
import sbt.ProjectRef

name := "protocol-vanilla-solar"

scalaVersion in ThisBuild := "2.11.8"



val genericFrontendUrl = "https://github.com/n3phtys/cqrs-client-frame.git"


val genericProtocolurl =  "https://github.com/n3phtys/cqrs-dual-frame.git"
lazy val genericFrontendProject = RootProject(uri(genericFrontendUrl))



lazy val genericProtocolProjectJS = ProjectRef(uri(genericProtocolurl), "cqrsdualframeJS")
lazy val genericProtocolProjectJVM = ProjectRef(uri(genericProtocolurl), "cqrsdualframeJVM")

lazy val root = project.in(file(".")).
  dependsOn(genericProtocolProjectJS, genericProtocolProjectJVM, genericProtocolProjectJS).
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

lazy val solarprotocolJVM = solarprotocol.jvm.dependsOn(genericProtocolProjectJVM)
lazy val solarprotocolJS = solarprotocol.js.dependsOn(genericProtocolProjectJS, genericProtocolProjectJS)