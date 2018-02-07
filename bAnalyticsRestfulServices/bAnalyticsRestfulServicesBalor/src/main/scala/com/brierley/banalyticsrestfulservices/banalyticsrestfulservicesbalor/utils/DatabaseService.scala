package com.brierley.banalyticsrestfulservices.banalyticsrestfulservicesbalor.utils

//Hbase imports
import org.apache.hadoop.hbase.client.{ConnectionFactory, Result}
import org.apache.hadoop.hbase.util.Bytes
import org.apache.hadoop.hbase.{HBaseConfiguration, TableName}
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.hbase.client.Get

class DatabaseService extends Config{

  private val hbaseConfig = HBaseConfiguration.create
  hbaseConfig.set("hbase.zookeeper.quorum", zkQuorum)
  hbaseConfig.set("hbase.zookeeper.property.clientPort", zkPort)
  hbaseConfig.set("zookeeper.znode.parent", zkPath)

  private val db = ConnectionFactory.createConnection(hbaseConfig)

  def getData(tableName:String, id:String): Result = {
    val table = db.getTable(TableName.valueOf(tableName))

    val get = new Get(Bytes.toBytes(id))

    val result = table.get(get)

    table.close()

    result

  }
}
