package services

import org.apache.zookeeper.{CreateMode, ZooDefs, ZooKeeper}
import org.apache.zookeeper.data.Stat
import scala.collection.JavaConverters._

class BasicImpl(zkConn: ZooKeeper) extends ZooKeeperHelperInterface {
  override def nodeExists(path: String): Boolean = {
    if (zkConn.exists(path, true) == null ) false else true
  }

  override def createNode(path: String, data: String): String = {
    zkConn.create(path, data.getBytes, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT)
  }

  override def getData(path: String): String = {
    val returnedBytes = zkConn.getData(path, false, null)
    new String(returnedBytes, "UTF-8")
  }

  override def setData(path: String, data: String): Stat = {
    val node = zkConn.exists(path, true)
    zkConn.setData(path, data.getBytes, node.getVersion)
  }

  override def getChildren(path: String): List[String] = {
    zkConn.getChildren(path, false).asScala.toList
  }
}
