package services

import org.apache.curator.framework.{CuratorFramework, CuratorFrameworkFactory}
import org.apache.curator.framework.recipes.cache.{PathChildrenCache, PathChildrenCacheEvent, PathChildrenCacheListener}
import org.apache.curator.retry.ExponentialBackoffRetry
import org.apache.zookeeper.KeeperException.NodeExistsException

class CuratorCacheExample {
  val zkConnectString = sys.env("ZOOKEEPER_CONNECTION_STRING")
  val path = "/little"
  val nestedPath1 = path + "/dragon"
  val nestedPath2 = path + "/louis"
  val data1 = "first bit of data"
  val data2 = "second bit"
  val data3 = "third"
  val data4 = "fourth"

  private def prettyPrintEvent(event: PathChildrenCacheEvent): (String, String) = {
    event.getData.getPath -> new String(event.getData.getData, "UTF-8")
  }

  def newCacheExample() = {
    val retryPolicy = new ExponentialBackoffRetry(1000, 3)
    val client = CuratorFrameworkFactory.newClient(zkConnectString, retryPolicy)
    val cache = new PathChildrenCache(client, path, true)
    cache.getListenable.addListener(new PathChildrenCacheListener {
      override def childEvent(client: CuratorFramework, event: PathChildrenCacheEvent): Unit = {
        event.getType match {
          case PathChildrenCacheEvent.Type.CHILD_ADDED => println(s"Child added: ${prettyPrintEvent(event)}")
          case PathChildrenCacheEvent.Type.CHILD_REMOVED => println(s"Child removed: ${prettyPrintEvent(event)}")
          case PathChildrenCacheEvent.Type.CHILD_UPDATED => println(s"Child updated: ${prettyPrintEvent(event)}")
          case _ => println(event.getType)
        }

      }
    })

    client.start
    cache.start
//    cache.clearAndRefresh

    List(path, nestedPath1, nestedPath2).foreach(path => {
      if (client.checkExists.creatingParentContainersIfNeeded.forPath(path) == null)
        try { client.create.creatingParentContainersIfNeeded.forPath(path) }
        catch { case e:NodeExistsException => println(e.getMessage)}
      Thread.sleep(500)
    })

    client.setData.forPath(nestedPath1, data1.getBytes)
    Thread.sleep(500)
    client.setData.forPath(nestedPath2, data2.getBytes)
    Thread.sleep(500)
    client.setData.forPath(nestedPath1, data3.getBytes)
    Thread.sleep(500)
    client.setData.forPath(nestedPath2, data4.getBytes)
    Thread.sleep(500)

    cache.close
    client.close
  }
}
