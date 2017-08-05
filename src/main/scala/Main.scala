package com.lombardo.app

import connectors.{BasicConnectionApi, CuratorConnectionApi}
import services.{BasicImpl, CuratorImpl, ZooKeeperHelperInterface}

object Main {
  def main(args: Array[String]) {
    val zkConnectString = sys.env("ZOOKEEPER_CONNECTION_STRING")

    println("***** begin zookeeper client implemention*")
    val zkBasicConnManagerApi = new BasicConnectionApi(zkConnectString)
    val zkConn = zkBasicConnManagerApi.getConnection
    val zkInstanceApi = new BasicImpl(zkConn)

    runZkInteractions(zkInstanceApi)

    zkBasicConnManagerApi.close(zkConn)
    println("***** end zookeeper client implemention")

    println("***** begin curator client implemention*")
    val curatorConnManagerApi = new CuratorConnectionApi(zkConnectString)
    val curatorConn = curatorConnManagerApi.getConnection
    val curatorInstanceApi = new CuratorImpl(curatorConn)

    runZkInteractions(curatorInstanceApi)

    curatorConnManagerApi.close(curatorConn)
    println("***** end curator client implemention*")
  }

  def runZkInteractions(client: ZooKeeperHelperInterface) = {
    val path = "/little"
    val nestedPath = path + "/dragon"
    val data1 = "i was looking at the trees"
    val data2 = "they were looking back at me"
    val data3 = "thoughts that occurred to me"
    val data4 = "not of the usual kind..."

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
}
