package dlink.ssh.common;

import java.util.UUID;

/**
 * Created by 91680 on 2018.6.12.
 */
public class ShhCommand {
    /**
     *  任务ID
     */
    private UUID taskId;
    /**
     *  远程主机ip地址
     */
    private String host;
    /**
     *  远程主机端口
     */
    private int port;
    /**
     *  远程主机用户名
     */
    private String username;
    /**
     *  远程主机密码
     */
    private String password;
    /**
     *  远程执行的命令
     */
    private String command;
    public ShhCommand(){}
    public ShhCommand(UUID taskId,String host,int port,String username,String password,String command){
        this.taskId=taskId;
        this.host=host;
        this.port=port;
        this.username=username;
        this.password=password;
        this.command=command;
    }

    public UUID getTaskId() {
        return taskId;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getCommand() {
        return command;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public void setTaskId(UUID taskId) {
        this.taskId = taskId;
    }

    public void setHost(String host) {
        this.host = host;
    }
}
