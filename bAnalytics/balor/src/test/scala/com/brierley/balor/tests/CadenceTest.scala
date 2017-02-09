package com.brierley.balor.tests

import com.brierley.balor.CadenceCalcs
import com.holdenkarau.spark.testing.DataFrameSuiteBase
import org.apache.spark.sql._
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

/**
  * Created by amerrill on 1/31/17.
  */

@RunWith(classOf[JUnitRunner])
class CadenceTest extends FunSuite with DataFrameSuiteBase {


  trait SingleUserData {
    val sqlCtx = sqlContext

    import sqlCtx.implicits._

    val singleUserSingleTrans = sc.parallelize(List(
      ("customerA", "txn1-custA-May", "05/05/2015", 8, 331.95, 300.00)
    )).toDF("CUST_ID", "TXN_ID", "TXN_DATE", "ItemsPurchased", "TXN_AMT", "QualifiedTotal")

    val singleUser2TransJan = sc.parallelize(List(
      ("customerA", "txn1-custA-Jan1", "01/03/2015", 1, 5.00, 5.00),
      ("customerA", "txn2-custA-Jan2", "01/04/2015", 1, 7.50, 7.50)
    )).toDF("CUST_ID", "TXN_ID", "TXN_DATE", "ItemsPurchased", "TXN_AMT", "QualifiedTotal")

    val singleUserMultiTransJan = sc.parallelize(List(
      ("customerA", "txn1-custA-Jan1", "01/03/2015", 1, 5.00, 5.00),
      ("customerA", "txn2-custA-Jan2", "01/04/2015", 1, 7.50, 7.50),
      ("customerA", "txn3-custA-Jan2", "01/04/2015", 4, 22.12, 22.12),
      ("customerA", "txn4-custA-Jan3", "01/05/2015", 3, 10.40, 10.40),
      ("customerA", "txn5-custA-Jan4", "01/10/2015", 2, 12.11, 12.11)
    )).toDF("CUST_ID", "TXN_ID", "TXN_DATE", "ItemsPurchased", "TXN_AMT", "QualifiedTotal")

    //dropping extra columns, including only required from this point on
    val singleUserMultiTransSummer = sc.parallelize(List(
      ("customerA", "txn1-custA-Jun1", "06/05/2015", 23.45),
      ("customerA", "txn2-custA-Jun2", "06/09/2015", 12.50),
      ("customerA", "txn3-custA-Jun3", "06/25/2015", 15.50),
      ("customerA", "txn4-custA-Jul1", "07/03/2015", 123.13),
      ("customerA", "txn5-custA-Jul2", "07/05/2015", 12.23)
    )).toDF("CUST_ID", "TXN_ID", "TXN_DATE", "TXN_AMT")

    val singleUserMultiTransLeap = sc.parallelize(List(
      ("customerA", "txn1-custA-Feb1", "02/15/2016", 12.34),
      ("customerA", "txn2-custA-Feb2", "02/22/2016", 22.10),
      ("customerA", "txn3-custA-Feb3", "02/29/2016", 10.50),
      ("customerA", "txn4-custA-Mar1", "03/02/2016", 25.45),
      ("customerA", "txn5-custA-Mar2", "03/05/2016", 1.50)
    )).toDF("CUST_ID", "TXN_ID", "TXN_DATE", "TXN_AMT")

    val singleUserMultiTransSavings = sc.parallelize(List(
      ("customerA", "txn1-custA-Mar1", "03/10/2016", 10.50),
      ("customerA", "txn2-custA-Mar2", "03/13/2016", 12.25),
      ("customerA", "txn3-custA-Mar3", "03/15/2016", 20.40),
      ("customerA", "txn4-custA-Apr1", "04/23/2016", 13.24),
      ("customerA", "txn5-custA-May1", "05/15/2016", 14.54),
      ("customerA", "txn6-custA-Jun1", "06/30/2016", 20.50),
      ("customerA", "txn7-custA-Jul1", "07/15/2016", 35.42),
      ("customerA", "txn8-custA-Aug1", "08/24/2016", 54.60),
      ("customerA", "txn9-custA-Sep1", "09/28/2016", 32.15),
      ("customerA", "txn10-custA-Oct1", "10/17/2016", 12.85),
      ("customerA", "txn11-custA-Oct1", "10/17/2016", 45.32),
      ("customerA", "txn12-custA-Nov1", "11/04/2016", 98.21),
      ("customerA", "txn13-custA-Nov2", "11/08/2016", 54.81)
    )).toDF("CUST_ID", "TXN_ID", "TXN_DATE", "TXN_AMT")

