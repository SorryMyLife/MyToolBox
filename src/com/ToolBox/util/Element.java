package com.ToolBox.util;
/**
* <p>创建时间：2019年1月24日 上午10:29:25
* <p>项目名称：ToolBox
* 
* <p>类说明：
* HTML元素工具集
* @version 1.0
* @since JDK 1.8
* 文件名称：Element.java
* */
public class Element {
	
	private String element , elementName;
	
	private StringTool st;
	
	/**<p>HTML元素函数*/
	public Element(String html , String name)
	{
		st = new StringTool();
		this.element =  html;
		this.elementName = name;
	}
	
	/**<p>返回HtmlTool对象*/
	public HtmlTool toHtmlTool() {
		return new HtmlTool(element);
	}
	
	/**<p>获取元素的值*/
	public HtmlTool getValue(String name)
	{
		return new HtmlTool(st.getByString(element, name+"=(\"|')(.+?(\"|'))", name+"=|\"|'"));
	}
	
	/**<p>获取元素所有的值*/
	public HtmlTool getAllValue(String name)
	{
		return new HtmlTool(st.getByAllString(element, name+"=(\"|')(.+?(\"|'))", name+"=|\"|'"));
	}
	
	/**<p>返回结果*/
	public String toString()
	{
		return element;
	}
	
	/**<p>获取纯文本*/
	public HtmlTool getText() {
		return new HtmlTool(st.getByString(element, ">(.+?</"+elementName+")",">|</"+elementName));
	}
	
	/**<p>获取所有纯文本*/
	public HtmlTool getAllText() {
		return new HtmlTool(st.getByAllString(element, ">(.+?</"+elementName+")",">|</"+elementName));
	}
	
	/**<p>获取标签值*/
	public HtmlTool getTitle() {
		return getValue("title");
	}
	
	/**<p>获取所有标签值*/
	public HtmlTool getAllTitle() {
		return getAllValue("title");
	}
	
	/**<p>获取超链接*/
	public HtmlTool getHref() {
		return getValue("href");
	}
	
	/**<p>获取所有超链接*/
	public HtmlTool getAllHref() {
		return getAllValue("href");
	}
	
}
