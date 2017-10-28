package com.example.zookeeper.zookeeper.main;

import com.example.utils.LogUtil;
import com.example.zookeeper.zookeeper.watcher.ConnectionWatcher;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.util.concurrent.CountDownLatch;

/**
 * Created by louyuting on 2017/6/6.
 *
 * 同步创建结点
 */
public class CreateNodeSync {
    private ZooKeeper zk;// client
    CountDownLatch latch = new CountDownLatch(1);// 闭锁

    public void connect() throws Exception{
        zk = new ZooKeeper(Constant.HOST1, Constant.SESSION_TIMEOUT,
                new ConnectionWatcher(latch));
        latch.await();// 等待连接完毕
    }
    /**
     * 创建结点
     * @param nodeName  结点的名称，不需要以 / 开头
     * @throws Exception
     */
    public void create(String nodeName) throws Exception{
        String path = "/" + nodeName;
        // 默认不加 ACL
        String createdPath = zk.create(path, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        LogUtil.log_debug(" created " + createdPath);

    }
    /**
     * 创建结点
     * @param nodeName  结点的名称，不需要以 / 开头
     * @param value   结点的数据
     * @throws Exception
     */
    public void create(String nodeName, String value) throws Exception{
        String path = "/" + nodeName;
        // 默认不加 ACL， 即结点的任何操作都不受权限控制
        String createdPath = zk.create(path, value.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        LogUtil.log_debug("createdPath：" + createdPath);
    }

    public void close() throws Exception{
        zk.close();
    }

}