    val singleUserMultiTransYearGap = sc.parallelize(List(
      ("customerA", "txn1-custA-Dec1", "12/24/2015", 12.34),
      ("customerA", "txn2-custA-Dec2", "12/30/2015", 22.10),
      ("customerA", "txn3-custA-Jan1", "01/03/2016", 10.50),
      ("customerA", "txn4-custA-Jan2", "01/09/2016", 25.45),
      ("customerA", "txn5-custA-Jan3", "01/09/2016", 1.50)
    )).toDF("CUST_ID", "TXN_ID", "TXN_DATE", "TXN_AMT")

  }

  trait MultiUserData {
    val sqlCtx = sqlContext

    import sqlCtx.implicits._

    val multiUserSingleAndDouble = sc.parallelize(List(
      ("custA", "txn1-custA-Jun1", "06/04/2016", 34.24),
      ("custA", "txn2-custA-Jun2", "06/10/2016", 23.14),
      ("custB", "txn1-custB-Jun1", "06/14/2016", 26.16)
    )).toDF("CUST_ID", "TXN_ID", "TXN_DATE", "TXN_AMT")

    val multiUserDoubleNov = sc.parallelize(List(
      ("custA", "txn1-custA-Nov1", "11/04/2016", 34.24),
      ("custB", "txn1-custB-Nov1", "11/07/2016", 26.16),
      ("custA", "txn2-custA-Nov2", "11/10/2016", 23.14),
      ("custB", "txn2-custB-Nov2", "11/10/2016", 98.32)
    )).toDF("CUST_ID", "TXN_ID", "TXN_DATE", "TXN_AMT")

    val multiUserMultiTransMultiMonth = sc.parallelize(List(
      ("custA", "txn1-custA-Jan1", "01/18/2016", 2.18),
      ("custB", "txn1-custB-Jan1", "01/18/2016", 3.22),
      ("custB", "txn2-custB-Jan2", "01/27/2016", 42.14),
      ("custC", "txn1-custC-Jan1", "01/12/2016", 23.51),
      ("custC", "txn2-custC-Feb1", "02/14/2016", 134.15),
      ("custA", "txn2-custA-Feb1", "02/14/2016", 12.41),
      ("custA", "txn3-custA-Feb2", "02/22/2016", 154.12),
      ("custB", "txn3-custB-Feb1", "02/23/2016", 36.13),
      ("custA", "txn4-custA-Mar1", "03/12/2016", 35.12),
      ("custB", "txn4-custB-Mar1", "03/13/2016", 23.75),
      ("custB", "txn5-custB-Mar2", "03/30/2016", 87.15),
      ("custC", "txn3-custC-Mar1", "03/30/2016", 32.57)
    )).toDF("CUST_ID", "TXN_ID", "TXN_DATE", "TXN_AMT")

    val multiUserMultiTransMultiYear = sc.parallelize(List(
      ("custA", "txn1-custA-Nov1", "11/18/2015", 2.18),
      ("custB", "txn1-custB-Nov1", "11/18/2015", 3.22),
      ("custB", "txn2-custB-Nov2", "11/27/2015", 42.14),
      ("custC", "txn1-custC-Nov1", "11/12/2015", 23.51),
      ("custC", "txn2-custC-Dec1", "12/14/2015", 134.15),
      ("custA", "txn2-custA-Dec1", "12/14/2015", 12.41),
      ("custA", "txn3-custA-Dec2", "12/22/2015", 154.12),
      ("custB", "txn3-custB-Dec1", "12/23/2015", 36.13),
      ("custA", "txn4-custA-Jan1", "01/12/2016", 35.12),
      ("custB", "txn4-custB-Jan1", "01/13/2016", 23.75),
      ("custB", "txn5-custB-Jan2", "01/30/2016", 87.15),
      ("custC", "txn3-custC-Jan1", "01/30/2016", 32.57),
      ("custC", "txn4-custC-Jan1", "01/30/2016", 43.12),
      ("custC", "txn5-custC-Feb1", "02/10/2016", 160.23)
    )).toDF("CUST_ID", "TXN_ID", "TXN_DATE", "TXN_AMT")

