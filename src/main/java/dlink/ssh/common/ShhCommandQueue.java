package dlink.ssh.common;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
/**
 * SHH命令队列
 * Created by 91680 on 2018.6.12.
 *
 */
public class ShhCommandQueue {
	 //队列大小
    static final int QUEUE_MAX_SIZE   = 1000;

    static BlockingQueue<ShhCommand> blockingQueue = new LinkedBlockingQueue<ShhCommand>(QUEUE_MAX_SIZE);
    
    /**
     * 私有的默认构造子，保证外界无法直接实例化
     */
    private ShhCommandQueue(){};
    /**
     * 类级的内部类，也就是静态的成员式内部类，该内部类的实例与外部类的实例
     * 没有绑定关系，而且只有被调用到才会装载，从而实现了延迟加载
     */
    private static class SingletonHolder{
        /**
         * 静态初始化器，由JVM来保证线程安全
         */
		private  static ShhCommandQueue queue = new ShhCommandQueue();
    }
    //单例队列
    public static ShhCommandQueue getShhCommandQueue(){
        return SingletonHolder.queue;
    }
    //生产入队
    public  void  produce(ShhCommand shhCommand) throws InterruptedException {
    	blockingQueue.put(shhCommand);
    }
    //消费出队
    public  ShhCommand consume() throws InterruptedException {
        return blockingQueue.take();
    }
    // 获取队列大小
    public int size() {
        return blockingQueue.size();
    }
}
