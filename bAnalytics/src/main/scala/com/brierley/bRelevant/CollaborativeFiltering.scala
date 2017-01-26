package com.brierley.bRelevant

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

import org.apache.log4j.Logger
import org.apache.spark.SparkContext
import org.apache.spark.sql.hive.HiveContext
import org.apache.spark.ml.evaluation.RegressionEvaluator
import org.apache.spark.ml.recommendation.ALS
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.{DataFrame, functions}
import java.util.Calendar

/**
  * Created by amerrill on 1/4/17.
  */
object CollaborativeFiltering {

  def getCustProdRank(bRelevantDFs: (DataFrame, DataFrame)): DataFrame = {
    //we don't need custProdMetric (getDFs._1) for explicit ratings, it could be used for implicit
    val custProdRank = bRelevantDFs._2
      .withColumn("Double_Rank_Metric", bRelevantDFs._2("Ranking_Metric").cast("Double"))
      .select("CUST_ID", "CustNum", "PRODUCT_CATEGORY_DESCR", "ProdNum", "Double_Rank_Metric")

    custProdRank.cache()
    (custProdRank)
  }

  def createNumCustProdRank(custProdRank: DataFrame): DataFrame = {

    custProdRank
      .select("CustNum", "ProdNum", "Double_Rank_Metric")
      .withColumnRenamed("Double_Rank_Metric", "Ranking")

  }

  def createCustNumList(custProdRank: DataFrame): DataFrame = {
    custProdRank
      .select("CustNum", "CUST_ID")
      .distinct()
  }

  def createProdNumList(custProdRank: DataFrame): DataFrame = {
    custProdRank
      .select("ProdNum", "PRODUCT_CATEGORY_DESCR")
      .distinct()
  }

  def run(args: Array[String], sc: SparkContext, sqlContext: HiveContext): Unit = {

    val logger = Logger.getLogger(this.getClass)

    //getting the base DataFrames from bRelevant
    val getDFs = bRelevant.run(args, sc, sqlContext)

    val custProdRank = getCustProdRank(getDFs)

    val numCustProdRank = createNumCustProdRank(custProdRank)

    val custNums = createCustNumList(custProdRank)
    val prodNums = createProdNumList(custProdRank)

    val Array(training, test) = numCustProdRank.randomSplit(Array(0.8, 0.2))

    //they can play with values to tweak performance/accuracy
    val als = new ALS()
      .setMaxIter(args(4).toInt)
      .setRegParam(args(5).toDouble)
      .setUserCol("CustNum")
      .setItemCol("ProdNum")
      .setRatingCol("Ranking")

    val model = als.fit(training)

    val testPredictions = model.transform(test)

    val trainPredictions = model.transform(training)

    //does anyone want this accuracy value? -- returns NaN -- there is a jira ticket open around what to do about this
    val evaluator = new RegressionEvaluator()
      .setMetricName("rmse")
      .setLabelCol("Ranking")
      .setPredictionCol("prediction")

    val rmse = evaluator.evaluate(trainPredictions)
    logger.info("RMSE value for dataset: " + rmse)

    val predictions = model.transform(numCustProdRank)

    val readablePredictions = predictions
      .join(custNums, "CustNum")
      .join(prodNums, "ProdNum")

    val currentTime = LocalDateTime.now.format(DateTimeFormatter.ofPattern("YYYYMMDDHHmmss"))

    val outputLocation = "users/Analytics/" + args(2) + "/" + sc.appName + "/" + currentTime

    readablePredictions.write.format("com.databricks.spark.csv").save("results/" + args(2) + "/CollFiltering")
    model.write.overwrite().save("model/" + args(2))
    //readablePredictions.write.parquet(outputLocation + "/results/" + args(1))
    //model.save(outputLocation + "/models/" + args(1) + "Model")

  }

}