    val everythingMixed = sc.parallelize(List(
      ("custA", "txn1-custA-Nov1", "11/18/2015", 2.18),
      ("custB", "txn1-custB-Nov1", "11/18/2015", 3.22),
      ("custB", "txn2-custB-Nov2", "11/27/2015", 42.14),
      ("custC", "txn1-custC-Nov1", "11/12/2015", 23.51),
      ("custD", "txn1-custD-Nov1", "11/12/2015", 235.15),
      ("custC", "txn2-custC-Dec1", "12/14/2015", 134.15),
      ("custA", "txn2-custA-Dec1", "12/14/2015", 12.41),
      ("custA", "txn3-custA-Dec2", "12/22/2015", 154.12),
      ("custB", "txn3-custB-Dec1", "12/23/2015", 36.13),
      ("custA", "txn4-custA-Jan1", "01/12/2016", 35.12),
      ("custE", "txn1-custE-Jan1", "01/13/2016", 643.19),
      ("custB", "txn4-custB-Jan1", "01/13/2016", 23.75),
      ("custF", "txn1-custF-Jan1", "01/30/2016", 53.98),
      ("custB", "txn5-custB-Jan2", "01/30/2016", 87.15),
      ("custC", "txn3-custC-Jan1", "01/30/2016", 32.57),
      ("custF", "txn2-custF-Jan1", "01/30/2016", 123.53),
      ("custC", "txn4-custC-Jan1", "01/30/2016", 43.12),
      ("custF", "txn3-custF-Jan1", "01/30/2016", 12.53),
      ("custC", "txn5-custC-Feb1", "02/10/2016", 160.23)
    )).toDF("CUST_ID", "TXN_ID", "TXN_DATE", "TXN_AMT")
  }

  trait CadenceNormalizingData {

    val fiveDays = 5

    val sevenDays = 7

    val eightDays = 8

    val thirteenDays = 13

    val fourteenDays = 14

    val twentyFourDays = 24

    val fortyFiveDays = 45

    val ninetyDays = 90

    val roundTo183 = 145

    val roundTo365Days = 245
  }

  trait DaysData {
    val sqlCtx = sqlContext

    import sqlCtx.implicits._

    val singleUser6Days = sc.parallelize(List(
      ("customerA", "txn1-custA-Jun1", "06/05/2015", 23.45, 0),
      ("customerA", "txn2-custA-Jun2", "06/09/2015", 12.50, 4),
      ("customerA", "txn3-custA-Jun3", "06/25/2015", 15.50, 16),
      ("customerA", "txn4-custA-Jul1", "07/03/2015", 123.13, 8),
      ("customerA", "txn5-custA-Jul2", "07/05/2015", 12.23, 2),
      ("customerA", "txn6-custA-Jul3", "07/09/2015", 51.13, 4)
    )).toDF("CUST_ID", "TXN_ID", "TXN_DATE", "TXN_AMT", "Cadence")

    val singleUser6Txns4Days = sc.parallelize(List(
      ("customerA", "txn1-custA-Jun1", "06/05/2015", 23.45, 0),
      ("customerA", "txn2-custA-Jun2", "06/09/2015", 12.50, 4),
      ("customerA", "txn3-custA-Jun3", "06/25/2015", 15.50, 16),
      ("customerA", "txn4-custA-Jul1", "07/03/2015", 123.13, 8),
      ("customerA", "txn5-custA-Jul2", "07/03/2015", 12.23, 0),
      ("customerA", "txn6-custA-Jul3", "07/09/2015", 51.13, 6)
    )).toDF("CUST_ID", "TXN_ID", "TXN_DATE", "TXN_AMT", "Cadence")

