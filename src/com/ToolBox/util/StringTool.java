package com.ToolBox.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
* <p>����ʱ�䣺2019��1��23�� ����6:10:35
* <p>��Ŀ���ƣ�ToolBox
* 
* <p>��˵����
* �ַ������߼�
* @version 1.0
* @since JDK 1.8
* �ļ����ƣ�StringTool.java
* */
public class StringTool {
	
	/**<p>�����ַ���������ȡ*/
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
	
	/**<p>�����ַ���������ȡ*/
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
	
	/**<p>��ȡjson����*/
	public String getByJson(String src , String name)
	{
		return getByString(src, "\""+name+"\":\"(.+?\")", "\""+name+"\":|\"|,").replaceAll("\\s+", "");
	}
	
	/**<p>�����ӽ���ת��*/
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
	
	/**<p>Unicodeת�ַ���*/
	public String UnicodeToString(String unicode) // UnicodeתString�õģ���Ϊ����ṷ���ַǳ����ߣ����������֡�����Ȼ�õĶ���Unicode���룬���ò�תһ�£���Ȼû����
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
	
	/**<p>��һ���ַ�����ʽ��Ϊjson��ʽ*/
	public String[] formatToJson(String src)
	{
		return getByAllString(src, "\\{(.+?\\}),", "").split("\n");
	}
	
}
