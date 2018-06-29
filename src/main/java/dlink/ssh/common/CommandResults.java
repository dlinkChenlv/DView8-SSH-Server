package dlink.ssh.common;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.Future;

/**
 * Created by 91680 on 2018.6.13.
 */
public class CommandResults {
    static HashMap<UUID, Future> commandResult = new HashMap<>();
    private static class SingletonHolder{
        /**
         * 静态初始化器，由JVM来保证线程安全
         */
        private  static CommandResults queue = new CommandResults();
    }
    //单例队列
    public static CommandResults getcommandResultsQueue(){
        return CommandResults.SingletonHolder.queue;
    }
    public static void  produce(UUID taskid, Future future) throws InterruptedException {
        commandResult.put(taskid, future);
    }
    //消费出队
    public static  Future consume(UUID taskid) throws InterruptedException {
        Future rest=commandResult.get(taskid);
        return rest;
    }
    public static  void remove(UUID taskid) throws InterruptedException {
        commandResult.remove(taskid);
    }
}