    val singleUserRepeats = sc.parallelize(List(
      ("customerA", "txn1-custA-Jun1", "06/05/2015", 23.45, 0),
      ("customerA", "txn2-custA-Jun1", "06/05/2015", 4.87, 0),
      ("customerA", "txn3-custA-Jun2", "06/06/2015", 71.51, 1),
      ("customerA", "txn4-custA-Jun3", "06/09/2015", 12.50, 4),
      ("customerA", "txn5-custA-Jun4", "06/25/2015", 15.50, 16),
      ("customerA", "txn6-custA-Jun4", "06/25/2015", 15.50, 0),
      ("customerA", "txn7-custA-Jul1", "07/03/2015", 123.13, 8),
      ("customerA", "txn8-custA-Jul2", "07/03/2015", 12.23, 0),
      ("customerA", "txn9-custA-Jul3", "07/09/2015", 51.13, 6),
      ("customerA", "txn10-custA-Jul4", "07/12/2015", 15.71, 3),
      ("customerA", "txn11-custA-Jul5", "07/14/2015", 8.14, 2),
      ("customerA", "txn12-custA-Jul5", "07/14/2015", 24.51, 2),
      ("customerA", "txn13-custA-Jul6", "07/17/2015", 21.12, 3),
      ("customerA", "txn14-custA-Jul6", "07/17/2015", 2.98, 0),
      ("customerA", "txn15-custA-Jul6", "07/17/2015", 94.76, 0),
      ("customerA", "txn16-custA-Jul7", "07/20/2015", 24.14, 3),
      ("customerA", "txn17-custA-Jul7", "07/20/2015", 36.16, 0)
    )).toDF("CUST_ID", "TXN_ID", "TXN_DATE", "TXN_AMT", "Cadence")

    val multiUserNoRepeats = sc.parallelize(List(
      ("custA", "txn1-custA-Oct1", "10/04/2016", 25.14, 0),
      ("custB", "txn1-custB-Oct1", "10/04/2016", 62.15, 0),
      ("custB", "txn2-custB-Oct2", "10/07/2016", 36.16, 3),
      ("custC", "txn1-custC-Oct3", "10/08/2016", 17.31, 0),
      ("custA", "txn2-custA-Oct3", "10/08/2016", 92.16, 4),
      ("custC", "txn2-custC-Oct4", "10/10/2016", 87.25, 2),
      ("custB", "txn3-custB-Oct5", "10/11/2016", 91.25, 4),
      ("custC", "txn3-custC-Oct6", "10/12/2016", 72.15, 2),
      ("custB", "txn4-custB-Oct6", "10/12/2016", 25.16, 1),
      ("custB", "txn5-custB-Oct7", "10/20/2016", 24.62, 8),
      ("custA", "txn3-custA-Oct7", "10/20/2016", 72.13, 12)
    )).toDF("CUST_ID", "TXN_ID", "TXN_DATE", "TXN_AMT", "Cadence") // 4

    val multiUserNewAndReturning = sc.parallelize(List(
      ("custA", "txn1-custA-Oct1", "10/04/2016", 25.14, 0),
      ("custB", "txn1-custB-Oct1", "10/04/2016", 62.15, 0),
      ("custB", "txn2-custB-Oct2", "10/07/2016", 36.16, 3),
      ("custC", "txn1-custC-Oct3", "10/08/2016", 17.31, 0),
      ("custC", "txn2-custC-Oct4", "10/10/2016", 87.25, 2),
      ("custB", "txn3-custB-Oct5", "10/11/2016", 91.25, 4),
      ("custC", "txn3-custC-Oct6", "10/12/2016", 72.15, 2),
      ("custB", "txn4-custB-Oct6", "10/12/2016", 25.16, 1),
      ("custB", "txn5-custB-Oct7", "10/20/2016", 24.62, 8),
      ("custD", "txn1-custD-Oct7", "10/20/2016", 25.83, 0),
      ("custD", "txn2-custD-Oct7", "10/20/2016", 15.97, 0),
      ("custD", "txn3-custD-Oct7", "10/20/2016", 39.05, 0),
      ("custB", "txn6-custB-Oct7", "10/20/2016", 15.72, 0),
      ("custC", "txn4-custC-Oct8", "10/31/2016", 51.52, 19)
    )).toDF("CUST_ID", "TXN_ID", "TXN_DATE", "TXN_AMT", "Cadence") // 6.4

