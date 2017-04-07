package aswathy

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.sql._

object SparkJob
{
  def main(args: Array[String]) = 
  {
    val conf = new SparkConf()
      .setAppName("SparkJob")
      .setMaster("local")
    val sc = new SparkContext(conf)
    val sqlContext = new org.apache.spark.sql.SQLContext(sc)
    import sqlContext.implicits._
    //Format JSON files to valid jsons
    val adEventsJson = sc.textFile(args(0))
                         .map(_.substring(32))
                         .map(_.replace("}, {", ","))
                         
    val assetsJson = sc.textFile(args(1))
                      .map(_.substring(32))
                      .map(_.replace("}, {", ","))
                      
    val ad_events = sqlContext.jsonRDD(adEventsJson)
    val assets_events = sqlContext.jsonRDD(assetsJson)
    
    ad_events.registerTempTable("AD_EVENTS")
    assets_events.registerTempTable("ASSETS") 
    
    val SQLVIEWS = sqlContext.sql("SELECT B.PV, B.CT, COUNT(A.e) FROM AD_EVENTS A, (SELECT ASSETS.pv as PV, COUNT(ASSETS.pv) as CT FROM ASSETS LEFT JOIN AD_EVENTS ON AD_EVENTS.pv=ASSETS.pv WHERE (AD_EVENTS.e='click' or AD_EVENTS.e='view') GROUP BY ASSETS.pv) AS B WHERE A.pv=B.PV and (A.e='view') group by B.PV, B.CT, A.e")
    val SQLCLICKS = sqlContext.sql("SELECT B.PV, B.CT, COUNT(A.e) FROM AD_EVENTS A, (SELECT ASSETS.pv as PV, COUNT(ASSETS.pv) as CT FROM ASSETS LEFT JOIN AD_EVENTS ON AD_EVENTS.pv=ASSETS.pv where (AD_EVENTS.e='click' or AD_EVENTS.e='view') GROUP BY ASSETS.pv) AS B WHERE A.pv=B.PV and (A.e='click') group by B.PV, B.CT, A.e")
    
    SQLVIEWS.registerTempTable("SQLVIEWS")
    SQLCLICKS.registerTempTable("SQLCLICKS")
    val fileOp = sqlContext.sql("SELECT DISTINCT SQLVIEWS.PV AS pv, SQLVIEWS.CT AS asset_impressions, SQLVIEWS._c2 AS views, COALESCE(SQLCLICKS._c2,0) AS clicks FROM SQLVIEWS LEFT JOIN SQLCLICKS ON SQLVIEWS.PV=SQLCLICKS.PV")
    val rows: org.apache.spark.rdd.RDD[org.apache.spark.sql.Row] = fileOp.rdd
    rows.coalesce(1, false).saveAsTextFile("output")
    sc.stop
  }
}