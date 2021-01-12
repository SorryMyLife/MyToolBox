package com.ToolBox.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * <p>
 * 创建时间：2019年1月27日 下午8:17:14
 * <p>
 * 项目名称：ToolBox
 * 
 * <p>
 * 类说明： 封装文件操作，使之更加便捷
 *
 * @version 1.0
 * @since JDK 1.8 文件名称：FileTool.java
 */
public class FileTool {

	public String encode = "utf-8";

	public String getEncode() {
		return encode;
	}

	public void setEncode(String encode) {
		this.encode = encode;
	}

	//清理不带match符号的所有文件内容
	public void delNoMatchFiles(File path, String match) {
		for (File f : path.listFiles()) {
			if (f.toString().indexOf(match) == -1) {
				f.delete();
			}
		}
	}

	public void delFolder(String dirPath) {
		delFolder(new File(dirPath));
	}

	public void delFolder(File dirPath) {
		if (dirPath.exists() && dirPath.isDirectory()) {
			for (File f : dirPath.listFiles()) {
				if (f.isDirectory()) {
					delFolder(f);
				} else {
					f.delete();
				}
			}
		}
	}

	/**
	 * <p>
	 * 读取文件
	 */
	public String readFile(File file) {
		String t = "";
		StringBuilder s = new StringBuilder();
		try {
			if (!file.exists()) {
				throw new Exception("没有这个文件！！！");
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), getEncode()));
			while ((t = br.readLine()) != null) {
				s.append(t + "\n");
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s.toString();
	}

	/**
	 * <p>
	 * 读取文件
	 */
	public String readFile(String file) {
		File f = new File(file);
		return readFile(f);
	}

	/**
	 * <p>
	 * 写入文件
	 */
	public void writeFile(String data, File file) {
		try {
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), getEncode()));
			bw.write(data);
			bw.flush();
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * <p>
	 * 写入文件
	 */
	public void writeFile(String data, String file) {
		File f = new File(file);
		if (f.exists()) {
			writeFile(data, file + "-b");
		}
		writeFile(data, f);

	}

	/**
	 * </p>
	 * 返回路径内所有文件
	 */
	public String getAllFiles(String path) {
		StringBuilder sb = new StringBuilder();
		for (File f : new File(path).listFiles()) {
			sb.append(f.toString() + "\n");
		}
		return sb.toString();
	}

	/**
	 * </p>
	 * 以二进制方式合并文件
	 */
	public void BinaryFileMerge(File files[], File outFile) throws IOException {
		FileOutputStream fos = new FileOutputStream(outFile);
		int len = -1;
		byte buff[] = new byte[1024];
		for (int i = 0; i < files.length; i++) {
			DataInputStream dataInputStream = new DataInputStream(new FileInputStream(files[i]));
			while ((len = dataInputStream.read(buff)) != -1) {
				fos.write(buff, 0, len);
			}
			dataInputStream.close();
		}
		fos.close();
	}

}
