package connectors

import org.apache.curator.framework.{CuratorFramework, CuratorFrameworkFactory}
import org.apache.curator.retry.ExponentialBackoffRetry

class CuratorConnectionApi(zkConnectionString: String) {
  def getConnection(): CuratorFramework = {
    val retryPolicy = new ExponentialBackoffRetry(1000, 3)
    val client = CuratorFrameworkFactory.newClient(zkConnectionString, retryPolicy)

    client.start

    client
  }

  def close(conn: CuratorFramework) = conn.close
}
