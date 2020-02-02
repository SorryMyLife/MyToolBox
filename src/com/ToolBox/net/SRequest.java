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
 * ����ʱ�䣺2019��6��22�� ����4:09:50
 * <p>
 * ��Ŀ���ƣ�ToolBox
 * 
 * <p>
 * ��˵���� ����socket�ع����繤��
 * 
 * @version 1.0
 * @since JDK 1.8 �ļ����ƣ�NewRequest.java
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
	 * ���ñ�Ҫ����
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
	 * ����GET����
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
	 * ����POST����
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
	 * ��ȡGET������ҳԴ��
	 */
	public String getPage(String url_name) {
		System.err.println(url_name);
		setUrl(url_name + "/");
		return getPage();
	}

	/**
	 * </p>
	 * ��ȡGET������ҳԴ��
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
	 * ��ȡgzip��ʽ�����GET������ҳԴ��
	 */
	public String getGzipPage(String url_name) {
		setUrl(url_name + "/");
		return getGzipPage();
	}

	/**
	 * </p>
	 * ��ȡgzip��ʽ�����GET������ҳԴ��
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
	 * ��ȡGET������ҳԴ��
	 */
	public String getResponse(String url_name) {
		setUrl(url_name + "/");
		return getResponse();
	}

	/**
	 * </p>
	 * ��ȡGET������ҳԴ��
	 */
	public String getResponse() {
		return getPage().replaceAll("\\n|\\r|\\t|\\v", "");
	}

	/**
	 * </p>
	 * ��ȡgzip��ʽ�����GET������ҳԴ��
	 */
	public String getGzipResponse(String url_name) {
		setUrl(url_name + "/");
		return getGzipResponse();
	}

	/**
	 * </p>
	 * ��ȡgzip��ʽ�����GET������ҳԴ��
	 */
	public String getGzipResponse() {
		return getGzipPage().replaceAll("\\n|\\r|\\t|\\v", "");
	}

	/**
	 * </p>
	 * ��ȡPOST������ҳԴ��
	 */
	public String getPostPage(String url_name, String param) {
		setUrl(url_name + "/");
		return getPostPage(param);
	}

	/**
	 * </p>
	 * ��ȡPOST������ҳԴ��
	 */
	public String getPostPage() {
		return getPostPage(getPostParam());
	}

	/**
	 * </p>
	 * ��ȡPOST������ҳԴ��
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
	 * ��ȡgzip��ʽ�����POST������ҳԴ��
	 */
	public String getGzipPostPage(String url_name, String param) {
		setUrl(url_name + "/");
		return getGzipPostPage(param);
	}

	/**
	 * </p>
	 * ��ȡgzip��ʽ�����POST������ҳԴ��
	 */
	public String getGzipPostPage() {
		return getGzipPostPage(getPostParam());
	}

	/**
	 * </p>
	 * ��ȡgzip��ʽ�����POST������ҳԴ��
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
	 * ��ȡPOST������ҳԴ��
	 */
	public String getPostResponse(String url_name, String param) {
		setUrl(url_name + "/");
		return getPostResponse(param);
	}

	/**
	 * </p>
	 * ��ȡPOST������ҳԴ��
	 */
	public String getPostResponse() {
		return getPostResponse(getPostParam());
	}

	/**
	 * </p>
	 * ��ȡPOST������ҳԴ��
	 */
	public String getPostResponse(String param) {
		return getPostPage(param).replaceAll("\\n|\\r|\\t|\\v", "");
	}

	/**
	 * </p>
	 * ��ȡgzip��ʽ�����POST������ҳԴ��
	 */
	public String getGzipPostResponse() {
		return getGzipPostResponse(getPostParam());
	}

	/**
	 * </p>
	 * ��ȡgzip��ʽ�����POST������ҳԴ��
	 */
	public String getGzipPostResponse(String url_name, String param) {
		setUrl(url_name + "/");
		return getGzipPostResponse(param);
	}

	/**
	 * </p>
	 * ��ȡgzip��ʽ�����POST������ҳԴ��
	 */
	public String getGzipPostResponse(String param) {
		return getGzipPostPage(param).replaceAll("\\n|\\r|\\t|\\v", "");
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

	/**
	 * </p>
	 * ����responseͷ��ΪMap����
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
	 * ����responseͷ��
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
	 * ����responseͷ��
	 */
	public String getResponseHeader(String name) {
		return getResponseHeader(getUrl(), name);
	}

	/**
	 * </p>
	 * ����responseͷ��ΪMap����
	 */
	public Map<String, String> ResponseHeaders() {
		return ResponseHeaders(getUrl());
	}

	public String checkMethod(String url_name) {
		return getMethod().equals("POST") ? getPostPage(url_name, getPostParam()) : getPage(url_name);
	}

	/**
	 * </p>
	 * ����responseͷ��Ϊ�ַ���
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
	 * ����responseͷ��Ϊ�ַ���
	 */
	public String getResponseHeaders() {
		return getResponseHeaders(getUrl());
	}

	/**
	 * </p>
	 * �������õ�����
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * </p>
	 * ��������
	 */
	public void setUrl(String url) {
		this.url = url;

	}

	/**
	 * </p>
	 * ���캯������Ҫ����һ������
	 */
	public SRequest(String url) {
		super();
		this.url = url;
	}

	/**
	 * </p>
	 * �������õ�Э��
	 */
	public String getProtocol() {
		return protocol;
	}

	/**
	 * </p>
	 * �������õ��ļ�����
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * </p>
	 * �������õĳ�ʱ����
	 */
	public int getTimeout() {
		return timeout;
	}

	/**
	 * </p>
	 * ����Э��
	 */
	public void setProtocol(String protocol) {
		this.protocol = protocol;

	}

	/**
	 * </p>
	 * ��������·��
	 */
	public void setPath(String path) {
		this.path = path;

	}

	/**
	 * </p>
	 * �����ļ�����
	 */
	public void setFilename(String filename) {
		this.filename = filename;

	}

	/**
	 * </p>
	 * ���ó�ʱ
	 */
	public void setTimeout(int timeout) {
		this.timeout = timeout;

	}

	/**
	 * </p>
	 * �������õ�����·��
	 */
	public String getPath() {
		return path;
	}

	/**
	 * </p>
	 * �������õ�����ͷ����
	 */
	public String[] getHeaders() {
		return headers;
	}

	/**
	 * </p>
	 * ��������ͷ����
	 */
	public void setHeaders(String[] headers) {
		this.headers = headers;

	}

	/**
	 * </p>
	 * ���캯��
	 */
	public SRequest() {
		super();
	}

	/**
	 * </p>
	 * ��������ͷ���飬��Ҫ�����������˿ڣ�Ĭ��ΪhttpЭ��
	 */
	public SRequest(String host, String port) {
		super();
		this.host = host;
		this.port = port;
		setUrl("http://" + host + "/");
	}

	/**
	 * </p>
	 * �������õ�����
	 */
	public String getHost() {
		return host;
	}

	/**
	 * </p>
	 * �������õĶ˿�
	 */
	public String getPort() {
		return port;
	}

	/**
	 * </p>
	 * �������õ����󷽷�
	 */
	public String getMethod() {
		return method;
	}

	/**
	 * </p>
	 * ��������
	 */
	public void setHost(String host) {
		this.host = host;

	}

	/**
	 * </p>
	 * ���ö˿�
	 */
	public void setPort(String port) {
		this.port = port;

	}

	/**
	 * </p>
	 * �������󷽷�
	 */
	public void setMethod(String method) {
		this.method = method;

	}

	/**
	 * </p>
	 * �������õ�HTTPЭ��汾
	 */
	public String getHttp() {
		return http;
	}

	/**
	 * </p>
	 * ����HTTPЭ��汾
	 */
	public void setHttp(String http) {
		this.http = http;

	}

	/**
	 * </p>
	 * �������õı���
	 */
	public String getEncode() {
		return encode;
	}

	/**
	 * </p>
	 * ���ñ���
	 */
	public void setEncode(String encode) {
		this.encode = encode;
	}

	/**
	 * </p>
	 * �������õ�post����
	 */
	public String getPostParam() {
		return postParam;
	}

	/**
	 * </p>
	 * ����post����
	 */
	public void setPostParam(String postParam) {
		this.postParam = postParam;
	}

}
