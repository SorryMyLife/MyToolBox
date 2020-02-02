package com.ToolBox.net;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import javax.net.ssl.SSLSocketFactory;

import com.ToolBox.util.HtmlTool;
import com.ToolBox.util.StringTool;

/**
 * <p>
 * 创建时间：2019年6月22日 下午4:09:50
 * <p>
 * 项目名称：ToolBox
 * 
 * <p>
 * 类说明： 基于socket重构网络工具
 * 
 * @version 1.0
 * @since JDK 1.8 文件名称：NewRequest.java
 */
public class SRequest {
	private String host, port, method, protocol, path, filename, url, http, encode, line, postParam;
	private final static String head = "Connection: keep-alive";
	private String headers[] = null;
	private int timeout = 800;
	private final static StringTool st = new StringTool();
	private Socket socket = null;
	private PrintWriter pw = null;
	private InputStream inputStream = null;

	public void ThreadDownload(boolean printLog, String downloadLink, String savePath, int ThreadNum) {
		MuchThreadDown m = new MuchThreadDown(downloadLink, savePath, ThreadNum);
		m.setPrintLog(printLog);
		m.download();
	}

	public String getFileName(String url_name) {
		return url_name.substring(url_name.lastIndexOf("/") + 1);
	}

	/**
	 * </p>
	 * 配置必要参数
	 */
	public void check() throws Exception {
		if (url != null | !url.equals("")) {
			if (url.indexOf("http") == -1) {
				url = "http://" + url + "/";
			}
			if (getProtocol() == null) {
				setProtocol(url != null ? url.substring(0, url.lastIndexOf("//")).replace(":", "") : "");
			}
			if (getHost() == null) {
				setHost(st.getByString(url, getProtocol() + "://(.+?/)", getProtocol() + "|/|:"));
			}

			if (getPath() == null) {
				setPath(url.replace(getProtocol() + "://" + getHost(), ""));
			}

			if (getMethod() == null) {
				throw new Exception("please set request method!");
			} else {
				if (!(getMethod().equals("POST") | getMethod().equals("GET") | getMethod().equals("get")
						| getMethod().equals("POST"))) {
					throw new Exception("please set request method on GET or POST");
				}
			}

			if (getFilename() == null) {
				setFilename(url.substring(url.lastIndexOf("/") + 1).equals("") ? "index"
						: url.substring(url.lastIndexOf("/") + 1));
			}

			if (getHeaders() == null) {
				setMethod("GET");
				String hs[] = { head, "Host:" + getHost() };
				setHeaders(hs);
			}
			if (getTimeout() == timeout) {
				setTimeout(timeout);
			}

			if (getHttp() == null) {
				setHttp("1.1");
			}

			if (getEncode() == null) {
				setEncode("utf-8");
			}

			if (getProtocol().equals("https")) {
				socket = SSLSocketFactory.getDefault().createSocket(
						InetAddress.getByName(getHost().replaceAll("\\s+", "")), Integer.parseInt(port = "443"));
			} else if (getProtocol().equals("http")) {
				socket = new Socket(InetAddress.getByName(getHost().replaceAll("\\s+", "")),
						Integer.parseInt(port = "80"));
			} else {
				throw new Exception("not support this port : " + getProtocol());
			}
			socket.setSoTimeout(getTimeout());
			pw = new PrintWriter(socket.getOutputStream());
			pw.write(getMethod() + " " + getPath() + " HTTP/" + getHttp() + "\n");
			for (String s : getHeaders()) {
				System.err.println(s);
				pw.write(s + "\n");
			}
		} else {
			throw new Exception("please set url!");
		}
	}

	/**
	 * </p>
	 * 配置GET参数
	 */
	private void GET() throws Exception {
		setMethod("GET");
		check();
		pw.println();
		pw.flush();
		inputStream = socket.getInputStream();
	}

	/**
	 * </p>
	 * 配置POST参数
	 */
	private void POST(String param) throws Exception {
		setMethod("POST");
		check();
		pw.write("Content-Length: " + param.length() + "\n");
		pw.write("\n");
		pw.write(param);
		pw.write("\n");
		pw.flush();
		inputStream = socket.getInputStream();
	}

	/**
	 * </p>
	 * 获取GET请求网页源码
	 */
	public String getPage(String url_name) {
		System.err.println(url_name);
		setUrl(url_name + "/");
		return getPage();
	}

