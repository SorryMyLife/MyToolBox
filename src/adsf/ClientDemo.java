package adsf;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
* <p>����ʱ�䣺2019��9��5�� ����11:11:02
* <p>��Ŀ���ƣ�ToolBox
* 
* <p>��˵����
*
* @version 1.0
* @since JDK 1.8
* �ļ����ƣ�ClientDemo.java
* */
public class ClientDemo {

	public static void main(String[] args) throws Exception {
		while(true) {
			System.out.println("�ͻ�������: ");
			Scanner sc = new Scanner(System.in);
	        Socket client = new Socket("localhost", 6666);
	        System.out.println("���ӵ�������" + client.getInetAddress().getHostAddress() + " ���˿ںţ�" + client.getPort()+" , Զ��������ַ��" + client.getRemoteSocketAddress());
	        OutputStream outToServer = client.getOutputStream();
	        DataOutputStream out = new DataOutputStream(outToServer);
	        System.out.print("������Ҫ���͵���Ϣ: ");
	        out.writeUTF(sc.nextLine());
	        InputStream inFromServer = client.getInputStream();
	        DataInputStream in = new DataInputStream(inFromServer);
	        System.out.println("����˷���:  " + in.readUTF()+"\n");
		}
//        client.close();
	}

}
