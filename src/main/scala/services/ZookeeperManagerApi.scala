package services

import java.util.concurrent.CountDownLatch

import org.apache.zookeeper.Watcher.Event.KeeperState
import org.apache.zookeeper._

class ZookeeperManagerApi {

  val connectedSignal = new CountDownLatch(1)

  def getConnection() : ZooKeeper =  {
    val zkConn = new ZooKeeper("localhost:2181", 5000, new Watcher {

      def process(we: WatchedEvent) {

        if (we.getState == KeeperState.SyncConnected) {
          connectedSignal.countDown
        }
      }
    })

    connectedSignal.await
    zkConn
  }

  def close(zkConn: ZooKeeper) = zkConn.close
}
