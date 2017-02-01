package com.brierley.balor.tests

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

  }

  trait MultiUserData {
    val sqlCtx = sqlContext

    import sqlCtx.implicits._
  }

  test("cadence column added to dataframe"){

  }

  test("Single User, single transaction cadence = 0"){

  }

  test("Single User, 2 transactions"){

  }

  test("Single User, multiple transactions, January"){

  }

  test("Single User, multiple transactions, June-July"){

  }

  test("Single User, multiple transactions, leap year, Feb-March"){

  }

  test("Single User, multiple transactions, Daylight savings start"){

  }

  test("Single User, multiple transactions, Daylight savings end"){

  }

  test("Single User, multiple transactions, 2015-2016"){

  }

  test("Multiple Users, 1 with single transaction, 1 with 2 transactions"){

  }

  test("Multiple Users, both with 2 transactions, single month"){

  }

  test("Multiple Users, multiple transactions, single year"){

  }

  test("Multiple Users, multiple transactions, multiple years"){

  }

  test("Everything mixed together"){

  }

}
