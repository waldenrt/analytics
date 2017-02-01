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
class BalorTest extends FunSuite with DataFrameSuiteBase{

  trait TimePeriodDatat {
    val sqlCtx = sqlContext
    import sqlCtx.implicits._

  }

  trait ClassLabelData {
    val sqlCtx = sqlContext
    import sqlCtx.implicits._

  }

  test("TimePeriod column added"){

  }

  test("Time Period breaks at daylight savings start"){

  }

  test("Time Period breaks around Leap Day"){

  }

  test("Time Period breaks at month end"){

  }

  test("Time Period breaks at year"){

  }

  test("Time Period breaks around year"){

  }

  test("Leftover data discarded"){

  }



  //CLASSIFICATION TESTS
  test("ClassLabel column added"){

  }

  test("New customer, one txn"){

  }

  test("New customer, multiple txn in latest period"){

  }

  test("New customer, really old txn (outside of balor range), and single new"){

  }

  test("Lapsed customer, no buy 2nd or 3rd period"){

  }

  test("Lapsed customer, no buy 3rd period"){

  }

  test("Reactivated customer (buy, no buy, buy"){

  }

  test("Returning customer, all 3 buys"){

  }

  test("Returning customer, buy 2nd and 3rd period"){

  }


}
