package com.ToolBox.video;

import com.ToolBox.util.StringTool;

/**
* <p>����ʱ�䣺2019��3��14�� ����10:40:26
* <p>��Ŀ���ƣ�ToolBox
* 
* <p>��˵����
*
* @version 1.0
* @since JDK 1.8
* �ļ����ƣ�m3u8.java
* */
public class m3u8 extends StringTool{
	
	private String m3u8File;
	
	public m3u8(String m3u8File) {
		this.m3u8File = m3u8File;
	}
	
	public String getM3u8File() {
		return m3u8File;
	}
	
	public String getVideoDownloadLink()
	{
		return getByAllString(getM3u8File(), "/(.+?.(ts|f4v|mp4))", "");
	}
	
	public void printLink()
	{
		System.out.println(getVideoDownloadLink());
	}
	
	
}
