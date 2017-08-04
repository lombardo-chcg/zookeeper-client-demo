package services

import org.apache.zookeeper.{CreateMode, ZooDefs}
import org.apache.zookeeper._
import scala.collection.JavaConverters._

trait ZookeeperHelper {
  def nodeExists(path: String): Boolean
  def createNode(path: String, data: String): String
  def getData(path: String) : String
  def setData(path: String, data: String): String
}

class ZookeeperHelperImpl(zkConn: ZooKeeper) extends ZookeeperHelper {
  def nodeExists(path: String): Boolean = {
    if (zkConn.exists(path, true) == null ) false else true
  }

  def createNode(path: String, data: String): String = {
    zkConn.create(path, data.getBytes, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT)
  }

  def getData(path: String) : String = {
    val returnedBytes = zkConn.getData(path, false, null)
    new String(returnedBytes, "UTF-8")
  }

  def setData(path: String, data: String) : String = {
    val node = zkConn.exists(path, true)
    val stat = zkConn.setData(path, data.getBytes, node.getVersion)
    stat.toString
  }

  def getChildren(path: String): List[String] = {
    zkConn.getChildren(path, false).asScala.toList
  }

}
