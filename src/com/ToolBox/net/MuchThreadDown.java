package com.ToolBox.net;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

public class MuchThreadDown {

	/**
	 * 程序复制地址：https://blog.csdn.net/qq_32101859/article/details/53177428
	 * 
	 */

	// private String path = "http://mpge.5nd.com/2016/2016-11-15/74847/1.mp3";
	// //下载路径
	private String path, targetFilePath , fileName;
	private int threadCount; // 线程数量

	public boolean printLog = false;

	public boolean isPrintLog() {
		return printLog;
	}

	public void setPrintLog(boolean printLog) {
		this.printLog = printLog;
	}

	/**
	 * 构造方法
	 * 
	 * @param path
	 *            要下载文件的网络路径
	 * @param targetFilePath
	 *            保存下载文件的目录
	 * @param threadCount
	 *            开启的线程数量,默认为 3
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
	 * 下载文件
	 */
	public void download() {
		try {
			HttpURLConnection connection = check();
			int code = connection.getResponseCode();
			if (code == 200) {
				// 获取资源大小
				int connectionLength = connection.getContentLength();
				if (isPrintLog()) {
					System.err.println("file size : " + connectionLength + " bytes");
					System.err.println("file save to : " + targetFilePath + "/"+fileName!=null?fileName:getFileName(path));
				}
				// 在本地创建一个与资源同样大小的文件来占位
				RandomAccessFile randomAccessFile = new RandomAccessFile(new File(targetFilePath, fileName!=null?fileName:getFileName(path)),
						"rw");
				randomAccessFile.setLength(connectionLength);
				/*
				 * 将下载任务分配给每个线程
				 */
				int blockSize = connectionLength / threadCount;// 计算每个线程理论上下载的数量.
				for (int threadId = 0; threadId < threadCount; threadId++) {// 为每个线程分配任务
					int startIndex = threadId * blockSize; // 线程开始下载的位置
					int endIndex = (threadId + 1) * blockSize - 1; // 线程结束下载的位置
					if (threadId == (threadCount - 1)) { // 如果是最后一个线程,将剩下的文件全部交给这个线程完成
						endIndex = connectionLength - 1;
					}
					new DownloadThread(threadId, startIndex, endIndex).start();// 开启线程下载
				}
				// randomAccessFile.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 下载的线程
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
				// 加载下载位置的文件
				File downThreadFile = new File(targetFilePath, "downThread_" + threadId + ".dt");
				RandomAccessFile downThreadStream = null;
				if (downThreadFile.exists()) {// 如果文件存在
					downThreadStream = new RandomAccessFile(downThreadFile, "rwd");
					String startIndex_str = downThreadStream.readLine();
					if (null != startIndex_str || !"".equals(startIndex_str)) { // 网友 imonHu 2017/5/22
						this.startIndex = Integer.parseInt(startIndex_str) - 1;// 设置下载起点
					}
				} else {
					downThreadStream = new RandomAccessFile(downThreadFile, "rwd");
				}

				HttpURLConnection connection = check();
				// 设置分段下载的头信息。 Range:做分段数据请求用的。格式: Range bytes=0-1024 或者 bytes:0-1024
				connection.setRequestProperty("Range", "bytes=" + startIndex + "-" + endIndex);

				if (isPrintLog()) {
					System.err.println("Thread[" + threadId + "] start : " + startIndex + "  ---  end : " + endIndex);
				}

				if (connection.getResponseCode() == 206) {// 200：请求全部资源成功， 206代表部分资源请求成功
					InputStream inputStream = connection.getInputStream();// 获取流
					RandomAccessFile randomAccessFile = new RandomAccessFile(
							new File(targetFilePath, fileName!=null?fileName:getFileName(path)), "rw");// 获取前面已创建的文件.
					randomAccessFile.seek(startIndex);// 文件写入的开始位置.

					/*
					 * 将网络流中的文件写入本地
					 */
					byte[] buffer = new byte[1024];
					int length = -1;
					int total = 0;// 记录本次下载文件的大小
					while ((length = inputStream.read(buffer)) > 0) {
						randomAccessFile.write(buffer, 0, length);
						total += length;
						/*
						 * 将当前现在到的位置保存到文件中
						 */
						downThreadStream.seek(0);
						downThreadStream.write((startIndex + total + "").getBytes("UTF-8"));
					}

					downThreadStream.close();
					inputStream.close();
					randomAccessFile.close();
					cleanTemp(downThreadFile);// 删除临时文件
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

	// 删除线程产生的临时文件
	private synchronized void cleanTemp(File file) {
		file.delete();
	}

	// 获取下载文件的名称
	private String getFileName(String url) {
		return url.substring(url.lastIndexOf("/") + 1);
	}

}