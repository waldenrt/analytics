package com.brierley.quantile.tests

import com.brierley.quantile.Quantile
import com.holdenkarau.spark.testing.DataFrameSuiteBase
import org.apache.spark.sql.Row
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

/**
  * Created by amerrill on 6/19/17.
  */
@RunWith(classOf[JUnitRunner])
class QuantileMigrationTest extends FunSuite with DataFrameSuiteBase {

  trait CreateMigrationData {
    val sqlCtx = sqlContext

    import sqlCtx.implicits._

    val fourStores4TP = sc.parallelize(List(
      (1, "Store-1", 1, 1, 1, 1.00, 0.00, 4),
      (1, "Store-2", 1, 1, 1, 2.00, 0.00, 4),
      (1, "Store-3", 2, 2, 2, 3.00, 0.00, 3),
      (1, "Store-4", 1, 1, 1, 4.00, 0.00, 2),
      (2, "Store-1", 1, 1, 1, 1.00, 0.00, 3),
      (2, "Store-3", 2, 2, 2, 3.00, 0.00, 2),
      (3, "Store-1", 1, 1, 1, 1.00, 0.00, 4),
      (3, "Store-2", 1, 1, 1, 2.00, 0.00, 1),
      (3, "Store-3", 2, 2, 2, 3.00, 0.00, 3),
      (4, "Store-1", 1, 1, 1, 1.00, 0.00, 4),
      (4, "Store-2", 1, 1, 1, 2.00, 0.00, 2),
      (4, "Store-3", 2, 2, 2, 3.00, 0.00, 3),
      (4, "Store-4", 1, 1, 1, 4.00, 0.00, 3)
    )).toDF("TimePeriod", "STORE_ID", "CUST_COUNT", "TXN_COUNT", "ITEM_QTY", "ITEM_AMT", "DISC_AMT", "Quantile")

    val fiveCusts5TP = sc.parallelize(List(
      (1, "Cust-1", 1, 1, 1.00, 0.00, 4),
      (1, "Cust-2", 1, 2, 2.00, 0.00, 3),
      (1, "Cust-4", 1, 4, 4.00, 0.00, 2),
      (1, "Cust-5", 1, 1, 5.00, 0.00, 1),
      (2, "Cust-1", 1, 1, 1.00, 0.00, 4),
      (2, "Cust-3", 1, 3, 3.00, 0.00, 3),
      (2, "Cust-5", 1, 1, 5.00, 0.00, 1),
      (3, "Cust-1", 1, 1, 1.00, 0.00, 4),
      (3, "Cust-2", 1, 1, 2.00, 0.00, 3),
      (3, "Cust-3", 1, 3, 3.00, 0.00, 3),
      (3, "Cust-5", 1, 1, 5.00, 0.00, 1),
      (4, "Cust-1", 1, 1, 1.00, 0.00, 4),
      (4, "Cust-3", 1, 3, 3.00, 0.00, 3),
      (4, "Cust-4", 1, 1, 4.00, 0.00, 2),
      (4, "Cust-5", 1, 1, 5.00, 0.00, 1),
      (5, "Cust-1", 1, 1, 1.00, 0.00, 4),
      (5, "Cust-2", 1, 1, 2.00, 0.00, 3),
      (5, "Cust-3", 1, 3, 3.00, 0.00, 3),
      (5, "Cust-4", 1, 1, 4.00, 0.00, 2)
    )).toDF("TimePeriod", "CUST_ID", "TXN_COUNT", "ITEM_QTY", "ITEM_AMT", "DISC_AMT", "Quantile")

    val orElseDF = sc.parallelize(List(1, 2, 3, 4)).toDF("Dummy")
  }

  trait SumMigrationData {
    val sqlCtx = sqlContext

    import sqlCtx.implicits._

    val sumCols = List("TimePeriod", "CurrQuant", "PrevQuant", "Count")

    val cust2TPs = sc.parallelize(List(
      ("Cust-1", 1, 4, Some(4)),
      ("Cust-2", 1, 3, null),
      ("Cust-4", 1, 2, Some(3)),
      ("Cust-5", 1, 1, Some(1)),
      ("Cust-1", 2, 4, Some(4)),
      ("Cust-3", 2, 4, Some(4)),
      ("Cust-5", 2, 1, Some(2))
    )).toDF("ID", "TimePeriod", "CurrQuant", "PrevQuant")

