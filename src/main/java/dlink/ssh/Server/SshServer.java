package dlink.ssh.Server;

import com.jcraft.jsch.JSchException;
import dlink.ssh.common.SSHResInfo;

/**
 * Created by 91680 on 2018.6.12.
 */
public interface SshServer {
    /**
     * 连接sftp服务器
     *
     * @param host     远程主机ip地址
     * @param port     sftp连接端口，null 时为默认端口
     * @param user     用户名
     * @param password 密码
     * @return
     * @throws JSchException
     */
    public boolean connect(String host, Integer port, String user, String password);

    /*
      * 执行命令，返回执行结果
      * @param command 命令
      * @param delay 估计shell命令执行时间
      * @return String 执行命令后的返回
      * @throws IOException
      * @throws JSchException
      */
    public SSHResInfo sendCmd(String command, int delay);
    /*
      * 关闭连接

      * @throws JSchException
      */
    public void closeCon();
}
