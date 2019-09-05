package adsf;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
* <p>创建时间：2019年9月5日 上午11:11:02
* <p>项目名称：ToolBox
* 
* <p>类说明：
*
* @version 1.0
* @since JDK 1.8
* 文件名称：ClientDemo.java
* */
public class ClientDemo {

	public static void main(String[] args) throws Exception {
		while(true) {
			System.out.println("客户端启动: ");
			Scanner sc = new Scanner(System.in);
	        Socket client = new Socket("localhost", 6666);
	        System.out.println("连接到主机：" + client.getInetAddress().getHostAddress() + " ，端口号：" + client.getPort()+" , 远程主机地址：" + client.getRemoteSocketAddress());
	        OutputStream outToServer = client.getOutputStream();
	        DataOutputStream out = new DataOutputStream(outToServer);
	        System.out.print("输入需要发送的消息: ");
	        out.writeUTF(sc.nextLine());
	        InputStream inFromServer = client.getInputStream();
	        DataInputStream in = new DataInputStream(inFromServer);
	        System.out.println("服务端返回:  " + in.readUTF()+"\n");
		}
//        client.close();
	}

}
