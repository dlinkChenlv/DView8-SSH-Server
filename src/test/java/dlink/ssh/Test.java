package dlink.ssh;

/**
 * Created by 91680 on 2018.6.12.
 */
public class Test {

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
//        SshServerImpl s=new SshServerImpl();
//        s.connect("172.18.192.201", 22, "admin", "admin");
//        SSHResInfo resInfo =s.sendCmd("cd 公共;ls",200);
//        System.out.println(resInfo.toString());

        //ShhCommand ssh=new ShhCommand("172.18.192.201", 22, "admin", "admin","cd 公共;rm gg","");
        //ShhCommandQueue.getShhCommandQueue().produce(ssh);
       // ShhCommandQueue.getShhCommandQueue().produce(ssh);
       // System.out.print("剩余SSH操作总数:{}"+ShhCommandQueue.getShhCommandQueue().size());
       // SshThread st=new SshThread();
      //  st.start();
        //testSSH();
    }

//    public static void testSSH(){
//        try {
//            //使用目标服务器机上的用户名和密码登陆
//            SshController helper = new SshController("172.18.192.201", 22, "admin", "admin");
//            String command = "cd 公共;rm gg";
//            try {
//                SSHResInfo resInfo = helper.sendCmd(command);
//                System.out.println(resInfo.toString());
//                //System.out.println(helper.deleteRemoteFIleOrDir(command));
//                //System.out.println(helper.detectedFileExist(command));
//                helper.close();
//            } catch (Exception e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        } catch (JSchException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }
}