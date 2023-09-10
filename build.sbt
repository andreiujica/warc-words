name := "BigDataAnalysis"

version := "1.0"

scalaVersion := "2.12.10"

val sparkV = "3.1.2"
val hadoopV = "3.3.1"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkV % "provided",
  "org.apache.spark" %% "spark-sql" % sparkV % "provided",
  "org.apache.hadoop" % "hadoop-client" % hadoopV % "provided",
  "org.jsoup" % "jsoup" % "1.11.3",
  "com.typesafe" % "config" % "1.4.1",
  "com.github.helgeho" % "hadoop-concat-gz" % "1.2.3-preview" from
    "file:///opt/hadoop/rubigdata/lib/hadoop-concat-gz-1.2.3-preview.jar"
)

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}

