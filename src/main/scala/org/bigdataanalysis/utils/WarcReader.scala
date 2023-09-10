package org.bigdataanalysis.utils

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession
import de.l3s.concatgz.io.warc.{WarcGzInputFormat, WarcWritable}
import org.apache.hadoop.io.NullWritable
import org.bigdataanalysis.config.AppConfig

object WarcReader {

  def readWarcFiles(sparkSession: SparkSession): RDD[(NullWritable, WarcWritable)] = {
    val filePaths = (0 to 9).map(i => s"${AppConfig.App.basePath}CC-MAIN-20210410105831-20210410135831-0062${i}.warc.gz")
    val selectedFiles = filePaths.mkString(",")

    sparkSession.sparkContext.newAPIHadoopFile(
      selectedFiles,
      classOf[WarcGzInputFormat],
      classOf[NullWritable],
      classOf[WarcWritable]
    )
  }
}


