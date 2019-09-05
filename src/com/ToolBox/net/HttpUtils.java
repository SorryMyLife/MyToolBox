package com.ToolBox.net;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.ToolBox.util.HtmlTool;

/**
 * <p>
 * ����ʱ�䣺2019��1��23�� ����5:53:52
 * <p>
 * ��Ŀ���ƣ�ToolBox
 * 
 * <p>
 * ��˵���� http���߼�
 * 
 * @version 1.0
 * @since JDK 1.8 �ļ����ƣ�HttpUtils.java
 */
public class HttpUtils extends HRequest {
	private boolean returnResult = true;

	/**
	 * <p>
	 * ����get����
	 */
	public String sendGet(String url_name) {
		String result = getResponse(url_name);
		return isReturnResult() ? result : "";
	}

	/**
	 * <p>
	 * ����post����
	 */
	public String sendPOST(String url_name, String param) {
		String result = getPostResponse(url_name, param);
		return isReturnResult() ? result : "";
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
	 * ��������
	 * <p>
	 * String url_name , String dirPath , String fileName
	 * <p>
	 * �������ӡ�����·������������
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
					System.err.println(url_name + " start Downlaod ! ");
					while ((len = input.read(buff)) != -1) {
						fos.write(buff, 0, len);
					}					
				} catch (Exception e) {
					e.printStackTrace();
				}finally {
					try {
						fos.close();
						input.close();
						System.err.println("Downlaod ok !  -- save to : " + name);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} else {
				System.err.println(name + " exists !");
			}
		}
	}

	/**
	 * <p>
	 * �ж��Ƿ�Ҫ���ؽ��
	 */
	public boolean isReturnResult() {
		return returnResult;
	}

	/**
	 * <p>
	 * �����Ƿ�Ҫ���ؽ��
	 */
	public void setReturnResult(boolean returnResult) {
		this.returnResult = returnResult;
	}

	/**
	 * <p>
	 * ����HtmlTool����
	 */
	public HtmlTool toHtml(String url_name) {
		return new HtmlTool(getResponse(url_name));
	}

	/**
	 * <p>
	 * ����HtmlTool����
	 */
	public HtmlTool toGzipHtml(String url_name) {
		return new HtmlTool(getGzipResponse(url_name));
	}

	/**
	 * <p>
	 * ����HtmlTool����
	 */
	public HtmlTool toPostHtml(String url_name, String param) {
		return new HtmlTool(getPostResponse(url_name, param));
	}

	/**
	 * <p>
	 * ����HtmlTool����
	 */
	public HtmlTool toGzipPostHtml(String url_name, String param) {
		return new HtmlTool(getGzipPostResponse(url_name, param));
	}

}
