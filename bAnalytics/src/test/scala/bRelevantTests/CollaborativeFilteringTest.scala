package com.brierley.bRelevant


import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import com.holdenkarau.spark.testing.DataFrameSuiteBase
import org.apache.spark.sql.hive.HiveContext
import org.apache.spark.sql._


/**
  * Created by amerrill on 1/10/17.
  */


@RunWith(classOf[JUnitRunner])
class CollaborativeFilteringTest extends FunSuite with DataFrameSuiteBase {

  trait TestData {
    val sqlCtx = sqlContext

    import sqlCtx.implicits._

    val custProdMetric = sc.parallelize(List(
      ("TEA", 7, 2, 2, 3.19, 1, "BBEB02D0-9E89-495B-A15B-4404F4572B11", 3, 2, 2, 6.38),
      ("CSD", 4, 1, 1, 2.29, 1, "BBEB02D0-9E89-495B-A15B-4404F4572B11", 3, 2, 2, 2.29),
      ("BREAKFAST BURRITOS", 1, 1, 2, 2.38, 11, "73581B69-97AE-49B6-8BC3-02A9BD529D5E", 2, 1, 1, 0.21636363636363637),
      ("CSD", 4, 1, 1, 1.59, 2, "BEFE8D05-2171-49C1-8A04-EF595B67721B", 4, 2, 3, 0.795),
      ("TATER TOTS", 6, 2, 2, 4.58, 2, "BEFE8D05-2171-49C1-8A04-EF595B67721B", 4, 2, 3, 4.58),
      ("BREAKFAST TOASTERS", 3, 1, 1, 2.89, 2, "BEFE8D05-2171-49C1-8A04-EF595B67721B", 4, 2, 3, 1.445),
      ("SPECIALTY DRINK", 5, 1, 1, 3.49, 11, "4496DD25-5BF4-4092-BB15-0F29127CBA7C", 1, 1, 2, 0.3172727272727273),
      ("BREAKFAST SNACK ITEMS", 2, 1, 1, 2.69, 11, "4496DD25-5BF4-4092-BB15-0F29127CBA7C", 1, 1, 2, 0.24454545454545454)
    )).toDF("PRODUCT_CATEGORY_DESCR", "ProdNum", "times_purchased", "cust_prod_qty", "cust_prod_sales", "cust_prod_min_rec",
      "CUST_ID", "CustNum", "cust_ttl_num_bask", "number_products_purchased", "metric")

    val completeCustProdRank = sc.parallelize(List(
      ("BREAKFAST BURRITOS", 1, 1, 2, 2.38, 11, "73581B69-97AE-49B6-8BC3-02A9BD529D5E", 2, 1, 1, 0.21636363636363637, 1, 111),
      ("BREAKFAST SNACK ITEMS", 2, 1, 1, 2.69, 11, "4496DD25-5BF4-4092-BB15-0F29127CBA7C", 1, 1, 2, 0.24454545454545454, 1, 101),
      ("BREAKFAST TOASTERS", 3, 1, 1, 2.89, 2, "BEFE8D05-2171-49C1-8A04-EF595B67721B", 4, 2, 3, 1.445, 2, 202),
      ("CSD", 4, 1, 1, 2.29, 1, "BBEB02D0-9E89-495B-A15B-4404F4572B11", 3, 2, 2, 2.29, 1, 101),
      ("CSD", 4, 1, 1, 1.59, 2, "BEFE8D05-2171-49C1-8A04-EF595B67721B", 4, 2, 3, 0.795, 1, 101),
      ("SPECIALTY DRINK", 5, 1, 1, 3.49, 11, "4496DD25-5BF4-4092-BB15-0F29127CBA7C", 1, 1, 2, 0.3172727272727273, 2, 202),
      ("TATER TOTS", 6, 2, 2, 4.58, 2, "BEFE8D05-2171-49C1-8A04-EF595B67721B", 4, 2, 3, 4.58, 3, 333),
      ("TEA", 7, 2, 2, 3.19, 1, "BBEB02D0-9E89-495B-A15B-4404F4572B11", 3, 2, 2, 6.38, 2, 222)
    )).toDF("PRODUCT_CATEGORY_DESCR", "ProdNum", "times_purchased", "cust_prod_qty", "cust_prod_sales", "cust_prod_min_rec",
      "CUST_ID", "CustNum", "cust_ttl_num_bask", "number_products_purchased", "metric", "Ranking_Metric", "RFM_Term")

    val cfCustProdRank = sc.parallelize(List(
      ("73581B69-97AE-49B6-8BC3-02A9BD529D5E", 2, "BREAKFAST BURRITOS", 1, 1.0),
      ("4496DD25-5BF4-4092-BB15-0F29127CBA7C", 1, "BREAKFAST SNACK ITEMS", 2, 1.0),
      ("BEFE8D05-2171-49C1-8A04-EF595B67721B", 4, "BREAKFAST TOASTERS", 3, 2.0),
      ("BBEB02D0-9E89-495B-A15B-4404F4572B11", 3, "CSD", 4, 1.0),
      ("BEFE8D05-2171-49C1-8A04-EF595B67721B", 4, "CSD", 4, 1.0),
      ("4496DD25-5BF4-4092-BB15-0F29127CBA7C", 1, "SPECIALTY DRINK", 5, 2.0),
      ("BEFE8D05-2171-49C1-8A04-EF595B67721B", 4, "TATER TOTS", 6, 3.0),
      ("BBEB02D0-9E89-495B-A15B-4404F4572B11", 3, "TEA", 7, 2.0)
    )).toDF("CUST_ID", "CustNum", "PRODUCT_CATEGORY_DESCR", "ProdNum", "Ranking_Metric")

  }

  test("get custProdRank, given both good DF") {
    new TestData {
      val custProdRank = CollaborativeFiltering.getCustProdRank((custProdMetric, completeCustProdRank))

      custProdRank.show

      val cust2 = custProdRank
        .where("CustNum = 2")
        .select("Ranking_Metric")
        .first
        .getDouble(0)

        assert (cust2 === 1.0)
    }
  }


  test("createNumCustProdRank given cfCustProdRank") {
    new TestData {
      val numCustProdRank = CollaborativeFiltering.createNumCustProdRank(cfCustProdRank)

      numCustProdRank.show

      intercept[AnalysisException] {
        val attempt = numCustProdRank.select("CAPTURED_LOYALTY_ID")
      }

        intercept[AnalysisException] {
          numCustProdRank.select("PRODUCT_CATEGORY_DESCR")
        }

      }
    }
  }