    val stores2TPs = sc.parallelize(List(
      ("Store-1", 1, 4, Some(3)),
      ("Store-2", 1, 4, null),
      ("Store-3", 1, 3, Some(2)),
      ("Store-4", 1, 2, null),
      ("Store-1", 2, 3, Some(4)),
      ("Store-3", 2, 2, Some(3)),
      ("Store-2", 2, 2, Some(3)),
      ("Store-5", 2, 2, Some(3))
    )).toDF("ID", "TimePeriod", "CurrQuant", "PrevQuant")

    val orElseDF = sc.parallelize(List(1, 2, 3, 4)).toDF("Dummy")
  }

  trait MigrationAvroData {
    val sqlCtx = sqlContext

    import sqlCtx.implicits._

    val sumDF = sc.parallelize(List(
      (1, 1, 1, 1.toLong),
      (1, 1, 2, 0.toLong),
      (1, 1, 3, 2.toLong),
      (1, 2, 1, 0.toLong),
      (1, 2, 2, 4.toLong),
      (1, 2, 3, 0.toLong),
      (1, 3, 1, 0.toLong),
      (1, 3, 2, 1.toLong),
      (1, 3, 3, 3.toLong),
      (2, 1, 1, 1.toLong),
      (2, 1, 2, 0.toLong),
      (2, 1, 3, 2.toLong),
      (2, 2, 1, 2.toLong),
      (2, 2, 2, 4.toLong),
      (2, 2, 3, 3.toLong),
      (2, 3, 1, 2.toLong),
      (2, 3, 2, 1.toLong),
      (2, 3, 3, 3.toLong)
    )).toDF("TimePeriod", "CurrQuant", "PrevQuant", "Count")

    val counDF = sc.parallelize(List(
      (1, 2, 1.toLong),
      (1, 4, 1.toLong),
      (2, 3, 1.toLong),
      (2, 2, 2.toLong)
    )).toDF("TimePeriod", "CurrQuant", "count(ID)")
  }

  test("create MigrationDF from quantDF STORES") {
    new CreateMigrationData {

      import sqlCtx.implicits._

      val migDF = Quantile.createMigrationDF(fourStores4TP, "store") getOrElse orElseDF
      assert(migDF.columns === List("TimePeriod", "ID", "CurrQuant", "PrevQuant"))

      migDF.show()
      val tp1DF = sc.parallelize(List(
        (1, "Store-1", Some(4), Some(3)),
        (1, "Store-2", Some(4), null),
        (1, "Store-3", Some(3), Some(2)),
        (1, "Store-4", Some(2), null)
      )).toDF("TimePeriod", "ID", "CurrQuant", "PrevQuant")

      val tp2DF = sc.parallelize(List(
        (2, "Store-1", Some(3), Some(4)),
        (2, "Store-3", Some(2), Some(3))
      )).toDF("TimePeriod", "ID", "CurrQuant", "PrevQuant")

      val tp3DF = sc.parallelize(List(
        (3, "Store-1", Some(4), Some(4)),
        (3, "Store-2", Some(1), Some(2)),
        (3, "Store-3", Some(3), Some(3))
      )).toDF("TimePeriod", "ID", "CurrQuant", "PrevQuant")


      val tp1 = migDF.filter(migDF("TimePeriod") === 1)
      val tp2 = migDF.filter(migDF("TimePeriod") === 2)
      val tp3 = migDF.filter(migDF("TimePeriod") === 3)

      assertDataFrameEquals(tp1, tp1DF)
      assertDataFrameEquals(tp2, tp2DF)
      assertDataFrameEquals(tp3, tp3DF)

    }
  }

