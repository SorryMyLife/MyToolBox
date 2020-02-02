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
 * 创建时间：2019年1月23日 下午7:10:20
 * <p>
 * 项目名称：ToolBox
 * 
 * <p>
 * 类说明：
 * 基于HttpURLConnection框架构造
 * 
 * @version 1.0
 * @since JDK 1.8 文件名称：Request.java
 */
public class HRequest {

	private String UserAgent = "", Cookie = "", Header = "", encode = "" ;
	private int timeout = 0 , code = -1;
	private String headers[] = null;
	HttpURLConnection huc = null;

	/**
	 * <p>
	 * 配置链接
	 */
	public void check(String url_name) throws Exception {
		huc = (HttpURLConnection) new URL(url_name).openConnection();
	}

	/**
	 * <p>
	 * 获取域名
	 */
	public String getHost(String host) {
		return new StringTool().getByString(host, "//(.+?/)", "//|/");
	}

	/**
	 * <p>
	 * 获取文件路径
	 */
	public String getPath(String host) {
		host = host.replaceAll("//", "");
		return host.substring(host.indexOf("/"));
	}

	/**
	 * <p>
	 * 配置get请求
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
	 * 配置post请求
	 * <p>
	 * 需要带参数
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
	 * 获取网页源码
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
	 * 获取gzip传输格式的网页源码
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
	 * 获取get请求网页源码
	 */
	public String getResponse(String url_name) {
		return getPage(url_name).replaceAll("\\n|\\r|\\t|\\v", "");
	}

	/**
	 * <p>
	 * 获取post请求网页源码
	 */
	public String getPostResponse(String url_name, String param) {
		return getPostPage(url_name,param).replaceAll("\\n|\\r|\\t|\\v", "");
	}

	/**
	 * <p>
	 * 获取get请求gzip格式网页源码
	 */
	public String getGzipResponse(String url_name) {
		return getGzipPage(url_name).replaceAll("\\n|\\r|\\t|\\v", "");
	}

	/**
	 * <p>
	 * 获取post请求gzip格式网页源码
	 */
	public String getGzipPostResponse(String url_name, String param) {
		return getGzipPostPage(url_name, param).replaceAll("\\n|\\r|\\t|\\v", "");
	}

	/**
	 * <p>
	 * 获取设置的请求头数组
	 */
	public String[] getHeaders() {
		return headers;
	}

	/**
	 * <p>
	 * 设置请求头数组
	 */
	public void setHeaders(String[] headers) {
		this.headers = headers;
	}

	/**
	 * <p>
	 * 获取设置的浏览器
	 */
	public String getUserAgent() {
		return UserAgent;
	}

	/**
	 * <p>
	 * 获取设置的cookie
	 */
	public String getCookie() {
		return Cookie;
	}

	/**
	 * <p>
	 * 获取设置的请求头
	 */
	public String getHeader() {
		return Header;
	}

	/**
	 * <p>
	 * 获取设置的请求编码
	 */
	public String getEncode() {
		return encode;
	}

	/**
	 * <p>
	 * 设置浏览器
	 */
	public void setUserAgent(String userAgent) {
		UserAgent = userAgent;
	}

	/**
	 * <p>
	 * 设置cookie
	 */
	public void setCookie(String cookie) {
		Cookie = cookie;
	}

	/**
	 * <p>
	 * 设置请求头
	 */
	public void setHeader(String header) {
		Header = header;
	}

	/**
	 * <p>
	 * 设置请求编码
	 */
	public void setEncode(String encode) {
		this.encode = encode;
	}

	/**
	 * <p>
	 * 获取设置的超时
	 */
	public int getTimeout() {
		return timeout;
	}

	/**
	 * <p>
	 * 设置超时
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
