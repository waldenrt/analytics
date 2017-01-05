package com.brierley.bRelevant

import java.text.SimpleDateFormat
import java.util.Calendar

import org.apache.log4j.Logger
import org.apache.spark.ml.{Pipeline, PipelineStage}
import org.apache.spark.ml.classification.{LogisticRegression, LogisticRegressionModel}
import org.apache.spark.ml.feature.VectorAssembler
import org.apache.spark.SparkContext
import org.apache.spark.sql.hive.HiveContext
import org.apache.spark.sql.functions._
import com.brierley.utils.DataframeUtils._
import org.apache.spark.ml.feature.Normalizer
import org.apache.spark.sql.functions.lit

import scala.collection.mutable.ArrayBuffer

/**
  * Created by amerrill on 1/3/17.
  */
object TryScores {

  def run(args: Array[String], sc: SparkContext, sqlContext: HiveContext): Unit = {

    val logger = Logger.getLogger(this.getClass)

    //getting the base DataFrames from bRelevant
    //for sure custProdRank should be cached, custProdMetric?
    val (custProdMetric, custProdRank) = bRelevant.run(args, sc, sqlContext)
    custProdRank.cache()

    val totalProductCount = custProdMetric.agg(countDistinct("PRODUCT_CATEGORY_DESCR")).collect()
    val totalProductCountArray = totalProductCount.map(t => t(0))
    val totalProductCountValue = totalProductCountArray(0).asInstanceOf[Long]

    logger.info("Creating the custRBar file")

    val custRBar = custProdRank
      .select("*")
      .groupBy("CAPTURED_LOYALTY_ID")
      .agg(
        (avg("Ranking_Metric") * (max("number_products_purchased") / totalProductCountValue)).as("R_BAR"),
        (avg("RFM_Term") * (max("number_products_purchased") / totalProductCountValue)).as("R_BAR_RFM")
      )

    logger.info("Creating the itemLHS file")

    val itemLHS = custProdRank
      .select("*")
      .join(custRBar)
      .select(custProdRank("CAPTURED_LOYALTY_ID").as("CUST_ID"),
        custProdRank("PRODUCT_CATEGORY_DESCR").as("LHS"),
        (custProdRank("Ranking_Metric") - custRBar("R_BAR")).as("R_LEFT"),
        (custProdRank("RFM_TERM") - custRBar("R_BAR_RFM")).as("R_LEFT_RFM")
      )

    logger.info("Creating the itemRHS file")

    val itemRHS = custProdRank
      .select("*")
      .join(custRBar)
      .select(custProdRank("CAPTURED_LOYALTY_ID").as("CUST_ID"),
        custProdRank("PRODUCT_CATEGORY_DESCR").as("RHS"),
        (custProdRank("Ranking_Metric") - custRBar("R_BAR")).as("R_RIGHT"),
        (custProdRank("RFM_TERM") - custRBar("R_BAR_RFM")).as("R_RIGHT_RFM")
      )

    logger.info("Creating the arule_1w file")

    val arule_1w = itemLHS
      .select("*")
      .join(itemRHS, itemLHS("CUST_ID") === itemRHS("CUST_ID") && itemLHS("LHS") != itemRHS("RHS"))
      .drop(itemRHS("CUST_ID"))
      .groupBy(itemLHS("LHS"), itemRHS("RHS"))
      .agg(count("CUST_ID").as("NUM_LHS_RHS"),
        when(sum(itemLHS("R_LEFT") * itemRHS("R_RIGHT")) > 0,
          sum(itemLHS("R_LEFT") * itemRHS("R_RIGHT")) / (sqrt(sum(pow(itemLHS("R_LEFT"), 2))) * sqrt(sum(pow(itemRHS("R_RIGHT"), 2)))))
          .otherwise(0).as("COS_SIM"),
        when(sum(itemLHS("R_LEFT_RFM") * itemRHS("R_RIGHT_RFM")) > 0,
          sum(itemLHS("R_LEFT_RFM") * itemRHS("R_RIGHT_RFM")) / (sqrt(sum(pow(itemLHS("R_LEFT_RFM"), 2))) * sqrt(sum(pow(itemRHS("R_RIGHT_RFM"), 2)))))
          .otherwise(0).as("SIM_MEASURE_RFM")
      )
      .select("LHS", "RHS", "NUM_LHS_RHS", "COS_SIM", "SIM_MEASURE_RFM")

    logger.info("Creating the sum_lhs file")

    val sum_lhs = custProdRank
      .select("*")
      .groupBy("PRODUCT_CATEGORY_DESCR")
      .agg(count("PRODUCT_CATEGORY_DESCR").as("NUM_LHS"))
      .select("PRODUCT_CATEGORY_DESCR",
        "NUM_LHS")
      .withColumnRenamed("PRODUCT_CATEGORY_DESCR", "LHS")

    logger.info("Creating the sum_rhs file")

    val sum_rhs = custProdRank
      .select("*")
      .groupBy("PRODUCT_CATEGORY_DESCR")
      .agg(count("PRODUCT_CATEGORY_DESCR").as("NUM_RHS"))
      .select("PRODUCT_CATEGORY_DESCR",
        "NUM_RHS")
      .withColumnRenamed("PRODUCT_CATEGORY_DESCR", "RHS")

    val numBasket = custProdRank.agg(countDistinct("CAPTURED_LOYALTY_ID")).collect()
    val numBasketArray = numBasket.map(t => t(0))
    val numBasketValue = numBasketArray(0).asInstanceOf[Long]

    logger.info("Creating the arule_1w_step2 file")

    val arule_1w_step2 = arule_1w
      .join(sum_lhs, "LHS")
      .join(sum_rhs, "RHS")
      .select(
        "LHS",
        "RHS",
        "NUM_LHS_RHS",
        "COS_SIM",
        "SIM_MEASURE_RFM",
        "NUM_LHS",
        "NUM_RHS"
      )
      .withColumn("NUM_BASKET", lit(numBasketValue))
      .withColumn("NUM_NO_LHS", lit(numBasketValue) - sum_lhs("NUM_LHS"))
      .withColumn("NUM_NO_RHS", lit(numBasketValue) - sum_rhs("NUM_RHS"))
      .withColumn("NUM_LHS_NO_RHS", sum_lhs("NUM_LHS") - arule_1w("NUM_LHS_RHS"))
      .withColumn("NUM_NO_LHS_RHS", sum_rhs("NUM_RHS") - arule_1w("NUM_LHS_RHS"))
      .withColumn("NUM_NO_LHS_NO_RHS", lit(numBasketValue) -
        sum_lhs("NUM_LHS") -
        sum_rhs("NUM_RHS") +
        arule_1w("NUM_LHS_RHS"))
      .withColumn("EXP_LHS_RHS", sum_lhs("NUM_LHS") *
        sum_rhs("NUM_RHS") /
        lit(numBasketValue))
      .withColumn("EXP_LHS_NO_RHS", (sum_lhs("NUM_LHS") *
        (lit(numBasketValue) - sum_rhs("NUM_RHS"))) /
        lit(numBasketValue))
      .withColumn("EXP_NO_LHS_RHS", (sum_rhs("NUM_RHS") *
        (lit(numBasketValue) - sum_lhs("NUM_LHS"))) /
        lit(numBasketValue))
      .withColumn("EXP_NO_LHS_NO_RHS", (lit(numBasketValue) -
        sum_lhs("NUM_LHS")) *
        (lit(numBasketValue) -
          sum_rhs("NUM_RHS")))
      .withColumn("SUPPORT", arule_1w("NUM_LHS_RHS") /
        lit(numBasketValue))
      .withColumn("CONFIDENCE", arule_1w("NUM_LHS_RHS") /
        sum_lhs("NUM_LHS"))
      .withColumn("LIFT", (arule_1w("NUM_LHS_RHS") *
        lit(numBasketValue)) /
        (sum_lhs("NUM_LHS") *
          sum_rhs("NUM_RHS")))
      .withColumn("TOTAL", lit(numBasketValue))
      .orderBy("LHS")

    logger.info("Creating the arule item with measure file")

    val arule_item_with_measure =
      arule_1w_step2.select("*")
        .withColumn("CHI_SQUARE",
          chiSquare(arule_1w_step2("EXP_LHS_RHS"),
            arule_1w_step2("NUM_LHS_RHS"),
            arule_1w_step2("EXP_LHS_NO_RHS"),
            arule_1w_step2("NUM_LHS_NO_RHS"),
            arule_1w_step2("EXP_NO_LHS_RHS"),
            arule_1w_step2("NUM_NO_LHS_RHS"),
            arule_1w_step2("EXP_NO_LHS_NO_RHS"),
            arule_1w_step2("NUM_NO_LHS_NO_RHS")))
        .withColumn("LAPLACE",
          laplace(arule_1w_step2("NUM_LHS_RHS"),
            arule_1w_step2("NUM_LHS")))
        .withColumn("CONVICTION",
          conviction(arule_1w_step2("NUM_BASKET"),
            arule_1w_step2("NUM_LHS_NO_RHS"),
            arule_1w_step2("NUM_LHS"),
            arule_1w_step2("NUM_NO_RHS")))
        .withColumn("ADDED_VALUE",
          addedValue(arule_1w_step2("NUM_LHS_RHS"),
            arule_1w_step2("NUM_LHS"),
            arule_1w_step2("NUM_RHS"),
            arule_1w_step2("NUM_BASKET")))
        .withColumn("CERTAINTY_FACTOR",
          certaintyFactor(arule_1w_step2("NUM_LHS_RHS"),
            arule_1w_step2("NUM_LHS"),
            arule_1w_step2("NUM_RHS"),
            arule_1w_step2("NUM_BASKET")))
        .withColumn("J_MEASURE",
          jMeasure(arule_1w_step2("NUM_BASKET"),
            arule_1w_step2("NUM_LHS_NO_RHS"),
            arule_1w_step2("NUM_LHS_RHS"),
            arule_1w_step2("NUM_LHS"),
            arule_1w_step2("NUM_RHS"),
            arule_1w_step2("NUM_NO_RHS")))
        .withColumn("GINI_INDEX",
          giniIndex(arule_1w_step2("NUM_LHS"),
            arule_1w_step2("NUM_BASKET"),
            arule_1w_step2("NUM_LHS_RHS"),
            arule_1w_step2("NUM_LHS_NO_RHS"),
            arule_1w_step2("NUM_RHS"),
            arule_1w_step2("NUM_NO_LHS"),
            arule_1w_step2("NUM_NO_LHS_NO_RHS"),
            arule_1w_step2("NUM_NO_RHS"),
            arule_1w_step2("NUM_NO_LHS_RHS")))
        .withColumn("JACCARD",
          jaccard(arule_1w_step2("NUM_LHS_RHS"),
            arule_1w_step2("NUM_LHS"),
            arule_1w_step2("NUM_RHS")))
        .withColumn("SHAPIRO",
          shapiro(arule_1w_step2("NUM_LHS_RHS"),
            arule_1w_step2("NUM_BASKET"),
            arule_1w_step2("NUM_RHS"),
            arule_1w_step2("NUM_LHS")))
        .withColumn("COSINE",
          cosine(arule_1w_step2("NUM_LHS_RHS"),
            arule_1w_step2("NUM_LHS"),
            arule_1w_step2("NUM_RHS")))
        .withColumn("CORRELATION",
          correlation(arule_1w_step2("NUM_LHS_RHS"),
            arule_1w_step2("NUM_BASKET"),
            arule_1w_step2("NUM_LHS"),
            arule_1w_step2("NUM_RHS"),
            arule_1w_step2("NUM_NO_LHS"),
            arule_1w_step2("NUM_NO_RHS")))
        .withColumn("ODDS_RATIO",
          oddsRatio(arule_1w_step2("NUM_LHS_NO_RHS"),
            arule_1w_step2("NUM_NO_LHS_RHS"),
            arule_1w_step2("NUM_LHS_RHS"),
            arule_1w_step2("NUM_NO_LHS_NO_RHS")))
        .withColumn("SIM_SCORE",
          simScore(arule_1w_step2("LIFT"),
            arule_1w_step2("COS_SIM"),
            arule_1w_step2("SUPPORT"),
            arule_1w_step2("NUM_RHS")))
        .orderBy("LHS")

    logger.info("Creating the cust_lhs_rhs file")

    val cust_lhs_rhs = custProdRank
      .join(arule_item_with_measure, custProdRank("PRODUCT_CATEGORY_DESCR") === arule_item_with_measure("LHS"))
      .select(custProdRank("CAPTURED_LOYALTY_ID").as("CUST_ID"),
        custProdRank("PRODUCT_CATEGORY_DESCR").as("LHS"),
        custProdRank("CUST_PROD_SALES"),
        custProdRank("CUST_PROD_MIN_REC"),
        custProdRank("CUST_PROD_QTY"),
        custProdRank("RANKING_METRIC"),
        custProdRank("RFM_TERM"),
        arule_item_with_measure("RHS"),
        arule_item_with_measure("COS_SIM"),
        arule_item_with_measure("SIM_MEASURE_RFM").as("RFM_SIM"),
        arule_item_with_measure("SUPPORT").as("SUPPORT_SIM"),
        arule_item_with_measure("CONFIDENCE").as("CONFIDENCE_SIM"),
        arule_item_with_measure("LIFT").as("LIFT_SIM"),
        arule_item_with_measure("CHI_SQUARE").as("CHI_SQUARE_SIM"),
        arule_item_with_measure("LAPLACE").as("LAPLACE_SIM"),
        arule_item_with_measure("CONVICTION").as("CONVICTION_SIM"),
        arule_item_with_measure("ADDED_VALUE").as("ADDED_VALUE_SIM"),
        arule_item_with_measure("CERTAINTY_FACTOR").as("CERTAINTY_FACTOR_SIM"),
        arule_item_with_measure("J_MEASURE").as("J_MEASURE_SIM"),
        arule_item_with_measure("GINI_INDEX").as("GINI_INDEX_SIM"),
        arule_item_with_measure("JACCARD").as("JACCARD_SIM"),
        arule_item_with_measure("SHAPIRO").as("SHAPIRO_SIM"),
        arule_item_with_measure("COSINE").as("COSINE_SIM"),
        arule_item_with_measure("CORRELATION").as("CORRELATION_SIM"),
        arule_item_with_measure("ODDS_RATIO").as("ODDS_RATIO_SIM"),
        arule_item_with_measure("SIM_SCORE"),
        arule_item_with_measure("TOTAL").as("REC_COUNT")
      )
      .cache()

    val recProduct = cust_lhs_rhs
      .select("CUST_ID", "RHS")
      .orderBy("CUST_ID", "RHS")
      .rdd
      .map(tokens => (tokens(0), List(tokens(1))))
      .reduceByKey((a, b) => a ::: b)
      .collectAsMap()

    val recProductBroadcast = sc.broadcast(recProduct)

    val cust_lhs_rhs_with_target = cust_lhs_rhs
      .select("CUST_ID", "RHS")
      .withColumn("TARGET",
        if (recProductBroadcast
          .value
          .get(cust_lhs_rhs("CUST_ID")).get
          .contains(cust_lhs_rhs("LHS"))) {
          lit(1)
        } else {
          lit(0)
        }
      )


    logger.info("Creating the suggOffers file")

    val suggOffers = cust_lhs_rhs
      .groupBy(cust_lhs_rhs("CUST_ID"), cust_lhs_rhs("RHS"))
      .agg(when(
        sum(cust_lhs_rhs("COS_SIM")) > 0,
        sum(cust_lhs_rhs("COS_SIM") *
          cust_lhs_rhs("RANKING_METRIC")) /
          sum(cust_lhs_rhs("COS_SIM")))
        .otherwise(0).as("RFM_SIM_SCR_MSI"),
        when(
          sum(cust_lhs_rhs("RFM_SIM")) > 0,
          sum(cust_lhs_rhs("RFM_SIM") *
            cust_lhs_rhs("RANKING_METRIC")) /
            sum(cust_lhs_rhs("RFM_SIM")))
          .otherwise(0).as("RFM_SIM_SCR_FAE"),
        when(
          sum(cust_lhs_rhs("SUPPORT_SIM")) > 0,
          sum(cust_lhs_rhs("SUPPORT_SIM") *
            cust_lhs_rhs("RANKING_METRIC")) /
            sum(cust_lhs_rhs("SUPPORT_SIM")))
          .otherwise(0).as("SUPPORT_SIM_SCR"),
        when(
          sum(cust_lhs_rhs("CONFIDENCE_SIM")) > 0,
          sum(cust_lhs_rhs("CONFIDENCE_SIM") *
            cust_lhs_rhs("RANKING_METRIC")) /
            sum(cust_lhs_rhs("CONFIDENCE_SIM")))
          .otherwise(0).as("CONFIDENCE_SIM_SCR"),
        when(
          sum(cust_lhs_rhs("LIFT_SIM")) > 0,
          sum(cust_lhs_rhs("LIFT_SIM") *
            cust_lhs_rhs("RANKING_METRIC")) /
            sum(cust_lhs_rhs("LIFT_SIM")))
          .otherwise(0).as("LIFT_SIM_SCR"),
        when(
          sum(cust_lhs_rhs("CHI_SQUARE_SIM")) > 0,
          sum(cust_lhs_rhs("CHI_SQUARE_SIM") *
            cust_lhs_rhs("RANKING_METRIC")) /
            sum(cust_lhs_rhs("CHI_SQUARE_SIM")))
          .otherwise(0).as("CHI_SQUARE_SIM_SCR"),
        when(
          sum(cust_lhs_rhs("LAPLACE_SIM")) > 0,
          sum(cust_lhs_rhs("LAPLACE_SIM") *
            cust_lhs_rhs("RANKING_METRIC")) /
            sum(cust_lhs_rhs("LAPLACE_SIM")))
          .otherwise(0).as("LAPLACE_SIM_SCR"),
        when(
          sum(cust_lhs_rhs("CONVICTION_SIM")) > 0,
          sum(cust_lhs_rhs("CONVICTION_SIM") *
            cust_lhs_rhs("RANKING_METRIC")) /
            sum(cust_lhs_rhs("CONVICTION_SIM")))
          .otherwise(0).as("CONVICTION_SIM_SCR"),
        when(
          sum(cust_lhs_rhs("ADDED_VALUE_SIM")) > 0,
          sum(cust_lhs_rhs("ADDED_VALUE_SIM") *
            cust_lhs_rhs("RANKING_METRIC")) /
            sum(cust_lhs_rhs("ADDED_VALUE_SIM")))
          .otherwise(0).as("ADDED_VALUE_SIM_SCR"),
        when(
          sum(cust_lhs_rhs("CERTAINTY_FACTOR_SIM")) > 0,
          sum(cust_lhs_rhs("CERTAINTY_FACTOR_SIM") *
            cust_lhs_rhs("RANKING_METRIC")) /
            sum(cust_lhs_rhs("CERTAINTY_FACTOR_SIM")))
          .otherwise(0).as("CERTAINTY_FACTOR_SIM_SCR"),
        when(
          sum(cust_lhs_rhs("J_MEASURE_SIM")) > 0,
          sum(cust_lhs_rhs("J_MEASURE_SIM") *
            cust_lhs_rhs("RANKING_METRIC")) /
            sum(cust_lhs_rhs("J_MEASURE_SIM")))
          .otherwise(0).as("J_MEASURE_SIM_SCR"),
        when(
          sum(cust_lhs_rhs("GINI_INDEX_SIM")) > 0,
          sum(cust_lhs_rhs("GINI_INDEX_SIM") *
            cust_lhs_rhs("RANKING_METRIC")) /
            sum(cust_lhs_rhs("GINI_INDEX_SIM")))
          .otherwise(0).as("GINI_INDEX_SIM_SCR"),
        when(
          sum(cust_lhs_rhs("JACCARD_SIM")) > 0,
          sum(cust_lhs_rhs("JACCARD_SIM") *
            cust_lhs_rhs("RANKING_METRIC")) /
            sum(cust_lhs_rhs("JACCARD_SIM")))
          .otherwise(0).as("JACCARD_SIM_SCR"),
        when(
          sum(cust_lhs_rhs("SHAPIRO_SIM")) > 0,
          sum(cust_lhs_rhs("SHAPIRO_SIM") *
            cust_lhs_rhs("RANKING_METRIC")) /
            sum(cust_lhs_rhs("SHAPIRO_SIM")))
          .otherwise(0).as("SHAPIRO_SIM_SCR"),
        when(
          sum(cust_lhs_rhs("COSINE_SIM")) > 0,
          sum(cust_lhs_rhs("COSINE_SIM") *
            cust_lhs_rhs("RANKING_METRIC")) /
            sum(cust_lhs_rhs("COSINE_SIM")))
          .otherwise(0).as("COSINE_SIM_SCR"),
        when(
          sum(cust_lhs_rhs("CORRELATION_SIM")) > 0,
          sum(cust_lhs_rhs("CORRELATION_SIM") *
            cust_lhs_rhs("RANKING_METRIC")) /
            sum(cust_lhs_rhs("CORRELATION_SIM")))
          .otherwise(0).as("CORRELATION_SIM_SCR"),
        when(
          sum(cust_lhs_rhs("ODDS_RATIO_SIM")) > 0,
          sum(cust_lhs_rhs("ODDS_RATIO_SIM") *
            cust_lhs_rhs("RANKING_METRIC")) /
            sum(cust_lhs_rhs("ODDS_RATIO_SIM")))
          .otherwise(0).as("ODDS_RATIO_SIM_SCR"),
        avg((cust_lhs_rhs("CUST_PROD_SALES") *
          cust_lhs_rhs("CUST_PROD_QTY") *
          cust_lhs_rhs("RANKING_METRIC")) /
          (cust_lhs_rhs("CUST_PROD_MIN_REC") + 1)).as("AVG_RF")
      )

    val suggOffersWithTarget = suggOffers
      .join(cust_lhs_rhs_with_target,
        suggOffers("CUST_ID") === cust_lhs_rhs_with_target("CUST_ID") &&
          suggOffers("RHS") === cust_lhs_rhs_with_target("RHS"))
      .drop(cust_lhs_rhs_with_target("CUST_ID"))
      .drop(cust_lhs_rhs_with_target("RHS"))
      .cache()

    logger.info("Running the logistic regression process")

    val stages = new ArrayBuffer[PipelineStage]()

    val assembler = new VectorAssembler().setInputCols(
      Array(
        "RFM_SIM_SCR_MSI",
        "RFM_SIM_SCR_FAE",
        "SUPPORT_SIM_SCR",
        "CONFIDENCE_SIM_SCR",
        "LIFT_SIM_SCR",
        "CHI_SQUARE_SIM_SCR",
        "LAPLACE_SIM_SCR",
        "CONVICTION_SIM_SCR",
        "ADDED_VALUE_SIM_SCR",
        "CERTAINTY_FACTOR_SIM_SCR",
        "J_MEASURE_SIM_SCR",
        "GINI_INDEX_SIM_SCR",
        "JACCARD_SIM_SCR",
        "SHAPIRO_SIM_SCR",
        "COSINE_SIM_SCR",
        "CORRELATION_SIM_SCR",
        "ODDS_RATIO_SIM_SCR",
        "avg_rf"))
      .setOutputCol("features_temp")

    val normalizer = new Normalizer().setInputCol("features_temp").setOutputCol("features")

    val lr = new LogisticRegression().setMaxIter(10)
    lr.setLabelCol("TARGET")

    stages += assembler
    stages += normalizer
    stages += lr


    val pipeline = new Pipeline().setStages(stages.toArray)

    val model = pipeline.fit(suggOffersWithTarget)

    val lorModel = model.stages.last.asInstanceOf[LogisticRegressionModel]

    val intercept = lorModel.intercept
    val weights = lorModel.coefficients.toArray

    val betas = new ArrayBuffer[Double]()

    betas += intercept
    betas += weights(0)
    betas += weights(1)
    betas += weights(2)
    betas += weights(3)

    logger.info("Getting recommended offers")

    val reccomendOffers = suggOffersWithTarget
      .withColumn("TRYSCORE",
        tryScore(lit(betas),
          suggOffersWithTarget("RFM_SIM_SCR_FAE"),
          suggOffersWithTarget("LIFT_SIM_SCR"),
          suggOffersWithTarget("LAPLACE_SIM_SCR"),
          suggOffersWithTarget("CORRELATION_SIM_SCR")))
      .orderBy("CUST_ID", "RHS", "TRYSCORE")
      .select("CUST_ID", "RHS", "TRYSCORE")

    val time = Calendar.getInstance().getTime()
    val formatTime = new SimpleDateFormat("YYYYMMDDHHmmss")
    val printTime = formatTime.format(time)

    val outputLocation = "users/Analytics/" + args(2) + "/" + sc.appName +  "/" + printTime

    reccomendOffers.write.parquet(outputLocation + "/tryScores/" + args(1))

  }
}
