package com.ToolBox.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
* <p>创建时间：2019年1月23日 下午6:10:35
* <p>项目名称：ToolBox
* 
* <p>类说明：
* 字符串工具集
* @version 1.0
* @since JDK 1.8
* 文件名称：StringTool.java
* */
public class StringTool {
	
	/**<p>进行字符串正则提取*/
	public String getByString(String src , String regex , String re_str)
	{
		StringBuilder tmp = new StringBuilder();
		Matcher m = Pattern.compile(regex).matcher(src);
		if(m.find())
		{
			tmp.append(m.group().replaceAll(re_str, "") +"\n");
		}
		return tmp.toString();
	}
	
	/**<p>进行字符串正则提取*/
	public String getByAllString(String src , String regex , String re_str)
	{
		StringBuilder tmp = new StringBuilder();
		Matcher m = Pattern.compile(regex).matcher(src);
		while(m.find())
		{
			tmp.append(m.group().replaceAll(re_str, "") + "\n" );
		}
		return tmp.toString();
	}
	
	public String[] getByStringArray(String src , String regex , String re_str) {
		return getByAllString(src, regex, re_str).split("\n");
	}
	
	/**<p>获取json数据*/
	public String getByJson(String src , String name)
	{
		return getByString(src, "\""+name+"\":\"(.+?\")", "\""+name+"\":|\"|,").replaceAll("\\s+", "");
	}
	
	/**<p>对链接进行转码*/
	public String urlencode(String src)
	{
		String tmp = "";
		try {
			tmp  = URLEncoder.encode(src, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return tmp;
	}
	
	/**<p>Unicode转字符串*/
	public String UnicodeToString(String unicode) // Unicode转String用的，因为这个酷狗音乐非常鸡肋，歌名、歌手、简介居然用的都是Unicode编码，不得不转一下，不然没法用
	{
		String tmp = "" , str = "";
		Matcher m = Pattern.compile("(\\\\u(\\p{XDigit}{4}))").matcher(unicode);
		char ch;
		while (m.find()) {
			tmp = m.group(2);
			ch = (char) Integer.parseInt(tmp, 16);
			str = m.group(1);
			unicode = unicode.replace(str, ch + "");
		}

		return unicode;
	}
	
	/**<p>将一堆字符串格式化为json格式*/
	public String[] formatToJson(String src)
	{
		return getByAllString(src, "\\{(.+?\\}),", "").split("\n");
	}
	
}