    val multiUserMultiTxns = sc.parallelize(List(
      ("custA", "txn1-custA-Oct1", "10/04/2016", 25.14, 0),
      ("custB", "txn1-custB-Oct1", "10/04/2016", 62.15, 0),
      ("custB", "txn2-custB-Oct2", "10/07/2016", 36.16, 3),
      ("custC", "txn1-custC-Oct3", "10/08/2016", 17.31, 0),
      ("custC", "txn2-custC-Oct4", "10/10/2016", 87.25, 2),
      ("custC", "txn5-custC-Oct4", "10/10/2016", 13.61, 0),
      ("custB", "txn3-custB-Oct5", "10/11/2016", 91.25, 4),
      ("custC", "txn3-custC-Oct6", "10/12/2016", 72.15, 2),
      ("custB", "txn4-custB-Oct6", "10/12/2016", 25.16, 1),
      ("custB", "txn5-custB-Oct7", "10/20/2016", 24.62, 8),
      ("custD", "txn1-custD-Oct7", "10/20/2016", 25.83, 0),
      ("custD", "txn2-custD-Oct7", "10/20/2016", 15.97, 0),
      ("custD", "txn3-custD-Oct7", "10/20/2016", 39.05, 0),
      ("custB", "txn6-custB-Oct7", "10/20/2016", 15.72, 0),
      ("custC", "txn4-custC-Oct8", "10/31/2016", 51.52, 19)
    )).toDF("CUST_ID", "TXN_ID", "TXN_DATE", "TXN_AMT", "Cadence") //5.6
  }

  test("Dataframe initial transform: Cadence column added, extra columns dropped") {
    new SingleUserData {

      val cadenceDF = CadenceCalcs.daysSinceLastVisit(singleUserSingleTrans)

      val cadenceCols = cadenceDF.columns

      val columns = Array("CUST_ID", "TXN_ID", "TXN_DATE", "TXN_AMT", "Cadence")

      assert(cadenceCols === columns)

    }
  }


  //SINGLE USER TESTS

  test("Single User, single transaction cadence = 0") {
    new SingleUserData {
      val cadenceDF = CadenceCalcs.daysSinceLastVisit(singleUserSingleTrans)
      val cadence = cadenceDF
        .select("Cadence")
        .first()
        .getInt(0)

      assert(cadence === 0)
    }
  }

  test("Single User, 2 transactions, different days") {
    new SingleUserData {
      val cadenceDF = CadenceCalcs.daysSinceLastVisit(singleUser2TransJan)
      val cadence = cadenceDF
        .select("Cadence").map(_ (0)).collect()

      assert(cadence === List(0, 1))
    }
  }

  test("Single User, multiple transactions (same and different days), January") {
    new SingleUserData {
      val cadenceDF = CadenceCalcs.daysSinceLastVisit(singleUserMultiTransJan)
      val cadence = cadenceDF
        .select("Cadence").map(_ (0)).collect()

      assert(cadence === List(0, 1, 0, 1, 5))
    }
  }

  test("Single User, multiple transactions, June-July") {
    new SingleUserData {
      val cadenceDF = CadenceCalcs.daysSinceLastVisit(singleUserMultiTransSummer)
      val cadence = cadenceDF
        .select("Cadence").map(_ (0)).collect()

      assert(cadence === List(0, 4, 16, 8, 2))
    }
  }

  test("Single User, multiple transactions, leap year, Feb-March") {
    new SingleUserData {
      val cadenceDF = CadenceCalcs.daysSinceLastVisit(singleUserMultiTransLeap)
      val cadence = cadenceDF
        .select("Cadence").map(_ (0)).collect()

      assert(cadence === List(0, 7, 7, 2, 3))
    }
  }

  test("Single User, multiple transactions, Daylight savings start and end") {
    new SingleUserData {
      val cadenceDF = CadenceCalcs.daysSinceLastVisit(singleUserMultiTransSavings)
      val cadence = cadenceDF
        .select("Cadence").map(_ (0)).collect()

      assert(cadence === List(0, 3, 2, 39, 22, 46, 15, 40, 35, 19, 0, 18, 4))
    }
  }

