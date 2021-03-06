package dlink.ssh;

/**
 * Created by 91680 on 2018.6.15.
 */


import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

import org.apache.commons.net.telnet.TelnetClient;

public class telnetTest2 {
    /**
     * 新建一个TelnetClient对象
     */
    private TelnetClient telnetClient = new TelnetClient();
    /**
     * 系统标示符号
     */
    private final String osTag = "\r\n";
    private final String user = "admin";
    private final String password = "admin";
    /**
     * get Value 系统标示符号
     */
    private final String getValOsTag = "END\r\n";
    /**
     * 输入流，接收返回信息
     */
    private InputStream in;
    /**
     * 向 服务器写入 命令
     */
    private PrintStream out;
    public void readUntil(String str) {
        char last = str.charAt(str.length() - 1);
        String[] ss;
        try {
            StringBuffer sb = new StringBuffer();
            char c;
            int code = -1;
            boolean ansiControl = false;
            boolean start = true;
            while ((code = (in.read())) != -1) {
                c = (char) code;
                if (c == '\033') {//vt100控制码都是以\033开头的。
                    ansiControl = true;
                    int code2 = in.read();
                    char cc = (char) code2;
                    if (cc == '[' || cc == '(') {
                    }
                }
                if (!ansiControl) {
                    if (c == '\r') {
                        //这里是命令行中的每一句反馈
                        String olds = new String(sb.toString().getBytes("ISO8859-1"),"GBK");
                        System.out.println(olds);
                        if (sb.lastIndexOf(str) != -1) {
                            break;
                        }
                        sb.delete(0, sb.length());
                    } else if (c == '\n')
                        ;
                    else
                        sb.append(c);
                    if (sb.lastIndexOf(str) != -1) {
                        break;
                    }
                }

                if (ansiControl) {
                    if (('a' <= c && c <= 'z') || ('A' <= c && c <= 'Z')
                            || c == '"') {
                        ansiControl = false;
                    }
                }
            }
            System.out.println(new String(sb.toString().getBytes("ISO8859-1"),"GBK"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * @param ip   : telnet的IP地址
     * @param port : 端口号，默认11211
     */
    public telnetTest2(String ip, Integer port) {
        try {
            telnetClient.connect(ip, port);
            in = telnetClient.getInputStream();
            out = new PrintStream(telnetClient.getOutputStream());
            readUntil("login: ");
            write(user);
            write("\n");
            readUntil("Password: ");
            write(password);
            write("\n");
        } catch (Exception e) {
            System.out.println("[telnet] connect error: connect to [" + ip + ":" + port + "] fail!");
        }
    }

    public telnetTest2(String ip) {
        try {
            telnetClient.connect(ip, 11211);
            in = telnetClient.getInputStream();
            out = new PrintStream(telnetClient.getOutputStream());
        } catch (Exception e) {
            System.out.println("[telnet] connect error: connect to [" + ip + ":" + 11211 + "] fail!");
        }
    }

    /**
     * 执行telnet命令
     *
     * @param command
     * @return
     */
    public String execute(String command) {
        write(command);
        StringBuffer sb = new StringBuffer();
        String osTagX = osTag;
        if (command.startsWith("get")) {
            osTagX = getValOsTag;
        }
        try {
            char ch = (char) in.read();
            int isEnd = 0;
            while (true) {
                sb.append(ch);
                if (ch == osTagX.charAt(isEnd)) {
                    isEnd++;
                    if (sb.toString().endsWith(osTagX) && isEnd == osTagX.length())
                        return sb.toString();
                } else {
                    isEnd = 0;
                }
                ch = (char) in.read();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "error! when the program execute";
    }

    /**
     * 向telnet命令行输入命令
     *
     * @param command
     */
    public void write(String command) {
        try {
            out.println(command);
            out.flush();
            System.out.println("[telnet] 打印本次执行的telnet命令:" + command);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭Telnet连接
     */
    public void disconnect() {
        try {
            Thread.sleep(10);
            telnetClient.disconnect();
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    /**
     * 用于测试
     *
     * @param url
     * @param port
     */
    public static void testGet(String url, Integer port) {
        System.out.println("----------------------------" + url + ":" + port + "----------------------------");
        telnetTest2 telnetTest = new telnetTest2(url, port);
        String result = telnetTest.execute("get apl");
        System.out.println(result);

        String result2 = telnetTest.execute("get wll");
        System.out.println(result2);
        telnetTest.disconnect();
    }

    /**
     * 存储服务器正在清空缓存服务器缓存
     *
     * @param url
     * @param port
     */
    public static void clearCache(String url, Integer port) {
        System.out.println("[telnet] 存储服务器正在清空缓存服务器缓存[" + url + ":" + port + "]----------------------------");
        telnetTest2 telnetTest = new telnetTest2(url, port);
        String result = telnetTest.execute("flush_all");
        System.out.println(result);
        telnetTest.disconnect();
    }

    public static void main(String[] args) {
        testGet("172.18.192.201", 23);
        testGet("172.18.192.201", 23);
        clearCache("172.18.192.201", 23);
        clearCache("172.18.192.201", 23);
    }
}
