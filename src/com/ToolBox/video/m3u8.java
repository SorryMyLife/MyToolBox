package com.ToolBox.video;

import java.io.File;
import java.net.URL;

import com.ToolBox.net.HttpUtils;
import com.ToolBox.util.FileTool;
import com.ToolBox.util.StringTool;

/**
* <p>����ʱ�䣺2019��7��19�� ����5:12:35
* <p>��Ŀ���ƣ�ToolBox
* 
* <p>��˵����
*<p>�����������������������m3u8�ļ��ģ�
*<p>���ö��̲߳������أ�������������������ֹͣ������������һ�鼴�ɣ�ֱ�����������˳���
*<p>���˽�����URL����m3u8�ļ�������ֱ�Ӵӱ��ؽ�������
* @version 1.0
* @since JDK 1.8
* �ļ����ƣ�m3u8.java
* */
public class m3u8 {
	
	private String m3u8File , savePath , host;
	private URL urlLink;
	private final static StringTool st = new StringTool();

	public m3u8() {
	}
	
	public m3u8(String m3u8File) {
		this.m3u8File = m3u8File;
	}
	
	public m3u8(URL urlLink) {
		this.urlLink = urlLink;
	}
	
	public m3u8(URL urlLink, String savePath) {
		this.urlLink = urlLink;
		this.savePath = savePath;
	}
	
	public m3u8(String m3u8File, String savePath) {
		this.m3u8File = m3u8File;
		this.savePath = savePath;
	}

	public String getSavePath() {
		return savePath;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	
	public String getM3u8File() {
		return m3u8File;
	}

	public void setM3u8File(String m3u8File) {
		this.m3u8File = m3u8File;
	}
	
	private String checkValue() {
		return getM3u8File()!=null?new FileTool().readFile(getM3u8File()):(getUrlLink()!=null?new HttpUtils().getPage(getUrlLink().toString()):"please set url or file path");
	}
	
	public String getLink() {
		String tmp = checkValue();
		return tmp.indexOf("http") != -1?st.getByAllString(tmp, "http(.+?\\.ts)(.+?\\S\\n)", "\\n"):st.getByAllString(tmp, "(.+?\\.ts)", "\\n");
	}
	
	public int getSize() {
		getLink();
		return st.toList().size();
	}
	
	public void merge(File filePath , File outName) {
		try {
			new FileTool().BinaryFileMerge(filePath.listFiles(), outName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void Download() {
		if(getSavePath() != null) {
			File outPath = new File(getSavePath());
			if(!outPath.exists()) {
				outPath.mkdirs();
				Download();
			}else {
				String downloadArr[] = getLink().split("\n");
				int len = downloadArr.length;
				System.err.println("file size : " +len);
				String size =len+"";
				String name = "";
				StringBuilder sb=  null;
				HttpUtils hu = new HttpUtils();
				for(int j = 0;j<len;j++) {
					name = j+"";
					if(name.length() < size.length()) {
						sb = new StringBuilder(name);
						sb.insert(sb.indexOf(""),"0");
						if(sb.length() < size.length()) {
							sb = new StringBuilder(sb.toString());
							sb.insert(sb.indexOf(""),"0");
						}
					}else {
						sb = new StringBuilder(name);
					}
					hu.threadDown(getHost()+"/"+downloadArr[j], getSavePath(), sb.toString());
				}
				System.err.println("files download ok !");
			}
		}else {
			System.err.println("please set save path !");
			System.exit(-1);
		}
	}
	
	public URL getUrlLink() {
		return urlLink;
	}

	public void setUrlLink(URL urlLink) {
		this.urlLink = urlLink;
	}
	
	public String getHost() {
		return urlLink != null?urlLink.getProtocol()+"://"+urlLink.getHost():host;
	}

	public void setHost(String host) {
		this.host = host;
	}
	
	
}
