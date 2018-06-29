package dlink.ssh.Server;

import dlink.ssh.common.SSHResInfo;
import dlink.ssh.common.ShhCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;

/**
 * Created by 91680 on 2018.6.12.
 */
public class SshOperation implements Callable<SSHResInfo> {
    /**
     * 日志
     */
    private static final Logger LOG = LoggerFactory.getLogger(SshServerImpl.class);

    private ShhCommand shhCommand;
    private SshServer shServers;
    public SshOperation(ShhCommand shhCommand) {
        this.shhCommand=shhCommand;
        shServers=new SshServerImpl();
    }
    @Override
    public SSHResInfo call() throws Exception {
        //连接sftp服务器
        shServers.connect(shhCommand.getHost(),shhCommand.getPort(),shhCommand.getUsername(),shhCommand.getPassword());
        //执行命令，返回执行结果
        SSHResInfo result=shServers.sendCmd(shhCommand.getCommand(),200);
        return result;
    }
}
