package com.lombardo.app

import org.apache.zookeeper.{CreateMode, ZooDefs}
import services.{ZookeeperHelper, ZookeeperHelperImpl, ZookeeperManagerApi}

object Main {
  def main(args: Array[String]) {
    println("Hello from sbt starter pack!")

    val zkManagerApi = new ZookeeperManagerApi
    val zkConn = zkManagerApi.getConnection
    val zkInstanceApi = new ZookeeperHelperImpl(zkConn)

    val path = "/little"
    val nestedPath = path + "/dragon"
    val data1 = "i was looking at the trees"
    val data2 = "they were looking back at me"
    val data3 = "thoughts that occurred to me"
    val data4 = "not of the usual kind..."

    if (zkInstanceApi.nodeExists(path)) zkInstanceApi.setData(path, data1)
    else zkInstanceApi.createNode(path, data1)

    println(zkInstanceApi.getData(path))
    zkInstanceApi.setData(path, data2)
    println(zkInstanceApi.getData(path))

    if (zkInstanceApi.nodeExists(nestedPath)) zkInstanceApi.setData(nestedPath, data3)
    else zkInstanceApi.createNode(nestedPath, data3)

    println(zkInstanceApi.getData(nestedPath))
    zkInstanceApi.setData(nestedPath, data4)
    println(zkInstanceApi.getData(nestedPath))
    println(zkInstanceApi.getChildren(path))

    zkManagerApi.close(zkConn)
  }
}
