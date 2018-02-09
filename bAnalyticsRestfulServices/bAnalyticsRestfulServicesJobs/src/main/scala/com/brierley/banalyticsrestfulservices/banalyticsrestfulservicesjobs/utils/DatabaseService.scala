package com.brierley.banalyticsrestfulservices.banalyticsrestfulservicesjobs.utils

import org.apache.hadoop.hbase.client._
import org.apache.hadoop.hbase.util.Bytes
import org.apache.hadoop.hbase.{HBaseConfiguration, TableName}
import org.apache.hadoop.hbase.filter.{CompareFilter, FilterList, SingleColumnValueFilter}

class DatabaseService extends Config {

  private val hbaseConfig = HBaseConfiguration.create
  hbaseConfig.set("hbase.zookeeper.quorum", zkQuorum)
  hbaseConfig.set("hbase.zookeeper.property.clientPort", zkPort)
  hbaseConfig.set("zookeeper.znode.parent", zkPath)

  private val db = ConnectionFactory.createConnection(hbaseConfig)

  def getData(tableName: String, id: String): Result = {
    val table = db.getTable(TableName.valueOf(tableName))

    val get = new Get(Bytes.toBytes(id))

    val result = table.get(get)

    table.close()

    result

  }

  def getRows(tableName: String): ResultScanner = {
    val table = db.getTable(TableName.valueOf(tableName))

    val scan = table.getScanner(new Scan())

    table.close()

    scan
  }

  def jobNameExists(client: String, jobName:String): Boolean = {

    val family = Bytes.toBytes("JobHistory")
    val clientNameColumn = Bytes.toBytes("client")
    val jobNameColumn = Bytes.toBytes("jobName")

    val table = db.getTable(TableName.valueOf("JobHistory"))

    val clientFilter = new SingleColumnValueFilter(family, clientNameColumn, CompareFilter.CompareOp.EQUAL, Bytes.toBytes(client))
    val jobNameFilter = new SingleColumnValueFilter(family, jobNameColumn, CompareFilter.CompareOp.EQUAL, Bytes.toBytes(jobName))

    val filterList = new FilterList(FilterList.Operator.MUST_PASS_ALL)
    filterList.addFilter(clientFilter)
    filterList.addFilter(jobNameFilter)


    val scan = new Scan().setFilter(filterList)

    val scanner = table.getScanner(scan)

    val result = if(scanner.next() == null) false else true

    scanner.close()
    table.close()

    result
  }

  def getTableRef(tableName: String): Table = {
    db.getTable(TableName.valueOf(tableName))
  }
}
