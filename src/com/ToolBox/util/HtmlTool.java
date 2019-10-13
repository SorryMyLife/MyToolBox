package com.ToolBox.util;
/**
* <p>����ʱ�䣺2019��1��24�� ����10:05:50
* <p>��Ŀ���ƣ�ToolBox
* 
* <p>��˵����
* html���߼�
* @version 1.0
* @since JDK 1.8
* �ļ����ƣ�HtmlTool.java
* */

public class HtmlTool {
	
	private String html = "";
	
	private StringTool st;
	
	/**<p>HTML����*/
	public HtmlTool(String html) {
		st = new StringTool();
		this.html = html;
	}
	
	/**<p>��ȡHTMLԪ��*/
	public Element getByElement(String name)
	{
		return new Element(st.getByAllString(html, "<"+name+"(.+?</"+name+">)", ""),name);
	}
	
	/**<p>��ȡHTMLԪ�����е�ֵ*/
	public HtmlTool getByElementValueAll(String name)
	{
		return new HtmlTool(st.getByAllString(html, name+"=\"(.+?\")", name+"=|\""));
	}
	
	/**<p>��ȡHTMLԪ�ص�ֵ*/
	public HtmlTool getByElementValue(String name)
	{
		return new HtmlTool(st.getByString(html, name+"=\"(.+?\")", name+"=|\""));
	}
	
	/**<p>���ػ�ȡ��������*/
	public String toString() {
		return html;
	}
}
