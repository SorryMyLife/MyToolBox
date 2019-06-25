package com.ToolBox.util;
/**
* <p>����ʱ�䣺2019��1��24�� ����10:29:25
* <p>��Ŀ���ƣ�ToolBox
* 
* <p>��˵����
* HTMLԪ�ع��߼�
* @version 1.0
* @since JDK 1.8
* �ļ����ƣ�Element.java
* */
public class Element {
	
	private String element , elementName;
	
	private StringTool st;
	
	/**<p>HTMLԪ�غ���*/
	public Element(String html , String name)
	{
		st = new StringTool();
		this.element =  html;
		this.elementName = name;
	}
	
	/**<p>����HtmlTool����*/
	public HtmlTool toHtmlTool() {
		return new HtmlTool(element);
	}
	
	/**<p>��ȡԪ�ص�ֵ*/
	public HtmlTool getValue(String name)
	{
		return new HtmlTool(st.getByString(element, name+"=(\"|')(.+?(\"|'))", name+"=|\"|'"));
	}
	
	/**<p>��ȡԪ�����е�ֵ*/
	public HtmlTool getAllValue(String name)
	{
		return new HtmlTool(st.getByAllString(element, name+"=(\"|')(.+?(\"|'))", name+"=|\"|'"));
	}
	
	/**<p>���ؽ��*/
	public String toString()
	{
		return element;
	}
	
	/**<p>��ȡ���ı�*/
	public HtmlTool getText() {
		return new HtmlTool(st.getByString(element, ">(.+?</"+elementName+")",">|</"+elementName));
	}
	
	/**<p>��ȡ���д��ı�*/
	public HtmlTool getAllText() {
		return new HtmlTool(st.getByAllString(element, ">(.+?</"+elementName+")",">|</"+elementName));
	}
	
	/**<p>��ȡ��ǩֵ*/
	public HtmlTool getTitle() {
		return getValue("title");
	}
	
	/**<p>��ȡ���б�ǩֵ*/
	public HtmlTool getAllTitle() {
		return getAllValue("title");
	}
	
	/**<p>��ȡ������*/
	public HtmlTool getHref() {
		return getValue("href");
	}
	
	/**<p>��ȡ���г�����*/
	public HtmlTool getAllHref() {
		return getAllValue("href");
	}
	
}