	/**
	 * </p>
	 * 获取GET请求网页源码
	 */
	public String getPage() {
		StringBuilder sb = new StringBuilder();
		try {
			GET();
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, getEncode()));
			while ((line = br.readLine()) != null) {
				sb.append(line + "\n");
			}
			br.close();
		} catch (SocketTimeoutException s) {

		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	/**
	 * </p>
	 * 获取gzip格式传输的GET请求网页源码
	 */
	public String getGzipPage(String url_name) {
		setUrl(url_name + "/");
		return getGzipPage();
	}

	/**
	 * </p>
	 * 获取gzip格式传输的GET请求网页源码
	 */
	public String getGzipPage() {
		StringBuilder sb = new StringBuilder();
		try {
			GET();
			BufferedReader br = new BufferedReader(
					new InputStreamReader(new GZIPInputStream(inputStream), getEncode()));
			while ((line = br.readLine()) != null) {
				sb.append(line + "\n");
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	/**
	 * </p>
	 * 获取GET请求网页源码
	 */
	public String getResponse(String url_name) {
		setUrl(url_name + "/");
		return getResponse();
	}

	/**
	 * </p>
	 * 获取GET请求网页源码
	 */
	public String getResponse() {
		return getPage().replaceAll("\\n|\\r|\\t|\\v", "");
	}

	/**
	 * </p>
	 * 获取gzip格式传输的GET请求网页源码
	 */
	public String getGzipResponse(String url_name) {
		setUrl(url_name + "/");
		return getGzipResponse();
	}

	/**
	 * </p>
	 * 获取gzip格式传输的GET请求网页源码
	 */
	public String getGzipResponse() {
		return getGzipPage().replaceAll("\\n|\\r|\\t|\\v", "");
	}

	/**
	 * </p>
	 * 获取POST请求网页源码
	 */
	public String getPostPage(String url_name, String param) {
		setUrl(url_name + "/");
		return getPostPage(param);
	}

	/**
	 * </p>
	 * 获取POST请求网页源码
	 */
	public String getPostPage() {
		return getPostPage(getPostParam());
	}

	/**
	 * </p>
	 * 获取POST请求网页源码
	 */
	public String getPostPage(String param) {
		StringBuilder sb = new StringBuilder();
		try {
			POST(param);
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, getEncode()));
			while ((line = br.readLine()) != null) {
				sb.append(line + "\n");
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	/**
	 * </p>
	 * 获取gzip格式传输的POST请求网页源码
	 */
	public String getGzipPostPage(String url_name, String param) {
		setUrl(url_name + "/");
		return getGzipPostPage(param);
	}

	/**
	 * </p>
	 * 获取gzip格式传输的POST请求网页源码
	 */
	public String getGzipPostPage() {
		return getGzipPostPage(getPostParam());
	}

	/**
	 * </p>
	 * 获取gzip格式传输的POST请求网页源码
	 */
	public String getGzipPostPage(String param) {
		StringBuilder sb = new StringBuilder();
		try {
			POST(param);
			BufferedReader br = new BufferedReader(
					new InputStreamReader(new GZIPInputStream(inputStream), getEncode()));
			while ((line = br.readLine()) != null) {
				sb.append(line + "\n");
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	/**
	 * </p>
	 * 获取POST请求网页源码
	 */
	public String getPostResponse(String url_name, String param) {
		setUrl(url_name + "/");
		return getPostResponse(param);
	}

	/**
	 * </p>
	 * 获取POST请求网页源码
	 */
	public String getPostResponse() {
		return getPostResponse(getPostParam());
	}

	/**
	 * </p>
	 * 获取POST请求网页源码
	 */
	public String getPostResponse(String param) {
		return getPostPage(param).replaceAll("\\n|\\r|\\t|\\v", "");
	}

	/**
	 * </p>
	 * 获取gzip格式传输的POST请求网页源码
	 */
	public String getGzipPostResponse() {
		return getGzipPostResponse(getPostParam());
	}

	/**
	 * </p>
	 * 获取gzip格式传输的POST请求网页源码
	 */
	public String getGzipPostResponse(String url_name, String param) {
		setUrl(url_name + "/");
		return getGzipPostResponse(param);
	}

	/**
	 * </p>
	 * 获取gzip格式传输的POST请求网页源码
	 */
	public String getGzipPostResponse(String param) {
		return getGzipPostPage(param).replaceAll("\\n|\\r|\\t|\\v", "");
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

	/**
	 * </p>
	 * 返回response头部为Map集合
	 */
	public Map<String, String> ResponseHeaders(String url_name) {
		Map<String, String> headerlist = new HashMap<>();
		for (String s : st.getByAllString(checkMethod(url_name).replaceAll("\\n", "----"), "HTT(.+?<)", "<")
				.split("----")) {
			String value = s.substring(s.indexOf(":") + 1);
			String key = s.replace(":" + value, "");
			headerlist.put(key, value);
		}
		return headerlist;
	}

	/**
	 * </p>
	 * 返回response头部
	 */
	public String getResponseHeader(String url_name, String name) {
		for (String s : st.getByAllString(checkMethod(url_name), "HTT(.+?<)", "<")
				.split("----")) {
			String value = s.substring(s.indexOf(":") + 1);
			String key = s.replace(":" + value, "");
			if (key.equals(name)) {
				return value;
			}
		}
		return "";
	}

	/**
	 * </p>
	 * 返回response头部
	 */
	public String getResponseHeader(String name) {
		return getResponseHeader(getUrl(), name);
	}

	/**
	 * </p>
	 * 返回response头部为Map集合
	 */
	public Map<String, String> ResponseHeaders() {
		return ResponseHeaders(getUrl());
	}

	public String checkMethod(String url_name) {
		return getMethod().equals("POST") ? getPostPage(url_name, getPostParam()) : getPage(url_name);
	}

	/**
	 * </p>
	 * 返回response头部为字符串
	 */
	public String getResponseHeaders(String url_name) {
		StringBuilder sb = new StringBuilder();
		for (String s : st.getByAllString(checkMethod(url_name), "HTT(.+?<)", "<")
				.split("----")) {
			sb.append(s + "\n");
		}
		return sb.toString();
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
			if (!name.exists()) {
				try {
					setUrl(url_name + "/");
					GET();
					byte[] buff = new byte[1];
					int len = -1;
					InputStream input = socket.getInputStream();
					FileOutputStream fos = new FileOutputStream(name);
					System.err.println(url_name + " start Downlaod ! ");
					while ((len = input.read(buff)) != -1) {
						fos.write(buff, 0, len);
					}
					fos.close();
					input.close();
					System.err.println("Downlaod ok !  -- save to : " + name);
				} catch (SocketTimeoutException e) {

				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				System.err.println(name + " exists !");
			}
		}
	}

	/**
	 * </p>
	 * 返回response头部为字符串
	 */
	public String getResponseHeaders() {
		return getResponseHeaders(getUrl());
	}

	/**
	 * </p>
	 * 返回设置的链接
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * </p>
	 * 设置链接
	 */
	public void setUrl(String url) {
		this.url = url;

	}

	/**
	 * </p>
	 * 构造函数，需要传递一个链接
	 */
	public SRequest(String url) {
		super();
		this.url = url;
	}

	/**
	 * </p>
	 * 返回设置的协议
	 */
	public String getProtocol() {
		return protocol;
	}

	/**
	 * </p>
	 * 返回设置的文件名称
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * </p>
	 * 返回设置的超时长度
	 */
	public int getTimeout() {
		return timeout;
	}

	/**
	 * </p>
	 * 设置协议
	 */
	public void setProtocol(String protocol) {
		this.protocol = protocol;

	}

	/**
	 * </p>
	 * 设置链接路径
	 */
	public void setPath(String path) {
		this.path = path;

	}

	/**
	 * </p>
	 * 设置文件名称
	 */
	public void setFilename(String filename) {
		this.filename = filename;

	}

	/**
	 * </p>
	 * 设置超时
	 */
	public void setTimeout(int timeout) {
		this.timeout = timeout;

	}

	/**
	 * </p>
	 * 返回设置的链接路径
	 */
	public String getPath() {
		return path;
	}

	/**
	 * </p>
	 * 返回设置的请求头数组
	 */
	public String[] getHeaders() {
		return headers;
	}

	/**
	 * </p>
	 * 设置请求头数组
	 */
	public void setHeaders(String[] headers) {
		this.headers = headers;

	}

	/**
	 * </p>
	 * 构造函数
	 */
	public SRequest() {
		super();
	}

	/**
	 * </p>
	 * 设置请求头数组，需要传入域名跟端口，默认为http协议
	 */
	public SRequest(String host, String port) {
		super();
		this.host = host;
		this.port = port;
		setUrl("http://" + host + "/");
	}

	/**
	 * </p>
	 * 返回设置的域名
	 */
	public String getHost() {
		return host;
	}

	/**
	 * </p>
	 * 返回设置的端口
	 */
	public String getPort() {
		return port;
	}

	/**
	 * </p>
	 * 返回设置的请求方法
	 */
	public String getMethod() {
		return method;
	}

	/**
	 * </p>
	 * 设置域名
	 */
	public void setHost(String host) {
		this.host = host;

	}

	/**
	 * </p>
	 * 设置端口
	 */
	public void setPort(String port) {
		this.port = port;

	}

	/**
	 * </p>
	 * 设置请求方法
	 */
	public void setMethod(String method) {
		this.method = method;

	}

	/**
	 * </p>
	 * 返回设置的HTTP协议版本
	 */
	public String getHttp() {
		return http;
	}

	/**
	 * </p>
	 * 设置HTTP协议版本
	 */
	public void setHttp(String http) {
		this.http = http;

	}

	/**
	 * </p>
	 * 返回设置的编码
	 */
	public String getEncode() {
		return encode;
	}

	/**
	 * </p>
	 * 设置编码
	 */
	public void setEncode(String encode) {
		this.encode = encode;
	}

	/**
	 * </p>
	 * 返回设置的post参数
	 */
	public String getPostParam() {
		return postParam;
	}

	/**
	 * </p>
	 * 设置post参数
	 */
	public void setPostParam(String postParam) {
		this.postParam = postParam;
	}

}
