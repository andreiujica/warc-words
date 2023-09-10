package org.bigdataanalysis

import org.bigdataanalysis.config.{AppConfig, SparkConfigurator}
import org.bigdataanalysis.utils.{WarcReader, TextAnalyzer}
import org.apache.spark.sql.SparkSession

object Main {
  def main(args: Array[String]): Unit = {
    val sparkConf = SparkConfigurator.configureSpark()

    val sparkSession = SparkSession.builder().config(sparkConf).getOrCreate()

    val warcs = WarcReader.readWarcFiles(sparkSession)
    val languageData = TextAnalyzer.extractLanguageCountsAndComplexity(sparkSession, warcs)
    TextAnalyzer.displayTopLanguageCountsAndComplexity(languageData, AppConfig.App.topLanguagesLimit)

    sparkSession.stop()
  }
}

