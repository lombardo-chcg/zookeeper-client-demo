# zookeeper client demo

Exploring the Apache ZooKeeper client library and the Apache Curator client library  

More info:
* [https://lombardo-chcg.github.io/tools/2017/08/03/zookeeper-client-api.html](https://lombardo-chcg.github.io/tools/2017/08/03/zookeeper-client-api.html)
* [https://lombardo-chcg.github.io/tools/2017/08/03/zookeeper-client-api-refactoring.html](https://lombardo-chcg.github.io/tools/2017/08/03/zookeeper-client-api-refactoring.html)  

Requirements
* Running 3.2.1 ZooKeeper instance:
```
docker run -d -p "2181:2181" -e "ZOOKEEPER_CLIENT_PORT=2181" confluentinc/cp-zookeeper:3.2.1
```
* ZOOKEEPER_CONNECTION_STRING env var set inside IDE.  ex: `localhost:2181`