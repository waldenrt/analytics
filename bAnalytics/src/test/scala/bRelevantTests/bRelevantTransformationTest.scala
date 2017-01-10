package com.brierley.bRelevant

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
class bRelevantTransformationTest extends FunSuite with DataFrameSuiteBase{


  trait TestDataCreation{
    val sqlCtx = sqlContext
    import sqlCtx.implicits._

    val transFileTemp = sc.parallelize(List(
      ("23-JUL-15","BBEB02D0-9E89-495B-A15B-4404F4572B11",1018402826,0.9,1,1,0.9,"TEA"),
      ("24-JUL-15","73581B69-97AE-49B6-8BC3-02A9BD529D5E",1021529565,1.19,2,2,2.38,"BREAKFAST BURRITOS"),
      ("24-JUL-15","4496DD25-5BF4-4092-BB15-0F29127CBA7C",1021282559,3.49,1,1,3.49,"SPECIALTY DRINK"),
      ("24-JUL-15","4496DD25-5BF4-4092-BB15-0F29127CBA7C",1021282559,2.69,1,1,2.69,"BREAKFAST SNACK ITEMS"),
      ("03-AUG-15","BBEB02D0-9E89-495B-A15B-4404F4572B11",1041012802,2.29,1,1,2.29,"TEA"),
      ("03-AUG-15","BBEB02D0-9E89-495B-A15B-4404F4572B11",1041012802,2.29,1,1,2.29,"CSD")
    )).toDF("TXN_BUSINESS_DATE","CAPTURED_LOYALTY_ID","TXN_HEADER_ID","ITEM_PRICE","QUANTITY","UNITS","LINE_AMT_AFTER_DISC","PRODUCT_CATEGORY_DESCR")
  }

  test("transFileTemp changed to transFile") {
    new TestDataCreation{

      //I have no idea how to test repartitioning
    }
  }

  test("createBasket from transFileTemp and all inclusive cutoff date") {
    new TestDataCreation{
      val basket = bRelevant.createBasket(transFileTemp, "08/04/2015")
      val teaTimes = basket
        .where("CAPTURED_LOYALTY_ID = 'BBEB02D0-9E89-495B-A15B-4404F4572B11'")
        .where("PRODUCT_CATEGORY_DESCR = 'TEA'")
        .select("times_purchased")
        .first()
        .getLong(0)

      assert(teaTimes === 2)
      basket.show()
    }
  }

}