package com.brianxia.test;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.sql.SparkSession;

/**
 * @author brianxia
 * @version 1.0
 * @date 2021/7/10 11:35
 */
public class HudiTest2 {

    public static void main(String[] args) {
        System.setProperty("HADOOP_USER_NAME", "root");
        SparkConf hudiTest = new SparkConf().setAppName("HudiTest")
                .set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
                .setMaster("local[*]");
        SparkSession sparkSession = SparkSession.builder().config(hudiTest).enableHiveSupport().getOrCreate();
        SparkContext sparkContext = sparkSession.sparkContext();
        sparkContext.hadoopConfiguration().set("dfs.client.use.datanode.hostname", "true");
    }
}
