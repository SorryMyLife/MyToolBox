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
 * ����ʱ�䣺2019��1��27�� ����8:17:14
 * <p>
 * ��Ŀ���ƣ�ToolBox
 * 
 * <p>
 * ��˵���� ��װ�ļ�������ʹ֮���ӱ��
 *
 * @version 1.0
 * @since JDK 1.8 �ļ����ƣ�FileTool.java
 */
public class FileTool {

	public String encode = "utf-8";

	public String getEncode() {
		return encode;
	}

	public void setEncode(String encode) {
		this.encode = encode;
	}

	//������match���ŵ������ļ�����
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
	 * ��ȡ�ļ�
	 */
	public String readFile(File file) {
		String t = "";
		StringBuilder s = new StringBuilder();
		try {
			if (!file.exists()) {
				throw new Exception("û������ļ�������");
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
	 * ��ȡ�ļ�
	 */
	public String readFile(String file) {
		File f = new File(file);
		return readFile(f);
	}

	/**
	 * <p>
	 * д���ļ�
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
	 * д���ļ�
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
	 * ����·���������ļ�
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
	 * �Զ����Ʒ�ʽ�ϲ��ļ�
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
