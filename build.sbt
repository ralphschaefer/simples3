
name := "s3"

organization := "my.s3test"

scalaVersion := "2.13.4"


lazy val root = (project in file("."))
  .settings(
    scalacOptions += "-deprecation",
    libraryDependencies ++= Seq(
      "ch.qos.logback" % "logback-classic" % "1.2.11",
      "com.typesafe.scala-logging" %% "scala-logging" % "3.9.5",
      "com.github.pureconfig" %% "pureconfig" % "0.17.1",
      "com.amazonaws" % "aws-java-sdk-s3" % "1.12.239",
      // "com.amazonaws" % "aws-java-sdk" % "1.12.239",
      "com.github.javafaker" % "javafaker" % "1.0.2"
    ),
    assembly / assemblyOutputPath := new File(target.value + "/" + name.value + ".jar"),
    assembly / assemblyMergeStrategy := {
      case "module-info.class" => MergeStrategy.discard
      case "META-INF/io.netty.versions.properties" => MergeStrategy.discard
      case x =>
        val oldStrategy = (assembly / assemblyMergeStrategy).value
        oldStrategy(x)
    }
)


