package dlink.ssh.Server;

import com.jcraft.jsch.*;
import dlink.ssh.common.SSHResInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by 91680 on 2018.6.12.
 */
public class SshServerImpl implements SshServer{
    private Session session;
    /**
     * 日志
     */
    private static final Logger LOG = LoggerFactory.getLogger(SshServerImpl.class);

    @Override
    public boolean connect(String host, Integer port, String user, String password) {
        boolean connect=false;
        try {
            JSch jsch = new JSch();
            if (port != null) {
                session = jsch.getSession(user, host, port.intValue());
            } else {
                session = jsch.getSession(user, host);
            }
            session.setPassword(password);
            //设置第一次登陆的时候提示，可选值:(ask | yes | no)
            session.setConfig("StrictHostKeyChecking", "no");
            //30秒连接超时
            session.connect(5000);
            return true;
        } catch (JSchException e) {
            e.printStackTrace();
            LOG.info(" Getting a connection error");
            return connect;
        }

    }

    @Override
    public SSHResInfo sendCmd(String command, int delay){
        if (delay < 50) {
            delay = 50;
        }
        SSHResInfo result = null;
        byte[] tmp = new byte[1024]; //读数据缓存
        StringBuffer strBuffer = new StringBuffer();  //执行SSH返回的结果
        StringBuffer errResult = new StringBuffer();
        Channel channel = null;
        try {
            channel = session.openChannel("exec");
            ChannelExec ssh= (ChannelExec)channel;
            //返回的结果可能是标准信息,也可能是错误信息,所以两种输出都要获取
            //一般情况下只会有一种输出.
            //但并不是说错误信息就是执行命令出错的信息,如获得远程java JDK版本就以
            //ErrStream来获得.
            InputStream stdStream = ssh.getInputStream();
            InputStream errStream = ssh.getErrStream();
            ssh.setCommand(command);
            ssh.connect();
            //开始获得SSH命令的结果
            while (true) {
                //获得错误输出
                while (errStream.available() > 0) {
                    int i = errStream.read(tmp, 0, 1024);
                    if (i < 0) break;
                    errResult.append(new String(tmp, 0, i));
                }
                //获得标准输出
                while (stdStream.available() > 0) {
                    int i = stdStream.read(tmp, 0, 1024);
                    if (i < 0) break;
                    strBuffer.append(new String(tmp, 0, i));
                }
                if (ssh.isClosed()) {
                    int code = ssh.getExitStatus();
                    LOG.info("exit-status: " + code);
                    result = new SSHResInfo(code, strBuffer.toString(), errResult.toString());
                    break;
                }
                try {
                    Thread.sleep(delay);
                } catch (Exception ee) {
                    ee.printStackTrace();
                }
            }
        } catch (JSchException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            channel.disconnect();

        }
        return result;
    }

    @Override
    public void closeCon(){
        if (session.isConnected()){
            session.disconnect();
        }
    }
}
