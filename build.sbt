
 lazy val root = (project in file(".")).
   settings(
     inThisBuild(List(
       organization := "ch.epfl.scala",
       scalaVersion := "2.13.8"
     )),
     name := "hello-world"
   )

libraryDependencies ++= Seq(
  "org.scalanlp" %% "breeze" % "2.1.0",
  "org.scalanlp" %% "breeze-natives" % "2.1.0"
)
//
libraryDependencies += "io.circe" %% "circe-yaml" % "0.14.2"
libraryDependencies += "com.lambdista" %% "config" % "0.8.1"
libraryDependencies += "com.typesafe" % "config" % "1.4.2"
libraryDependencies += "com.lihaoyi" %% "upickle" % "0.9.5"
