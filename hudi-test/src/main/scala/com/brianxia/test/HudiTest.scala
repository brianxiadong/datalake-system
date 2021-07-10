package com.brianxia.test

import com.brianxia.entity.YouFanEntity
import com.brianxia.utils.JsonUtil
import org.apache.spark.SparkConf
import org.apache.spark.sql.{SaveMode, SparkSession}

import scala.collection.mutable.ArrayBuffer

/**
 * @author brianxia
 * @date 2021/7/9 18:50
 * @version 1.0
 */
object HudiTest {

  def main(args: Array[String]): Unit = {
    System.setProperty("HADOOP_USER_NAME", "root")
    val sparkConf = new SparkConf().setAppName("HudiTest")
      .set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
      .setMaster("local[*]")
    val sparkSession = SparkSession.builder().config(sparkConf).enableHiveSupport().getOrCreate()
    val ssc = sparkSession.sparkContext
    ssc.hadoopConfiguration.set("dfs.client.use.datanode.hostname", "true");

    insertData(sparkSession)
  }


  def insertData(sparkSession: SparkSession) = {
    import org.apache.spark.sql.functions._
    import sparkSession.implicits._
    val commitTime = System.currentTimeMillis().toString //生成提交时间
    val df = sparkSession.read.text("/test/test11")
      .mapPartitions(partitions => {
        partitions.map(item => {
          val jsonObject = JsonUtil.getJsonData(item.getString(0))
          YouFanEntity(jsonObject.getIntValue("uid"), jsonObject.getString("uname"), jsonObject.getString("dt"))
        })
      })
    val result = df.withColumn("ts", lit(commitTime)) //添加ts 时间戳列
      .withColumn("uuid", col("uid"))
      .withColumn("hudipart", col("dt")) //增加hudi分区列
    result.write.format("org.apache.hudi")
      .option("hoodie.insert.shuffle.parallelism", 2)
      .option("hoodie.upsert.shuffle.parallelism", 2)
      .option("PRECOMBINE_FIELD_OPT_KEY", "ts") //指定提交时间列
      .option("RECORDKEY_FIELD_OPT_KEY", "uuid") //指定uuid唯一标示列
      .option("hoodie.table.name", "youfanTable")
      .option("hoodie.datasource.write.partitionpath.field", "hudipart") //分区列
      .mode(SaveMode.Overwrite)
      .save("/youfan2/data/hudi")


  }
}
