name := "Encry"

version := "0.1"

scalaVersion := "2.12.4"

val scorexVersion = "2.0.0-RC3"

libraryDependencies ++= Seq(
  "org.scorexfoundation" %% "iodb" % "0.3.2",
  "org.scorexfoundation" %% "scorex-core" % scorexVersion,
  "org.scorexfoundation" %% "avl-iodb" % "0.2.11",
  "com.storm-enroute" %% "scalameter" % "0.8.+",
  "com.iheart" %% "ficus" % "1.4.+",
)
        