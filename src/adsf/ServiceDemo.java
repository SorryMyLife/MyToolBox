package adsf;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Scanner;

/**
* <p>����ʱ�䣺2019��9��5�� ����11:12:23
* <p>��Ŀ���ƣ�ToolBox
* 
* <p>��˵����
*
* @version 1.0
* @since JDK 1.8
* �ļ����ƣ�ServiceDemo.java
* */
public class ServiceDemo {
	
	private static class GreetingServer extends Thread
	{
	   private ServerSocket serverSocket;
	   private  ArrayList<String> list = null;
	   
	   public GreetingServer() {
		   list = new ArrayList<>();
	   }
	   
	   public GreetingServer(int port) throws IOException
	   {
	      serverSocket = new ServerSocket(port);
	      list = new ArrayList<>();
	   }
	 
	   public void run()
	   {
	      while(true)
	      {
	         try
	         {
	            System.out.println("���Ƿ����\n�ȴ�Զ�����ӣ��˿ں�Ϊ��" + serverSocket.getLocalPort() + "...");
	            Socket server = serverSocket.accept();
	            System.out.println("Զ��������ַ��" + server.getInetAddress().getHostAddress());
	            DataInputStream in = new DataInputStream(server.getInputStream());
	            String data = in.readUTF();
	            System.out.println("�ͻ��˷�����Ϣ : "+data+"\n");
	            if(data.equals("q")||data.equals("exit")) {
	            	System.out.println("break !");
	            	System.exit(-1);
	            }else if(data.indexOf("insert") != -1) {
	            	list.add(data.replaceAll("insert|\\s+", ""));
	            	System.out.println("insert ok !");
	            }else if(data.indexOf("del") != -1) {
	            	for(int i =0 ;i<list.size();i++) {
	            		if(list.get(i).equals(data.replaceAll("del|\\s+", ""))) {
	            			list.remove(i);
	            			System.out.println("remove ok !");
	            		}
	            		
	            	}
	            }else if(data.indexOf("list") != -1) {
	            	for(String s : list) {
	            		System.out.println(s);
	            	}
	            }else if(data.indexOf("change") != -1) {
	            	String str[] = data.split(" ");
	            	for(int i =0 ;i<list.size();i++) {
	            		if(list.get(i).equals(str[1])) {
	            			list.set(i, str[2]);
	            			System.out.println("change ok !");
	            		}
	            		
	            	}
	            }
	            DataOutputStream out = new DataOutputStream(server.getOutputStream());
	            out.writeUTF(server.getInetAddress().getHostAddress()+ "!");
//	            server.close();
	         }catch(SocketTimeoutException s)
	         {
	            System.out.println("Socket timed out!");
	            break;
	         }catch(IOException e)
	         {
	            e.printStackTrace();
	            break;
	         }
	      }
	   }
	 public void startWork() {  
		 try {
			new GreetingServer(6666).run();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
	}
	
	private static class Client{
		public Client() {
			// TODO Auto-generated constructor stub
		}
		
		public void work() throws Exception {
			String serverName = "localhost";
			int port = 6666;		
			Scanner sc = new Scanner(System.in);
			System.out.println("���ӵ�������" + serverName + " ���˿ںţ�" + port);
	        Socket client = new Socket(serverName, port);
	        System.out.println("Զ��������ַ��" + client.getRemoteSocketAddress());
	        OutputStream outToServer = client.getOutputStream();
	        DataOutputStream out = new DataOutputStream(outToServer);
	        out.writeUTF(sc.nextLine());
	        InputStream inFromServer = client.getInputStream();
	        DataInputStream in = new DataInputStream(inFromServer);
	        System.out.println("��������Ӧ�� " + in.readUTF());
	        client.close();
		}
		
		public void start() {
			try {
				work();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		new GreetingServer().startWork();
//		new Client().start();
	}
}
