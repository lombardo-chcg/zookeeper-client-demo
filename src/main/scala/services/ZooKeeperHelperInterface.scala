package services

import org.apache.zookeeper.data.Stat

trait ZooKeeperHelperInterface {
  def nodeExists(path: String): Boolean
  def createNode(path: String, data: String): String
  def getData(path: String) : String
  def setData(path: String, data: String): Stat
  def getChildren(path: String): List[String]
}
