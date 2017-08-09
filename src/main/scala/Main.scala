package com.lombardo.app

import connectors.{BasicConnectionApi, CuratorConnectionApi}
import org.apache.curator.framework.CuratorFramework
import org.apache.curator.framework.api.{CuratorEvent, CuratorListener}
import org.apache.zookeeper.{WatchedEvent, Watcher}
import services._
import org.apache.curator.framework.recipes.cache
import org.apache.curator.framework.recipes.cache.{PathChildrenCache, PathChildrenCacheEvent, PathChildrenCacheListener}

import scala.collection.JavaConverters._

object Main {
  val zkConnectString = sys.env("ZOOKEEPER_CONNECTION_STRING")
  val path = "/little"
  val nestedPath = path + "/dragon"
  val data1 = "i was looking at the trees"
  val data2 = "they were looking back at me"
  val data3 = "thoughts that occurred to me"
  val data4 = "not of the usual kind..."

  def main(args: Array[String]) {

//    println("***** begin zookeeper client implemention*")
//    val zkBasicConnManagerApi = new BasicConnectionApi(zkConnectString)
//    val zkConn = zkBasicConnManagerApi.getConnection
//    val zkInstanceApi = new BasicImpl(zkConn)
//
//    runZkInteractions(zkInstanceApi)
//
//    zkBasicConnManagerApi.close(zkConn)
//    println("***** end zookeeper client implemention")
//
//    println("***** begin curator client implemention*")
//    val curatorConnManagerApi = new CuratorConnectionApi(zkConnectString)
//    val curatorConn = curatorConnManagerApi.getConnection
//    val curatorInstanceApi = new CuratorImpl(curatorConn)
//
//    runZkInteractions(curatorInstanceApi)
//
//    curatorConnManagerApi.close(curatorConn)
//    println("***** end curator client implemention*")
//    newCacheExample()
      leaderExample()
  }

  def runZkInteractions(client: ZooKeeperHelperInterface) = {

    if (client.nodeExists(path)) client.setData(path, data1)
    else client.createNode(path, data1)

    println(client.getData(path))
    client.setData(path, data2)
    println(client.getData(path))

    if (client.nodeExists(nestedPath)) client.setData(nestedPath, data3)
    else client.createNode(nestedPath, data3)

    println(client.getData(nestedPath))
    client.setData(nestedPath, data4)
    println(client.getData(nestedPath))
    println(client.getChildren(path))
  }

  def leaderExample() = {
    val leaderEx = new ClusterService
    leaderEx.run
  }

  def newCacheExample() = {
    val cacheEx = new CuratorCacheExample
    cacheEx.newCacheExample
  }

  def cacheExample() = {
    val curatorConnManagerApi = new CuratorConnectionApi(zkConnectString)
    val client = curatorConnManagerApi.getConnection

    val listener = new CuratorListener {
      override def eventReceived(client: CuratorFramework, event: CuratorEvent): Unit = {
        println("HIIIIIYE " + event.getPath + event.getType)
      }
    }

    val watcher = new Watcher {
      override def process(event: WatchedEvent) = {
        println(event.getPath)
      }
    }

    client.getCuratorListenable.addListener(listener)
    client.getChildren().usingWatcher(watcher).forPath(path)

    client.setData.forPath(nestedPath, "FUCK SANWICH #2".getBytes)

//    client.setData.inBackground.forPath(path, "FUCK SANWICH".getBytes)

    if (client.checkExists.forPath(path) != null) client.setData.forPath(path, data1.getBytes)
    else client.create.forPath(path, data1.getBytes)

    client.setData.forPath(nestedPath, "FUCK SANWICH #231".getBytes)


    curatorConnManagerApi.close(client)
  }
}