  test("Single User, multiple transactions, 2015-2016") {
    new SingleUserData {
      val cadenceDF = CadenceCalcs.daysSinceLastVisit(singleUserMultiTransJan)
      val cadence = cadenceDF
        .select("Cadence").map(_ (0)).collect()

      assert(cadence === List(0, 6, 4, 5, 0))

    }
  }

  //MULTI USER TESTS

  test("Multiple Users, 1 with single transaction, 1 with 2 transactions") {
    new MultiUserData {
      val cadenceDF = CadenceCalcs.daysSinceLastVisit(multiUserSingleAndDouble)
      val custACadence = cadenceDF
        .where("CUST_ID = 'custA'")
        .select("Cadence").map(_ (0)).collect()
      val custBCadence = cadenceDF
        .where("CUST_ID = 'custB'")
        .select("Cadence")
        .first()
        .getInt(0)

      assert(custACadence === List(0, 6))
      assert(custBCadence === 0)
    }
  }

  test("Multiple Users, both with 2 transactions, single month") {
    new MultiUserData {
      val cadenceDF = CadenceCalcs.daysSinceLastVisit(multiUserDoubleNov)
      val custACadence = cadenceDF
        .where("CUST_ID = 'custA'")
        .select("Cadence").map(_ (0)).collect()
      val custBCadence = cadenceDF
        .where("CUST_ID = 'custB'")
        .select("Cadence").map(_ (0)).collect()

      assert(custACadence === List(0, 6))
      assert(custBCadence === List(0, 3))
    }
  }

  test("Multiple Users, multiple transactions, single year") {
    new MultiUserData {
      val cadenceDF = CadenceCalcs.daysSinceLastVisit(multiUserDoubleNov)
      val custACadence = cadenceDF
        .where("CUST_ID = 'custA'")
        .select("Cadence").map(_ (0)).collect()
      val custBCadence = cadenceDF
        .where("CUST_ID = 'custB'")
        .select("Cadence").map(_ (0)).collect()
      val custCCadence = cadenceDF
        .where("CUST_ID = 'custC'")
        .select("Cadence").map(_ (0)).collect()

      assert(custACadence === List(0, 27, 8, 19))
      assert(custBCadence === List(0, 9, 27, 20, 17))
      assert(custCCadence === List(0, 33, 45))
    }
  }

  test("Multiple Users, multiple transactions, multiple years") {
    new MultiUserData {
      val cadenceDF = CadenceCalcs.daysSinceLastVisit(multiUserDoubleNov)
      val custACadence = cadenceDF
        .where("CUST_ID = 'custA'")
        .select("Cadence").map(_ (0)).collect()
      val custBCadence = cadenceDF
        .where("CUST_ID = 'custB'")
        .select("Cadence").map(_ (0)).collect()
      val custCCadence = cadenceDF
        .where("CUST_ID = 'custC'")
        .select("Cadence").map(_ (0)).collect()

      assert(custACadence === List(0, 26, 8, 21))
      assert(custBCadence === List(0, 9, 26, 21, 17))
      assert(custCCadence === List(0, 32, 46, 0, 11))
    }
  }

  test("Everything mixed together") {
    new MultiUserData {
      val cadenceDF = CadenceCalcs.daysSinceLastVisit(multiUserDoubleNov)
      val custACadence = cadenceDF
        .where("CUST_ID = 'custA'")
        .select("Cadence").map(_ (0)).collect()
      val custBCadence = cadenceDF
        .where("CUST_ID = 'custB'")
        .select("Cadence").map(_ (0)).collect()
      val custCCadence = cadenceDF
        .where("CUST_ID = 'custC'")
        .select("Cadence").map(_ (0)).collect()
      val custDCadence = cadenceDF
        .where("CUST_ID = 'custD'")
        .select("Cadence")
        .first()
        .getInt(0)
      val custECadence = cadenceDF
        .where("CUST_ID = 'custE'")
        .select("Cadence")
        .first()
        .getInt(0)
      val custFCadence = cadenceDF
        .where("CUST_ID = 'custF'")
        .select("Cadence").map(_ (0)).collect()

      assert(custACadence === List(0, 26, 8, 21))
      assert(custBCadence === List(0, 9, 26, 21, 17))
      assert(custCCadence === List(0, 32, 46, 0, 11))
      assert(custDCadence === 0)
      assert(custECadence === 0)
      assert(custFCadence === List(0, 0, 0))
    }

  }

