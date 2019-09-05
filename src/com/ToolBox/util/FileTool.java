package com.ToolBox.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
* <p>创建时间：2019年1月27日 下午8:17:14
* <p>项目名称：ToolBox
* 
* <p>类说明：
*
* @version 1.0
* @since JDK 1.8
* 文件名称：FileTool.java
* */
public class FileTool {
	
	/**<p>读取文件*/
	public String readFile(String file)
	{
		String  t = "";
		StringBuilder s = new StringBuilder();
		try {
			File f = new File(file);
			if (!f.exists()) {
				throw new Exception("没有这个文件！！！");
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
	
	/**<p>写入文件*/
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
			System.err.println("写入完成");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	/**</p>返回路径内所有文件*/
	public String getAllFiles(String path) {
		StringBuilder sb = new StringBuilder();
		for(File f :new File(path).listFiles()) {
			sb.append(f.toString()+"\n");
		}
		return sb.toString();
	}
	
	/**</p>以二进制方式合并文件*/
	public void BinaryFileMerge(File files[] , File outFile) throws IOException {
		FileOutputStream fos = new FileOutputStream(outFile);
		int len = -1;
		byte buff[] = new byte[1024];
		for(int i = 0;i<files.length;i++) {
			DataInputStream dataInputStream = new DataInputStream(new FileInputStream(files[i]));
			while((len = dataInputStream.read(buff)) != -1) {
				fos.write(buff, 0, len);
			}
			dataInputStream.close();
		}
		fos.close();
		System.err.println("merge ok !");
	}
	
	
	
	
}
