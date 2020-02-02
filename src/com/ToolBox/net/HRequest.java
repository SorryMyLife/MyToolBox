package com.ToolBox.net;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.GZIPInputStream;

import com.ToolBox.util.StringTool;

/**
 * <p>
 * ����ʱ�䣺2019��1��23�� ����7:10:20
 * <p>
 * ��Ŀ���ƣ�ToolBox
 * 
 * <p>
 * ��˵����
 * ����HttpURLConnection��ܹ���
 * 
 * @version 1.0
 * @since JDK 1.8 �ļ����ƣ�Request.java
 */
public class HRequest {

	private String UserAgent = "", Cookie = "", Header = "", encode = "" ;
	private int timeout = 0 , code = -1;
	private String headers[] = null;
	HttpURLConnection huc = null;

	/**
	 * <p>
	 * ��������
	 */
	public void check(String url_name) throws Exception {
		huc = (HttpURLConnection) new URL(url_name).openConnection();
	}

	/**
	 * <p>
	 * ��ȡ����
	 */
	public String getHost(String host) {
		return new StringTool().getByString(host, "//(.+?/)", "//|/");
	}

	/**
	 * <p>
	 * ��ȡ�ļ�·��
	 */
	public String getPath(String host) {
		host = host.replaceAll("//", "");
		return host.substring(host.indexOf("/"));
	}

	/**
	 * <p>
	 * ����get����
	 */
	protected HttpURLConnection Get(String url_name) {
		try {
			check(url_name);
			huc.setRequestMethod("GET");
			if (!getHeader().equals("")) {
				huc.setRequestProperty(getHeader().split(":")[0], getHeader().split(":")[1].replaceAll("\\s+", ""));
			} else if (getHeaders() != null) {
				for (String s : getHeaders()) {
					String arr[] = s.split(":");
					huc.setRequestProperty(arr[0], arr[1].replaceAll("\\s+", ""));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return huc;
	}
	
	/**
	 * <p>
	 * ����post����
	 * <p>
	 * ��Ҫ������
	 */
	protected HttpURLConnection Post(String host, String param) {
		try {
			check(host);
			huc.setRequestMethod("POST");
			if (!getHeader().equals("")) {
				huc.setRequestProperty(getHeader().split(":")[0], getHeader().split(":")[1].replaceAll("\\s+", ""));
			} else if (getHeaders() != null) {
				for (String s : getHeaders()) {
					String arr[] = s.split(":");
					huc.setRequestProperty(arr[0], arr[1].replaceAll("\\s+", ""));
				}
			}
			// huc.setRequestProperty("Content-Length", huc.getContentLength()+"");
			huc.setDoOutput(true);
			huc.setDoInput(true);
			OutputStreamWriter w = new OutputStreamWriter(huc.getOutputStream());
			w.write(param);
			w.flush();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return huc;
	}

	/**
	 * <p>
	 * ��ȡ��ҳԴ��
	 */
	private String Page(HttpURLConnection s) {
		String line = "";
		StringBuilder tmp = new StringBuilder();
		if (getEncode().equals("")) {
			setEncode("utf-8");
		}
		try {
			setCode(s.getResponseCode());
			BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream(), getEncode()));
			while ((line = br.readLine()) != null) {
				tmp.append(line+"\n");
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tmp.toString();
	}

	/**
	 * <p>
	 * ��ȡgzip�����ʽ����ҳԴ��
	 */
	private String GzipPage(HttpURLConnection s) {
		String line = "";
		StringBuilder tmp = new StringBuilder();
		if (getEncode().equals("")) {
			setEncode("utf-8");
		}
		try {
			setCode(s.getResponseCode());
			BufferedReader br = new BufferedReader(
					new InputStreamReader(new GZIPInputStream(s.getInputStream()), getEncode()));
			while ((line = br.readLine()) != null) {
				tmp.append(line+"\n");
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tmp.toString();
	}
	
	public String getPostPage(String url_name , String param) {
		return Page(Post(url_name, param));
	}
	
	public String getGzipPostPage(String url_name , String param) {
		return GzipPage(Post(url_name, param));
	}
	
	
	public String getGzipPage(String url_name) {
		return GzipPage(Get(url_name));
	}
	
	public String getPage(String url_name) {
		return Page(Get(url_name));
	}
	
	/**
	 * <p>
	 * ��ȡget������ҳԴ��
	 */
	public String getResponse(String url_name) {
		return getPage(url_name).replaceAll("\\n|\\r|\\t|\\v", "");
	}

	/**
	 * <p>
	 * ��ȡpost������ҳԴ��
	 */
	public String getPostResponse(String url_name, String param) {
		return getPostPage(url_name,param).replaceAll("\\n|\\r|\\t|\\v", "");
	}

	/**
	 * <p>
	 * ��ȡget����gzip��ʽ��ҳԴ��
	 */
	public String getGzipResponse(String url_name) {
		return getGzipPage(url_name).replaceAll("\\n|\\r|\\t|\\v", "");
	}

	/**
	 * <p>
	 * ��ȡpost����gzip��ʽ��ҳԴ��
	 */
	public String getGzipPostResponse(String url_name, String param) {
		return getGzipPostPage(url_name, param).replaceAll("\\n|\\r|\\t|\\v", "");
	}

	/**
	 * <p>
	 * ��ȡ���õ�����ͷ����
	 */
	public String[] getHeaders() {
		return headers;
	}

	/**
	 * <p>
	 * ��������ͷ����
	 */
	public void setHeaders(String[] headers) {
		this.headers = headers;
	}

	/**
	 * <p>
	 * ��ȡ���õ������
	 */
	public String getUserAgent() {
		return UserAgent;
	}

	/**
	 * <p>
	 * ��ȡ���õ�cookie
	 */
	public String getCookie() {
		return Cookie;
	}

	/**
	 * <p>
	 * ��ȡ���õ�����ͷ
	 */
	public String getHeader() {
		return Header;
	}

	/**
	 * <p>
	 * ��ȡ���õ��������
	 */
	public String getEncode() {
		return encode;
	}

	/**
	 * <p>
	 * ���������
	 */
	public void setUserAgent(String userAgent) {
		UserAgent = userAgent;
	}

	/**
	 * <p>
	 * ����cookie
	 */
	public void setCookie(String cookie) {
		Cookie = cookie;
	}

	/**
	 * <p>
	 * ��������ͷ
	 */
	public void setHeader(String header) {
		Header = header;
	}

	/**
	 * <p>
	 * �����������
	 */
	public void setEncode(String encode) {
		this.encode = encode;
	}

	/**
	 * <p>
	 * ��ȡ���õĳ�ʱ
	 */
	public int getTimeout() {
		return timeout;
	}

	/**
	 * <p>
	 * ���ó�ʱ
	 */
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

}
