package services

import org.apache.curator.framework.CuratorFramework
import org.apache.zookeeper.data.Stat
import scala.collection.JavaConverters._

class CuratorImpl(client: CuratorFramework) extends ZooKeeperHelperInterface {
  override def nodeExists(path: String): Boolean = {
    if (client.checkExists.forPath(path) == null ) false else true
  }

  override def createNode(path: String, data: String): String = {
    client.create.forPath(path, data.getBytes)
  }

  override def getData(path: String): String = {
    val returnedBytes = client.getData.forPath(path)
    new String(returnedBytes, "UTF-8")
  }

  override def setData(path: String, data: String): Stat = {
    client.setData.forPath(path, data.getBytes)
  }

  override def getChildren(path: String): List[String] = {
    client.getChildren.forPath(path).asScala.toList
  }
}
