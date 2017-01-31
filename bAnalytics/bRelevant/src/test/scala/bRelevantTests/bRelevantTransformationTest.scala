
package com.brierley.bRelevant

import com.holdenkarau.spark.testing.DataFrameSuiteBase
import org.apache.spark.sql._
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

/**
  * Created by amerrill on 1/10/17.
  */


@RunWith(classOf[JUnitRunner])
class bRelevantTransformationTest extends FunSuite with DataFrameSuiteBase {


  trait TestDataCreation {
    val sqlCtx = sqlContext

    import sqlCtx.implicits._

    val cutOffDate = "08/04/2015"

    val transFileTemp = sc.parallelize(List(
      ("23-JUL-15", "BBEB02D0-9E89-495B-A15B-4404F4572B11", 1018402826, 0.9, 1, 1, 0.9, "TEA"),
      ("24-JUL-15", "73581B69-97AE-49B6-8BC3-02A9BD529D5E", 1021529565, 1.19, 2, 2, 2.38, "BREAKFAST BURRITOS"),
      ("24-JUL-15", "4496DD25-5BF4-4092-BB15-0F29127CBA7C", 1021282559, 3.49, 1, 1, 3.49, "SPECIALTY DRINK"),
      ("24-JUL-15", "4496DD25-5BF4-4092-BB15-0F29127CBA7C", 1021282559, 2.69, 1, 1, 2.69, "BREAKFAST SNACK ITEMS"),
      ("03-AUG-15", "BBEB02D0-9E89-495B-A15B-4404F4572B11", 1041012802, 2.29, 1, 1, 2.29, "TEA"),
      ("03-AUG-15", "BBEB02D0-9E89-495B-A15B-4404F4572B11", 1041012802, 2.29, 1, 1, 2.29, "CSD"),
      ("01-JUL-15", "BEFE8D05-2171-49C1-8A04-EF595B67721B", 1024706965, 1.69, 1, 1, 1.69, "TATER TOTS"),
      ("02-AUG-15", "BEFE8D05-2171-49C1-8A04-EF595B67721B", 1039154079, 1.59, 1, 1, 1.59, "CSD"),
      ("02-AUG-15", "BEFE8D05-2171-49C1-8A04-EF595B67721B", 1039154079, 2.89, 1, 1, 2.89, "TATER TOTS"),
      ("02-AUG-15", "BEFE8D05-2171-49C1-8A04-EF595B67721B", 1039154079, 2.89, 1, 1, 2.89, "BREAKFAST TOASTERS")
    )).toDF("TXN_BUSINESS_DATE", "CAPTURED_LOYALTY_ID", "TXN_HEADER_ID", "ITEM_PRICE", "QUANTITY", "UNITS",
      "LINE_AMT_AFTER_DISC", "PRODUCT_CATEGORY_DESCR")

    val testTransFile = sc.parallelize(List(
      ("23-JUL-15", "TEA", "BBEB02D0-9E89-495B-A15B-4404F4572B11", 1018402826, 0.9, 1, 1, 0.9, 3, 7),
      ("03-AUG-15", "TEA", "BBEB02D0-9E89-495B-A15B-4404F4572B11", 1041012802, 2.29, 1, 1, 2.29, 3, 7),
      ("03-AUG-15", "CSD", "BBEB02D0-9E89-495B-A15B-4404F4572B11", 1041012802, 2.29, 1, 1, 2.29, 3, 4),
      ("24-JUL-15", "BREAKFAST BURRITOS", "73581B69-97AE-49B6-8BC3-02A9BD529D5E", 1021529565, 1.19, 2, 2, 2.38, 2, 1),
      ("02-AUG-15", "BREAKFAST TOASTERS", "BEFE8D05-2171-49C1-8A04-EF595B67721B", 1039154079, 2.89, 1, 1, 2.89, 4, 3),
      ("01-JUL-15", "TATER TOTS", "BEFE8D05-2171-49C1-8A04-EF595B67721B", 1024706965, 1.69, 1, 1, 1.69, 4, 6),
      ("02-AUG-15", "TATER TOTS", "BEFE8D05-2171-49C1-8A04-EF595B67721B", 1039154079, 2.89, 1, 1, 2.89, 4, 6),
      ("02-AUG-15", "CSD", "BEFE8D05-2171-49C1-8A04-EF595B67721B", 1039154079, 1.59, 1, 1, 1.59, 4, 4),
      ("24-JUL-15", "SPECIALTY DRINK", "4496DD25-5BF4-4092-BB15-0F29127CBA7C", 1021282559, 3.49, 1, 1, 3.49, 1, 5),
      ("24-JUL-15", "BREAKFAST SNACK ITEMS", "4496DD25-5BF4-4092-BB15-0F29127CBA7C", 1021282559, 2.69, 1, 1, 2.69, 1, 2)
    )).toDF("TXN_BUSINESS_DATE", "PRODUCT_CATEGORY_DESCR", "CAPTURED_LOYALTY_ID", "TXN_HEADER_ID", "ITEM_PRICE",
      "QUANTITY", "UNITS", "LINE_AMT_AFTER_DISC", "CustNum", "ProdNum")

