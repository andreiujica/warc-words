package org.bigdataanalysis.config

import com.typesafe.config.ConfigFactory

object AppConfig {
  private val config = ConfigFactory.load()

  object App {
    private val appConfig = config.getConfig("app")
    val name: String = appConfig.getString("name")
    val basePath: String = appConfig.getString("basePath")
    val topLanguagesLimit: Int = appConfig.getInt("topLanguagesLimit")
  }

  object Spark {
    private val sparkConfig = config.getConfig("spark")
    val serializer: String = sparkConfig.getString("serializer")
    val driverMemory: String = sparkConfig.getString("driverMemory")
    val executorMemory: String = sparkConfig.getString("executorMemory")
    val defaultParallelism: String = sparkConfig.getString("defaultParallelism")
    val shufflePartitions: String = sparkConfig.getString("shufflePartitions")
    val executorCores: String = sparkConfig.getString("executorCores")
  }
}

