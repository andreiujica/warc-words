package org.bigdataanalysis.config

import org.apache.spark.SparkConf

object SparkConfigurator {

  def configureSpark(): SparkConf = {
    new SparkConf()
      .setAppName(AppConfig.App.name)
      .set("spark.serializer", AppConfig.Spark.serializer)
      .registerKryoClasses(Array(classOf[de.l3s.concatgz.data.WarcRecord]))
      .set("spark.driver.memory", AppConfig.Spark.driverMemory)
      .set("spark.executor.memory", AppConfig.Spark.executorMemory)
      .set("spark.default.parallelism", AppConfig.Spark.defaultParallelism)
      .set("spark.sql.shuffle.partitions", AppConfig.Spark.shufflePartitions)
      .set("spark.executor.cores", AppConfig.Spark.executorCores)
  }
}

