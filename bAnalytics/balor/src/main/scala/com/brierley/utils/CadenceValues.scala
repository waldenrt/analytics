package com.brierley.utils

/**
  * Created by amerrill on 2/28/17.
  */
sealed abstract class CadenceValues (val index: Int, val name: String, val periodDivisor: Int) extends Ordered[CadenceValues]{
  def compare(that: CadenceValues) = this.index - that.index
}


case object OneWeek extends CadenceValues(1, "1 Week", 7)
case object TwoWeeks extends CadenceValues(2, "2 Weeks", 14)
case object OneMonth extends CadenceValues(3, "1 Month", 1)
case object TwoMonths extends CadenceValues(4, "2 Months", 2)
case object ThreeMonths extends CadenceValues(5, "3 Months", 3)
case object SixMonths extends CadenceValues(6, "6 months", 6)
case object OneYear extends CadenceValues(7, "1 year", 12)

