package com.ToolBox.net;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.ToolBox.util.HtmlTool;

/**
 * <p>
 * 创建时间：2019年1月23日 下午5:53:52
 * <p>
 * 项目名称：ToolBox
 * 
 * <p>
 * 类说明： http工具集
 * 
 * @version 1.0
 * @since JDK 1.8 文件名称：HttpUtils.java
 */
public class HttpUtils extends HRequest {
	private boolean returnResult = true, printLog = true;

	public boolean isPrintLog() {
		return printLog;
	}

	public void setPrintLog(boolean printLog) {
		this.printLog = printLog;
	}

	public void ThreadDownload(String downloadLink, String savePath, int ThreadNum) {
		MuchThreadDown m = new MuchThreadDown(downloadLink, savePath, ThreadNum);
		m.setPrintLog(isPrintLog());
		m.download();
	}

	public void ThreadDownload(String downloadLink, String savePath, String fileName, int ThreadNum) {
		MuchThreadDown m = new MuchThreadDown(downloadLink, savePath, fileName, ThreadNum);
		m.setPrintLog(isPrintLog());
		m.download();
	}

	public String getFileName(String url_name) {
		return url_name.substring(url_name.lastIndexOf("/") + 1);
	}

	/**
	 * <p>
	 * 发送get请求
	 */
	public String sendGet(String url_name) {
		String result = getResponse(url_name);
		return isReturnResult() ? result : "";
	}

	/**
	 * <p>
	 * 发送post请求
	 */
	public String sendPOST(String url_name, String param) {
		return isReturnResult() ? getPostResponse(url_name, param) : "";
	}

	public void threadDown(String url_name, String dirPath, String fileName) {

		new Thread(new Runnable() {
			public void run() {
				Download(url_name, dirPath, fileName);
			}
		}).start();
	}

	/**
	 * <p>
	 * 下载内容
	 * <p>
	 * String url_name , String dirPath , String fileName
	 * <p>
	 * 下载链接、保存路径、保存名称
	 */
	public void Download(String url_name, String dirPath, String fileName) {
		File dir = new File(dirPath);
		if (!dir.exists()) {
			dir.mkdirs();
			Download(url_name, dirPath, fileName);
		} else {
			File name = new File(dirPath + "/" + fileName);
			InputStream input = null;
			FileOutputStream fos = null;
			if (!name.exists()) {
				try {
					byte[] buff = new byte[1024];
					int len = -1;
					input = Get(url_name).getInputStream();
					fos = new FileOutputStream(name);
					if (isPrintLog()) {
						System.err.println(url_name + " start Downlaod ! ");
					}
					while ((len = input.read(buff)) != -1) {
						fos.write(buff, 0, len);
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					try {
						fos.close();
						input.close();
						if (isPrintLog()) {
							System.err.println("Downlaod ok !  -- save to : " + name);
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	/**
	 * <p>
	 * 判断是否要返回结果
	 */
	public boolean isReturnResult() {
		return returnResult;
	}

	/**
	 * <p>
	 * 设置是否要返回结果
	 */
	public void setReturnResult(boolean returnResult) {
		this.returnResult = returnResult;
	}

	/**
	 * <p>
	 * 返回HtmlTool对象
	 */
	public HtmlTool toHtml(String url_name) {
		return new HtmlTool(getResponse(url_name));
	}

	/**
	 * <p>
	 * 返回HtmlTool对象
	 */
	public HtmlTool toGzipHtml(String url_name) {
		return new HtmlTool(getGzipResponse(url_name));
	}

	/**
	 * <p>
	 * 返回HtmlTool对象
	 */
	public HtmlTool toPostHtml(String url_name, String param) {
		return new HtmlTool(getPostResponse(url_name, param));
	}

	/**
	 * <p>
	 * 返回HtmlTool对象
	 */
	public HtmlTool toGzipPostHtml(String url_name, String param) {
		return new HtmlTool(getGzipPostResponse(url_name, param));
	}

	

}
