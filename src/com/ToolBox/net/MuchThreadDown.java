package com.ToolBox.net;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

public class MuchThreadDown {

	/**
	 * �����Ƶ�ַ��https://blog.csdn.net/qq_32101859/article/details/53177428
	 * 
	 */

	// private String path = "http://mpge.5nd.com/2016/2016-11-15/74847/1.mp3";
	// //����·��
	private String path, targetFilePath , fileName;
	private int threadCount; // �߳�����

	public boolean printLog = false;

	public boolean isPrintLog() {
		return printLog;
	}

	public void setPrintLog(boolean printLog) {
		this.printLog = printLog;
	}

	/**
	 * ���췽��
	 * 
	 * @param path
	 *            Ҫ�����ļ�������·��
	 * @param targetFilePath
	 *            ���������ļ���Ŀ¼
	 * @param threadCount
	 *            �������߳�����,Ĭ��Ϊ 3
	 */
	public MuchThreadDown(String path, String targetFilePath, int threadCount) {
		this.path = path;
		this.targetFilePath = targetFilePath;
		this.threadCount = threadCount;
	}

	
	public MuchThreadDown(String path, String targetFilePath, String fileName, int threadCount) {
		this.path = path;
		this.targetFilePath = targetFilePath;
		this.threadCount = threadCount;
		this.fileName = fileName;
	}
	
	private HttpURLConnection check() throws Exception {
		HttpURLConnection connection = (HttpURLConnection) new URL(path).openConnection();
		connection.setRequestMethod("GET");
		connection.setConnectTimeout(10000);
		return connection;
	}

	/**
	 * �����ļ�
	 */
	public void download() {
		try {
			HttpURLConnection connection = check();
			int code = connection.getResponseCode();
			if (code == 200) {
				// ��ȡ��Դ��С
				int connectionLength = connection.getContentLength();
				if (isPrintLog()) {
					System.err.println("file size : " + connectionLength + " bytes");
					System.err.println("file save to : " + targetFilePath + "/"+fileName!=null?fileName:getFileName(path));
				}
				// �ڱ��ش���һ������Դͬ����С���ļ���ռλ
				RandomAccessFile randomAccessFile = new RandomAccessFile(new File(targetFilePath, fileName!=null?fileName:getFileName(path)),
						"rw");
				randomAccessFile.setLength(connectionLength);
				/*
				 * ��������������ÿ���߳�
				 */
				int blockSize = connectionLength / threadCount;// ����ÿ���߳����������ص�����.
				for (int threadId = 0; threadId < threadCount; threadId++) {// Ϊÿ���̷߳�������
					int startIndex = threadId * blockSize; // �߳̿�ʼ���ص�λ��
					int endIndex = (threadId + 1) * blockSize - 1; // �߳̽������ص�λ��
					if (threadId == (threadCount - 1)) { // ��������һ���߳�,��ʣ�µ��ļ�ȫ����������߳����
						endIndex = connectionLength - 1;
					}
					new DownloadThread(threadId, startIndex, endIndex).start();// �����߳�����
				}
				// randomAccessFile.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// ���ص��߳�
	private class DownloadThread extends Thread {

		private int threadId;
		private int startIndex;
		private int endIndex;

		public DownloadThread(int threadId, int startIndex, int endIndex) {
			this.threadId = threadId;
			this.startIndex = startIndex;
			this.endIndex = endIndex;
		}

		@Override
		public void run() {
			if (isPrintLog()) {
				System.err.println("Thread[" + threadId + "] start download !");
			}
			try {
				// ��������λ�õ��ļ�
				File downThreadFile = new File(targetFilePath, "downThread_" + threadId + ".dt");
				RandomAccessFile downThreadStream = null;
				if (downThreadFile.exists()) {// ����ļ�����
					downThreadStream = new RandomAccessFile(downThreadFile, "rwd");
					String startIndex_str = downThreadStream.readLine();
					if (null != startIndex_str || !"".equals(startIndex_str)) { // ���� imonHu 2017/5/22
						this.startIndex = Integer.parseInt(startIndex_str) - 1;// �����������
					}
				} else {
					downThreadStream = new RandomAccessFile(downThreadFile, "rwd");
				}

				HttpURLConnection connection = check();
				// ���÷ֶ����ص�ͷ��Ϣ�� Range:���ֶ����������õġ���ʽ: Range bytes=0-1024 ���� bytes:0-1024
				connection.setRequestProperty("Range", "bytes=" + startIndex + "-" + endIndex);

				if (isPrintLog()) {
					System.err.println("Thread[" + threadId + "] start : " + startIndex + "  ---  end : " + endIndex);
				}

				if (connection.getResponseCode() == 206) {// 200������ȫ����Դ�ɹ��� 206��������Դ����ɹ�
					InputStream inputStream = connection.getInputStream();// ��ȡ��
					RandomAccessFile randomAccessFile = new RandomAccessFile(
							new File(targetFilePath, fileName!=null?fileName:getFileName(path)), "rw");// ��ȡǰ���Ѵ������ļ�.
					randomAccessFile.seek(startIndex);// �ļ�д��Ŀ�ʼλ��.

					/*
					 * ���������е��ļ�д�뱾��
					 */
					byte[] buffer = new byte[1024];
					int length = -1;
					int total = 0;// ��¼���������ļ��Ĵ�С
					while ((length = inputStream.read(buffer)) > 0) {
						randomAccessFile.write(buffer, 0, length);
						total += length;
						/*
						 * ����ǰ���ڵ���λ�ñ��浽�ļ���
						 */
						downThreadStream.seek(0);
						downThreadStream.write((startIndex + total + "").getBytes("UTF-8"));
					}

					downThreadStream.close();
					inputStream.close();
					randomAccessFile.close();
					cleanTemp(downThreadFile);// ɾ����ʱ�ļ�
					if (isPrintLog()) {
						System.err.println("Thread[" + threadId + "] download ok !");
					}
				} else {
					System.err.println("response code : " + connection.getResponseCode() + " , not support thread download !!!!!");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// ɾ���̲߳�������ʱ�ļ�
	private synchronized void cleanTemp(File file) {
		file.delete();
	}

	// ��ȡ�����ļ�������
	private String getFileName(String url) {
		return url.substring(url.lastIndexOf("/") + 1);
	}

}