package com.ToolBox.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

/**
* <p>����ʱ�䣺2019��1��27�� ����8:17:14
* <p>��Ŀ���ƣ�ToolBox
* 
* <p>��˵����
*
* @version 1.0
* @since JDK 1.8
* �ļ����ƣ�FileTool.java
* */
public class FileTool {
	
	/**<p>��ȡ�ļ�*/
	public String readFile(String file)
	{
		String  t = "";
		StringBuilder s = new StringBuilder();
		try {
			File f = new File(file);
			if (!f.exists()) {
				throw new Exception("û������ļ�������");
			}
			BufferedReader br = new BufferedReader(new FileReader(f));
			while((t = br.readLine()) != null)
			{
				s.append(t + "\n");
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s.toString();
	}
	
	/**<p>д���ļ�*/
	public void writeFile(String data , String file)
	{
		try {
			File f = new File(file);
			if(f.exists())
			{
				writeFile(data, file+"-b");
			}
			BufferedWriter bw = new BufferedWriter(new FileWriter(f));
			bw.write(data);
			bw.flush();
			bw.close();
			System.err.println("д�����");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	/**</p>����·���������ļ�*/
	public String getAllFiles(String path) {
		StringBuilder sb = new StringBuilder();
		for(File f :new File(path).listFiles()) {
			sb.append(f.toString()+"\n");
		}
		return sb.toString();
	}
	
}