  //CALCULATE OVERALL CADENCE VALUE

  test("Single user, no multi visits on same day") {
    new DaysData {
      val cadence = CadenceCalcs.calculateCadenceValue(singleUser6Days)
      assert(cadence === 9.6)
    }
  }

  test("Single user, multi visits on same day (not first)") {
    new DaysData {
      val cadence = CadenceCalcs.calculateCadenceValue(singleUser6Txns4Days)
      assert(cadence === 9.6)
    }
  }

  test("Single user, multi visits, first and later") {
    new DaysData {
      val cadence = CadenceCalcs.calculateCadenceValue(singleUserRepeats)
      assert(cadence === 4.4)
    }
  }

  test("MultiUser, multi visits, no repeats") {
    new DaysData {
      val cadence = CadenceCalcs.calculateCadenceValue(multiUserNoRepeats)
      assert(cadence === 4)
    }
  }

  test("MultiUser, 1 w/ single visit, 1 w/ multi visits on only day of visits, others normal") {
    new DaysData {
      val cadence = CadenceCalcs.calculateCadenceValue(multiUserNewAndReturning)
      assert(cadence === 6.4)
      //if cadence = 4.8 then not removing only day repeats properly
    }
  }

  test("MultiUser, repeats for all users (single day and multi day)") {
    new DaysData {
      val cadence = CadenceCalcs.calculateCadenceValue(multiUserMultiTxns)
      assert(cadence === 5.6)
    }
  }

  //CADENCE ROUNDING

  test("cadence = 5 days, should round to 7 days") {
    new CadenceNormalizingData {
      val seven = CadenceCalcs.normalizeCadenceValue(fiveDays)
      assert(seven === 7)
    }
  }

  test("cadence = 7 days, no rounding") {
    new CadenceNormalizingData {
      val seven = CadenceCalcs.normalizeCadenceValue(sevenDays)
      assert(seven === 7)
    }
  }

  test("cadence = 8 days, should round to 14 days") {
    new CadenceNormalizingData {
      val fourteen = CadenceCalcs.normalizeCadenceValue(eightDays)
      assert(fourteen === 14)
    }
  }

  test("cadence = 13 days, should round to 14 days") {
    new CadenceNormalizingData {
      val fourteen = CadenceCalcs.normalizeCadenceValue(thirteenDays)
      assert(fourteen === 14)
    }
  }

  test("cadence = 14 days, no rounding") {
    new CadenceNormalizingData {
      val fourteen = CadenceCalcs.normalizeCadenceValue(fourteenDays)
      assert(fourteen === 14)
    }
  }

  test("cadence = 24 days, should round to 30 days") {
    new CadenceNormalizingData {
      val thirty = CadenceCalcs.normalizeCadenceValue(twentyFourDays)
      assert(thirty === 30)
    }
  }

  test("cadence = 45 days, should round to 60 days") {
    new CadenceNormalizingData {
      val sixty = CadenceCalcs.normalizeCadenceValue(fortyFiveDays)
      assert(sixty === 60)
    }
  }

  test("cadence = 90 days, should round to 92 days") {
    new CadenceNormalizingData {
      val ninetyTwo = CadenceCalcs.normalizeCadenceValue(ninetyDays)
      assert(ninetyTwo === 92)
    }
  }

  test("cadence = 145 days, should round to 183 days") {
    new CadenceNormalizingData {
      val sixMonths = CadenceCalcs.normalizeCadenceValue(roundTo183)
      assert(sixMonths === 183)
    }
  }

  test("cadence = 245 days, should round to 365 days") {
    new CadenceNormalizingData {
      val oneYear = CadenceCalcs.normalizeCadenceValue(roundTo365Days)
      assert(oneYear === 365)
    }
  }

}
