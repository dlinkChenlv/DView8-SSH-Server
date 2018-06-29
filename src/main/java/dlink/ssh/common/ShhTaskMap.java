package dlink.ssh.common;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by 91680 on 2018.6.14.
 */
public class ShhTaskMap {
    static HashMap<UUID, ShhCommand> ShhTaskMaps = new HashMap<>();
    private static class SingletonHolder{
        /**
         * 静态初始化器，由JVM来保证线程安全
         */
        private  static ShhTaskMap queue = new ShhTaskMap();
    }
    //单例队列
    public static ShhTaskMap getShhTaskMap(){
        return ShhTaskMap.SingletonHolder.queue;
    }
    public static void  produce(UUID taskid, ShhCommand shhCommand) throws InterruptedException {
        ShhTaskMaps.put(taskid, shhCommand);
    }
    //消费出队
    public static  ShhCommand consume(UUID taskid) throws InterruptedException {
        ShhCommand rest=ShhTaskMaps.get(taskid);
        return rest;
    }
    public static  void remove(UUID taskid) throws InterruptedException {
        ShhTaskMaps.remove(taskid);
    }
}
