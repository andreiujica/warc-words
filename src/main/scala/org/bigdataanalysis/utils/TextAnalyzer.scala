package org.bigdataanalysis.utils

import org.apache.spark.rdd.RDD
import org.apache.hadoop.io.NullWritable
import de.l3s.concatgz.io.warc.WarcWritable
import org.apache.spark.sql.{Row, SparkSession, DataFrame}
import org.apache.spark.sql.types.{StructType, StructField, StringType, IntegerType, DoubleType}
import org.apache.spark.sql.functions._
import org.jsoup.Jsoup
import scala.collection.mutable
import java.text.BreakIterator

object TextAnalyzer {

  def extractLanguageCountsAndComplexity(sparkSession: SparkSession, warcRDD: RDD[(NullWritable, WarcWritable)]): DataFrame = {
    val wordsRDD = warcRDD.flatMap { case (_, warcWritable) =>
      val httpBody = warcWritable.getRecord.getHttpStringBody()
      if(httpBody != null) {
        val d = Jsoup.parse(httpBody)
        val lang = d.select("html").attr("lang")
        if (lang.nonEmpty) {
          val (avgSentenceLength, vocabRichness) = analyzeTextComplexity(d.text(), lang)
          Seq(Row(lang, 1, avgSentenceLength, vocabRichness))
        } else {
          Seq.empty[Row]
        }
      } else {
        Seq.empty[Row]
      }
    }

    val schema = StructType(Seq(
      StructField("language", StringType, nullable = false),
      StructField("count", IntegerType, nullable = false),
      StructField("average_sentence_length", DoubleType, nullable = true),
      StructField("vocabulary_richness", DoubleType, nullable = true)
    ))

    val wordsDF = sparkSession.createDataFrame(wordsRDD, schema)
    wordsDF.groupBy("language")
      .agg(
        sum("count").alias("count"),
        avg("average_sentence_length").alias("average_sentence_length"),
        avg("vocabulary_richness").alias("vocabulary_richness")
      )
      .sort(desc("count"))
  }


  private def analyzeTextComplexity(text: String, language: String): (Double, Double) = {
    val locale = new java.util.Locale(language)
    val wordTokenizer = BreakIterator.getWordInstance(locale)
    val sentenceTokenizer = BreakIterator.getSentenceInstance(locale)
    wordTokenizer.setText(text)
    sentenceTokenizer.setText(text)

    val wordCount = mutable.Map[String, Int]()
    var totalWords = 0
    var prevWordBoundary = wordTokenizer.first()
    var wordBoundary = wordTokenizer.next()
    while (wordBoundary != BreakIterator.DONE) {
      val word = text.substring(prevWordBoundary, wordBoundary).trim
      if (word.matches("\\p{L}+")) {
        totalWords += 1
        wordCount(word) = wordCount.getOrElse(word, 0) + 1
      }
      prevWordBoundary = wordBoundary
      wordBoundary = wordTokenizer.next()
    }

    var totalSentences = 0
    var prevSentenceBoundary = sentenceTokenizer.first()
    var sentenceBoundary = sentenceTokenizer.next()
    while (sentenceBoundary != BreakIterator.DONE) {
      totalSentences += 1
      prevSentenceBoundary = sentenceBoundary
      sentenceBoundary = sentenceTokenizer.next()
    }

    val averageSentenceLength = if (totalSentences > 0) totalWords.toDouble / totalSentences else 0.0
    val vocabularyRichness = wordCount.size.toDouble / totalWords

    (averageSentenceLength, vocabularyRichness)
  }

  def displayTopLanguageCountsAndComplexity(languageData: DataFrame, limit: Int): Unit = {
    languageData.take(limit).foreach(println)
  }
}

