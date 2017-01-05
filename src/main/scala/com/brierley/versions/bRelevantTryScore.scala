package com.brierley.versions

import org.apache.log4j.Logger
import com.brierley.bRelevant._
import org.apache.spark.sql.hive.HiveContext
import org.apache.spark.{SparkConf, SparkContext}
/**
  * Created by amerrill on 1/3/17.
  */
object bRelevantTryScore {

  def time[R](block: => R): R = {
    val t0 = System.nanoTime()
    val result = block    // call-by-name
    val t1 = System.nanoTime()
    println("Elapsed time: " + (t1 - t0) + "ns")
    result
  }

  def main(args: Array[String]) {
    val logger = Logger.getLogger(this.getClass)
    println("=> Starting bRelevant at time")

    if (args.length < 4) {
      logger.error("=> Wrong number of parameters")
      System.err.println("Usage: bRelevantTryScore CustomerFile FinalOutputFile user cutOffDate (05/10/2016)")
      System.exit(1)
    }

    val jobName = "bRelevantTryScores"
    val conf = new SparkConf().setAppName(jobName)
      .set("spark.driver.maxResultSize", "3g")
      .set("spark.sql.autoBroadcastJoinThreshold","500000000")
      .set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
      .set("spark.speclation", "true")

    val sc = new SparkContext(conf)

    val sqlContext = new HiveContext(sc)

    TryScores.run(args, sc, sqlContext)
  }
}
