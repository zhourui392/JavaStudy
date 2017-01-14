package zk;

import org.apache.zookeeper.*;

import java.io.IOException;

/**
 * Created by zhourui on 2016/11/6.
 */
public class Create {

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        ZooKeeper zk = new ZooKeeper("192.168.1.203:2181", 60000, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                System.out.println("State : " + watchedEvent.getState());
            }
        });

        zk.create("/kafka",null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);


        zk.close();
    }
}
