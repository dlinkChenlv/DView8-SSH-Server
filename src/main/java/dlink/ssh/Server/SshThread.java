package dlink.ssh.Server;

import dlink.ssh.common.ShhCommand;
import dlink.ssh.common.ShhCommandQueue;
import dlink.ssh.common.CommandResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by 91680 on 2018.6.12.
 */
public class SshThread extends Thread {
    /**
     * 日志
     */
    private static final Logger LOG = LoggerFactory.getLogger(SshThread.class);
    private final ExecutorService executor;//java的线程池

    public SshThread() {
        this.executor = Executors.newFixedThreadPool(100);
    }
    @Override
    public void run() {
        while (true) {
            try {
                ShhCommand shhCommand = ShhCommandQueue.getShhCommandQueue().consume();
                if (shhCommand != null) {
                    LOG.info("Total number of remaining SSH operations:{}", ShhCommandQueue.getShhCommandQueue().size());
                    Future commandResult = executor.submit(new SshOperation(shhCommand));
                    CommandResults.getcommandResultsQueue().produce(shhCommand.getTaskId(),commandResult);
                    LOG.info("Total number of remaining SSH operations:{}", commandResult.get().toString());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
