package com.brierley.bRelevant

import java.text.SimpleDateFormat

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

  def run(args: Array[String], sc: SparkContext, sqlContext: HiveContext): Unit = {

    val logger = Logger.getLogger(this.getClass)

    //getting the base DataFrames from bRelevant
    val getDFs = bRelevant.run(args, sc, sqlContext)

    //we don't need custProdMetric (getDFs._1) for explicit ratings, it could be used for implicit
    val custProdRank = getDFs._2
      .withColumn("Double_Rank_Metric", getDFs._2("Ranking_Metric").cast("Double"))
      .select("CUST_ID", "PRODUCT_CATEGORY_DESCR", "Double_Rank_Metric")
      .withColumnRenamed("Double_Rank_Metric", "Ranking_Metric")

    custProdRank.cache()

    val prodList = custProdRank
      .select("PRODUCT_CATEGORY_DESCR")
      .distinct()
      .withColumn("ProdNum",
        functions.row_number()
          .over(Window.partitionBy("PRODUCT_CATEGORY_DESCR")
            .orderBy("PRODUCT_CATEGORY_DESCR")))

    val custList = custProdRank
      .select("CUST_ID")
      .distinct()
      .withColumn("CustNum", functions.row_number()
        .over(Window.partitionBy("CUST_ID")
          .orderBy("CUST_ID")))

    val numCustProdRank = custProdRank
      .select("*")
      .join(custList, custProdRank("CUST_ID") === custList("CUST_ID"))
      .join(prodList, custProdRank("PRODUCT_CATEGORY_DESCR") === prodList("PRODUCT_CATEGORY_DESCR"))
      .select("CustNum", "ProdNum", "Ranking_Metric")

    val Array(training, test) = numCustProdRank.randomSplit(Array(0.8, 0.2))

    //they can play with values to tweak performance/accuracy
    val als = new ALS()
      .setMaxIter(args(4).toInt)
      .setRegParam(args(5).toDouble)
      .setUserCol("CustNum")
      .setItemCol("ProdNum")
      .setRatingCol("Ranking_Metric")

    val model = als.fit(training)

    val testPredictions = model.transform(test)

    //does anyone want this accuracy value?
    val evaluator = new RegressionEvaluator()
      .setMetricName("rmse")
      .setLabelCol("Ranking_Metric")
      .setPredictionCol("prediction")

    val rmse = evaluator.evaluate(testPredictions)
    logger.info("RMSE value for dataset: " + rmse)

    val predictions = model.transform(numCustProdRank)

//    val readablePredictions = predictions
//      .select("*")
//      .join(custList, predictions("CustNum") === custList("CustNum"))
//      .join(prodList, predictions("ProdNum") === prodList("ProdNum"))
    // .drop("ProdNum")
    // .drop("CustNum")

    val time = Calendar.getInstance().getTime()
    val formatTime = new SimpleDateFormat("YYYYMMDDHHmmss")
    val printTime = formatTime.format(time)

    val outputLocation = "users/Analytics/" + args(2) + "/" + sc.appName + "/" + printTime

    predictions.write.parquet(outputLocation + "/results/" + args(1))
    model.save(outputLocation + "/models/" + args(1) + "Model")


  }

}