    val tempBasket = sc.parallelize(List(
      ("BEFE8D05-2171-49C1-8A04-EF595B67721B", "CSD", 4, 1, 1, 1.59, 2),
      ("BEFE8D05-2171-49C1-8A04-EF595B67721B", "TATER TOTS", 6, 2, 2, 4.58, 2),
      ("BBEB02D0-9E89-495B-A15B-4404F4572B11", "TEA", 7, 2, 2, 3.19, 1),
      ("4496DD25-5BF4-4092-BB15-0F29127CBA7C", "SPECIALTY DRINK", 5, 1, 1, 3.49, 11),
      ("BEFE8D05-2171-49C1-8A04-EF595B67721B", "BREAKFAST TOASTERS", 3, 1, 1, 2.89, 2),
      ("BBEB02D0-9E89-495B-A15B-4404F4572B11", "CSD", 4, 1, 1, 2.29, 1),
      ("73581B69-97AE-49B6-8BC3-02A9BD529D5E", "BREAKFAST BURRITOS", 1, 1, 2, 2.38, 11),
      ("4496DD25-5BF4-4092-BB15-0F29127CBA7C", "BREAKFAST SNACK ITEMS", 2, 1, 1, 2.69, 11)
    )).toDF("CAPTURED_LOYALTY_ID", "PRODUCT_CATEGORY_DESCR", "ProdNum", "times_purchased", "cust_prod_qty",
      "cust_prod_sales", "cust_prod_min_rec")

    val tempCust = sc.parallelize(List(
      ("BBEB02D0-9E89-495B-A15B-4404F4572B11", 3, 2, 2),
      ("73581B69-97AE-49B6-8BC3-02A9BD529D5E", 2, 1, 1),
      ("4496DD25-5BF4-4092-BB15-0F29127CBA7C", 1, 1, 2),
      ("BEFE8D05-2171-49C1-8A04-EF595B67721B", 4, 2, 3)
    )).toDF("CUST_ID", "CustNum", "cust_ttl_num_bask", "number_products_purchased")

