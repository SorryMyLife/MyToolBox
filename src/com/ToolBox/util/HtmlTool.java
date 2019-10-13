package com.ToolBox.util;
/**
* <p>创建时间：2019年1月24日 上午10:05:50
* <p>项目名称：ToolBox
* 
* <p>类说明：
* html工具集
* @version 1.0
* @since JDK 1.8
* 文件名称：HtmlTool.java
* */

public class HtmlTool {
	
	private String html = "";
	
	private StringTool st;
	
	/**<p>HTML工具*/
	public HtmlTool(String html) {
		st = new StringTool();
		this.html = html;
	}
	
	/**<p>获取HTML元素*/
	public Element getByElement(String name)
	{
		return new Element(st.getByAllString(html, "<"+name+"(.+?</"+name+">)", ""),name);
	}
	
	/**<p>获取HTML元素所有的值*/
	public HtmlTool getByElementValueAll(String name)
	{
		return new HtmlTool(st.getByAllString(html, name+"=\"(.+?\")", name+"=|\""));
	}
	
	/**<p>获取HTML元素的值*/
	public HtmlTool getByElementValue(String name)
	{
		return new HtmlTool(st.getByString(html, name+"=\"(.+?\")", name+"=|\""));
	}
	
	/**<p>返回获取到的内容*/
	public String toString() {
		return html;
	}
}
