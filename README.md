# DView8-SSH-Server
DView8-SSH-Server
###1,运行pom.xml导入相关jre包
 
###2.运行SshServerApplication.java 启动DView8-SSH-Server

###3.利用Postman工具便可访问 http://localhost:8080/DView8-SSH-Server/sshCommand 连接到Telnet服务器 并执行相应命令
   example.png为范例截图
   
###4.代码逻辑
   当前台发送ssh命令到sshCommand接口服务后，该接口服务将命令压到队列，并等待执行结果
   SshThread线程为该命名调起一个SshOperation线程，并把返回结果保存到静态map