  test("create MigrationDF from quantDF CUSTOMERS") {
    new CreateMigrationData {

      import sqlCtx.implicits._

      val migDF = Quantile.createMigrationDF(fiveCusts5TP, "customer") getOrElse orElseDF
      assert(migDF.columns === List("TimePeriod", "ID", "CurrQuant", "PrevQuant"))
migDF.sort("TimePeriod").show()
      val tp1DF = sc.parallelize(List(
        (1, "Cust-1", Some(4), Some(4)),
        (1, "Cust-2", Some(3), null),
        (1, "Cust-4", Some(2), null),
        (1, "Cust-5", Some(1), Some(1))
      )).toDF("TimePeriod", "ID", "CurrQuant", "PrevQuant")

      val tp2DF = sc.parallelize(List(
        (2, "Cust-1", Some(4), Some(4)),
        (2, "Cust-3", Some(3), Some(3)),
        (2, "Cust-5", Some(1), Some(1))
      )).toDF("TimePeriod", "ID", "CurrQuant", "PrevQuant")

      val tp3DF = sc.parallelize(List(
        (3, "Cust-1", Some(4), Some(4)),
        (3, "Cust-2", Some(3), null),
        (3, "Cust-3", Some(3), Some(3)),
        (3, "Cust-5", Some(1), Some(1))
      )).toDF("TimePeriod", "ID", "CurrQuant", "PrevQuant")

      val tp4DF = sc.parallelize(List(
        (4, "Cust-1", Some(4), Some(4)),
        (4, "Cust-3", Some(3), Some(3)),
        (4, "Cust-4", Some(2), Some(2)),
        (4, "Cust-5", Some(1), null)
      )).toDF("TimePeriod", "ID", "CurrQuant", "PrevQuant")

      val tp1 = migDF.filter(migDF("TimePeriod") === 1)
      val tp2 = migDF.filter(migDF("TimePeriod") === 2)
      val tp3 = migDF.filter(migDF("TimePeriod") === 3)
      val tp4 = migDF.filter(migDF("TimePeriod") === 4)

      assertDataFrameEquals(tp1, tp1DF)
      assertDataFrameEquals(tp2, tp2DF)
      assertDataFrameEquals(tp3, tp3DF)
      assertDataFrameEquals(tp4, tp4DF)

    }
  }

  test("sumMigrations CUSTOMERS, .25 quantile value (4 bins)") {
    new SumMigrationData {
      val sumDF = Quantile.sumMigrations(cust2TPs, .25, sqlCtx, sc) getOrElse orElseDF

      assert(sumDF.count === 32)
      assert(sumDF.columns === sumCols)

      val twoTo3 = sumDF.filter(sumDF("CurrQuant") === 2 && sumDF("PrevQuant") === 3 && sumDF("TimePeriod") === 1).head()
      assert(twoTo3 === Row(1, 2, 3, 1))

      val fourTo4 = sumDF.filter(sumDF("CurrQuant") === 4 && sumDF("PrevQuant") === 4 && sumDF("TimePeriod") === 2).head()
      assert(fourTo4 === Row(2, 4, 4, 2))

      val zero = sumDF.filter(sumDF("CurrQuant") === 1 && sumDF("PrevQuant") === 2 && sumDF("TimePeriod") === 1).head()
      assert(zero === Row(1, 1, 2, 0))
    }
  }

  test("sumMigrations STORES, .20 quantile value (5 bins)") {
    new SumMigrationData {
      val sumDF = Quantile.sumMigrations(stores2TPs, .20, sqlCtx, sc) getOrElse orElseDF

      assert(sumDF.count() === 50)

      val count3 = sumDF.filter(sumDF("CurrQuant") === 2 && sumDF("PrevQuant") === 3 && sumDF("TimePeriod") === 2).head()
      assert(count3 === Row(2, 2, 3, 3))
    }
  }

  test("countTotals") {
    new SumMigrationData {
      val countDF = Quantile.countTotals(stores2TPs) getOrElse orElseDF

      val quant2 = countDF.filter(countDF("CurrQuant") === 2).head()
      val quant4 = countDF.filter(countDF("CurrQuant") === 4).head()

      assert(quant2 === Row(1, 2, 1))
      assert(quant4 === Row(1, 4, 1))
    }
  }

  test("migration avro creation") {
  new MigrationAvroData {
    val avro = Quantile.createMigrationAvro(sumDF, counDF).get

    println(s"avro results: $avro")
  }
  }

}
