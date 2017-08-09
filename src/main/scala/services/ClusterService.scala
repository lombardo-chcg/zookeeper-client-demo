package services

import org.apache.curator.framework.CuratorFrameworkFactory
import org.apache.curator.framework.recipes.leader.LeaderLatch
import org.apache.curator.retry.ExponentialBackoffRetry

class ClusterService {
  val zkConnectString = sys.env("ZOOKEEPER_CONNECTION_STRING")
  val retryPolicy = new ExponentialBackoffRetry(1000, 3)
  val client = CuratorFrameworkFactory.newClient(zkConnectString, retryPolicy)

  def run() = {
    client.start

    val leaderLatch = new LeaderLatch(client, "/testing-leadership")
    leaderLatch.start

    println("i am leader = " + leaderLatch.hasLeadership)

    leaderLatch.await

    println("yes! i am leader = " + leaderLatch.hasLeadership)
    println("now sleeping for 7 seconds...")
    Thread.sleep(7000)
    println("process now ending.")

    leaderLatch.close
    client.close
  }
}