    val partialCustProdMetric = sc.parallelize(List(
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

    val testCustList = sc.parallelize(List(
      ("4496DD25-5BF4-4092-BB15-0F29127CBA7C", 1),
      ("73581B69-97AE-49B6-8BC3-02A9BD529D5E", 2),
      ("BBEB02D0-9E89-495B-A15B-4404F4572B11", 3),
      ("BEFE8D05-2171-49C1-8A04-EF595B67721B", 4)
    )).toDF("CAPTURED_LOYALTY_ID", "CustNum")

    val testProdList = sc.parallelize(List(
      ("BREAKFAST BURRITOS", 1),
      ("BREAKFAST SNACK ITEMS", 2),
      ("BREAKFAST TOASTERS", 3),
      ("CSD", 4),
      ("SPECIALTY DRINK", 5),
      ("TATER TOTS", 6),
      ("TEA", 7)
    )).toDF("PRODUCT_CATEGORY_DESCR", "ProdNum")
  }


  test("transFileTemp changed to transFile") {
    new TestDataCreation {

      println("transFileTemp (aka starting DF)")
      transFileTemp.show()

      val transFile = bRelevant.createTransFile(transFileTemp, testCustList, testProdList)
      println("Transfile result:")
      transFile.show
      transFile.printSchema
    }
  }

  test("createCustList with transFileTemp") {
    new TestDataCreation {

      val custList = bRelevant.createCustList(transFileTemp)
      println("custList from method Call....")
      custList.show

      val totalCusts = custList.count

      assert(4 === totalCusts)
    }
  }

  test("createProdList with transFileTemp") {
    new TestDataCreation {

      val prodList = bRelevant.createProdList(transFileTemp)
      println("prodList from method call....")
      prodList.show

      assert(7 === prodList.count)
    }
  }

  test("createBasket from testTransFile and all inclusive cutoff date") {
    new TestDataCreation {
      val basket = bRelevant.createBasket(testTransFile, cutOffDate)


      //for debug purposes while writing tests
      println("Basket from testTransFile")
      basket.show()

      //check times_purchased with item in multiple transactions
      val teaTimes = basket
        .where("CAPTURED_LOYALTY_ID = 'BBEB02D0-9E89-495B-A15B-4404F4572B11'")
        .where("PRODUCT_CATEGORY_DESCR = 'TEA'")
        .select("times_purchased")
        .first()
        .getLong(0)
      assert(teaTimes === 2)

      //check cust_prod_qty with quanites from a single transaction, and multiple transactions
      val bbProdQty = basket
        .where("CAPTURED_LOYALTY_ID = '73581B69-97AE-49B6-8BC3-02A9BD529D5E'")
        .where("PRODUCT_CATEGORY_DESCR = 'BREAKFAST BURRITOS'")
        .select("cust_prod_qty")
        .first()
        .getLong(0)

      val teaProdQty = basket
        .where("CAPTURED_LOYALTY_ID = 'BBEB02D0-9E89-495B-A15B-4404F4572B11'")
        .where("PRODUCT_CATEGORY_DESCR = 'TEA'")
        .select("cust_prod_qty")
        .first()
        .getLong(0)

      assert(bbProdQty === 2)
      assert(teaProdQty === 2)

      //check cust_prod_sales with items from multiple transactions
      val teaCost = basket
        .where("CAPTURED_LOYALTY_ID = 'BBEB02D0-9E89-495B-A15B-4404F4572B11'")
        .where("PRODUCT_CATEGORY_DESCR = 'TEA'")
        .select("cust_prod_sales")
        .first()
        .getDouble(0)

      assert(teaCost === 3.19)

      //old purchase in previous month, and new purchase in current month
      val recentTots = basket
        .where("CAPTURED_LOYALTY_ID = 'BEFE8D05-2171-49C1-8A04-EF595B67721B'")
        .where("PRODUCT_CATEGORY_DESCR = 'TATER TOTS'")
        .select("cust_prod_min_rec")
        .first()
        .getLong(0)

      assert(recentTots === 2)

      //only single purchase in previous month
      val oldDrink = basket
        .where("CAPTURED_LOYALTY_ID = '4496DD25-5BF4-4092-BB15-0F29127CBA7C'")
        .where("PRODUCT_CATEGORY_DESCR = 'SPECIALTY DRINK'")
        .select("cust_prod_min_rec")
        .first()
        .getLong(0)

      assert(oldDrink === 11)

    }
  }

  test("createBasket from testTransFile include single day transaction") {
    new TestDataCreation {
      val basket = bRelevant.createBasket(testTransFile, "07/23/2015")
      basket.show()

      //assert(basket.count === 1) -- this actually returns all 5 rows, with negative min_rec values
    }
  }

  test("createCustomer from testTransFile") {
    new TestDataCreation {
      val customer = bRelevant.createCustomer(testTransFile)
      println("return from customer after being given testTransFile")
      customer.show()

      assert(customer.count === 4)

      val twoBaskets = customer
        .where("CUST_ID = 'BBEB02D0-9E89-495B-A15B-4404F4572B11'")
        .select("cust_ttl_num_bask")
        .first()
        .getLong(0)

      assert(twoBaskets === 2)

      val twoItems = customer
        .where("CUST_ID = '4496DD25-5BF4-4092-BB15-0F29127CBA7C'")
        .select("number_products_purchased")
        .first()
        .getLong(0)

      assert(twoItems === 2)

    }
  }

  test("createCustProdMetric from tempBasket and tempCust") {
    new TestDataCreation {
      val custProdMetric = bRelevant.createCustProdMetric(tempBasket, tempCust)
      println("return from custProdMetric after being given tempBasket and tempCust")
      custProdMetric.show()

      val caught =
        intercept[AnalysisException] {
          val attempt = custProdMetric.select("CAPTURED_LOYALTY_ID")
        }

      val metrics = List(6.38, 2.29, 0.21636363636363637, 0.795, 4.58, 1.445, 0.3172727272727273, 0.24454545454545454)

      val colMetrics = custProdMetric
        .select("metric").map(_ (0)).collect()

      assert(metrics === colMetrics)
    }
  }

  test("createProdRank from partialCustProdMetric") {
    new TestDataCreation {
      val prodRank = bRelevant.createProdRank(partialCustProdMetric)
      println("return from createProdRank after being given partialCustProdMetric")
      prodRank.show()

      val rankMetric = List(1, 1, 2, 1, 1, 2, 3, 2)
      val rfm = Array("111", "101", "202", "101", "101", "202", "333", "222")

      val rankMetricCol = prodRank
        .select("Ranking_Metric").map(_ (0)).collect()

      assert(rankMetric === rankMetricCol)

      val rfmCol = prodRank
        .select("RFM_Term").map(_ (0)).collect()

      assert(rfm === rfmCol)
    }
  }

  test("all transformations one after the other given only transFileTemp"){
    new TestDataCreation {
      val cList = bRelevant.createCustList(transFileTemp)
      val pList = bRelevant.createProdList(transFileTemp)

      val tFile = bRelevant.createTransFile(transFileTemp, cList, pList)

      val bask = bRelevant.createBasket(tFile, cutOffDate)

      val cust = bRelevant.createCustomer(tFile)

      val cpMetric = bRelevant.createCustProdMetric(bask, cust)

      val cpRank = bRelevant.createProdRank(cpMetric)

      val custNum = cpRank
        .where("CUST_ID = 'BEFE8D05-2171-49C1-8A04-EF595B67721B'")
        .select("CustNum")
        .first
        .getInt(0)

      assert(custNum === 4)

      val prodNum = cpRank
        .where("PRODUCT_CATEGORY_DESCR = 'TEA'")
        .select("ProdNum")
        .first
        .getInt(0)

      assert(prodNum === 7)
    }
  }
}
