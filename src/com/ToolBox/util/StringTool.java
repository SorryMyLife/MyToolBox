package com.ToolBox.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * 创建时间：2019年1月23日 下午6:10:35
 * <p>
 * 项目名称：ToolBox
 * 
 * <p>
 * 类说明： 字符串工具集
 * 
 * @version 1.0
 * @since JDK 1.8 文件名称：StringTool.java
 */
public class StringTool {
	private List<String> Matchlist = null;

	/**
	 * <p>
	 * 进行字符串正则提取
	 */
	public String getByString(String src, String regex, String re_str) {
		StringBuilder tmp = new StringBuilder();
		Matcher m = Pattern.compile(regex).matcher(src);
		if (m.find()) {
			tmp.append(m.group().replaceAll(re_str, "") + "\n");
		}
		return tmp.toString();
	}

	public List<String> toList() {
		return Matchlist;
	}

	/**
	 * <p>
	 * 进行字符串正则提取
	 */
	public List<String> getByToList(String src, String regex, String re_str) {
		getByAllString(src, regex, re_str);
		return Matchlist;
	}

	/**
	 * <p>
	 * 进行字符串正则提取
	 */
	public String getByAllString(String src, String regex, String re_str) {
		Matchlist = new ArrayList<String>();
		StringBuilder tmp = new StringBuilder();
		Matcher m = Pattern.compile(regex).matcher(src);
		while (m.find()) {
			tmp.append(m.group().replaceAll(re_str, "") + "\n");
			Matchlist.add(m.group().replaceAll(re_str, "") + "\n");
		}
		return tmp.toString();
	}

	public String[] getByStringArray(String src, String regex, String re_str) {
		return getByAllString(src, regex, re_str).split("\n");
	}

	/**
	 * <p>
	 * 获取json数据
	 */
	public String getByJson(String src, String key) {
		return getByString(src, "\"" + key + "\":\"(.+?\")", "\"" + key + "\":|\"|,").replaceAll("\\s+", "");
	}

	/**
	 * <p>
	 * 对链接进行转码
	 */
	public String urlencode(String src) {
		String tmp = "";
		try {
			tmp = URLEncoder.encode(src, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return tmp;
	}

	/**
	 * <p>
	 * Unicode转字符串
	 */
	public String UnicodeToString(String unicode) // Unicode转String用的，因为这个酷狗音乐非常鸡肋，歌名、歌手、简介居然用的都是Unicode编码，不得不转一下，不然没法用
	{
		String tmp = "", str = "";
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

	/**
	 * <p>
	 * 将一堆字符串格式化为json格式
	 */
	public String[] formatToJson(String src) {
		return getByAllString(src, "\\{(.+?\\}),", "").split("\n");
	}

	public byte[] toByteArray(String hexString) {
		if (hexString.isEmpty()) {
			throw new IllegalArgumentException("this hexString must not be empty");
		} else {
			final byte[] byteArray = new byte[hexString.length() / 2];
			hexString = hexString.toLowerCase();
			int k = 0;
			for (int i = 0; i < byteArray.length; i++) {// 因为是16进制，最多只会占用4位，转换成字节需要两个16进制的字符，高位在先
				byte high = (byte) (Character.digit(hexString.charAt(k), 16) & 0xff);
				byte low = (byte) (Character.digit(hexString.charAt(k + 1), 16) & 0xff);
				byteArray[i] = (byte) (high << 4 | low);
				k += 2;
			}
			return byteArray;
		}
	}

	/**
	 * 
	 * 转成十六进制字符串
	 * 
	 * byteArray : 传入的字节数组
	 * 
	 * */
	
	public String toHexString(byte[] byteArray) {
		final StringBuilder hexString = new StringBuilder();

		if (byteArray == null || byteArray.length < 1) {
			throw new IllegalArgumentException("this byteArray must not be null or empty");
		} else {
			for (int i = 0; i < byteArray.length; i++) {
				if ((byteArray[i] & 0xff) < 0x10)// 0~F前面不零
					hexString.append("0");
				hexString.append(Integer.toHexString(0xFF & byteArray[i]));
			}
		}
		return hexString.toString().toLowerCase();
	}

	/**
	 * 将网页源码里的一些特殊字符转成能看的内容
	 * srcHtml : 网页源码
	 * 
	 * */
	public String DecodeHtml(String srcHtml) {
		String code[][] = { { "&nbsp;", "&#160;", " " }, { "&lt;", "&#60;", "<" }, { "&gt;", "&#62;", ">" }, { "&amp;", "&#38;", "&" }, { "&quot;", "&#34;", "\"" },
				{ "&apos;", "&#39;", "'" }, { "&cent;", "&#162;", "￠" }, { "&pound;", "&#163;", "£" }, { "&yen;", "&#165;", "¥" }, { "&euro;", "&#8364;", "€" }, { "&sect;", "&#167;", "§" },
				{ "&copy;", "&#169;", "©" }, { "&reg;", "&#174;", "®" }, { "&trade;", "&#8482;", "™" }, { "&times;", "&#215;", "×" }, { "&divide;", "&#247;", "÷" }};

		for(int i = 0;i<code.length;i++) {
			srcHtml = srcHtml.replaceAll(code[i][0], code[i][2]).replaceAll(code[i][1], code[i][2]);	
		}
		return srcHtml;
	}
	
	/**
	 * 
	 * 将一个字符串中的第一个字符串转成大写
	 * 
	 * str : 传入需要将第一个字符串大写转换的字符串
	 * */
	
	public String toUpperCaseStr(String str) {
		char firstChar = str.toUpperCase().charAt(0);
	    String nextStr = str.toLowerCase().substring(1);
	    return firstChar + nextStr;
	}
	
	/**
	 * 将带有下划线的字符串转成驼峰字符串
	 * 
	 * str : 需要进行驼峰命名转换的字符串
	 * caseLower : 用于筛选是否第一个下划线字符串的第一个字符为小写
	 * 
	 * */
	public String toHump(String str,boolean caseLower) {
		
		if(str!=null && !str.equals(null) && !str.isEmpty()) {
			String[] split = str.split("_");
			if(split.length > 0) {
				StringBuilder sb = new StringBuilder();
				for(int i = 0;i<split.length ;i++) {
					if(!split[i].equals("") && !split[i].isEmpty() && i == 0) {
						sb.append(caseLower?split[i].toLowerCase():toUpperCaseStr(split[i].trim()));
					}else {
						if(!split[i].equals("") && !split[i].isEmpty()) {
							sb.append(toUpperCaseStr(split[i].trim()));
						}
					}				
				}
				return sb.toString().trim();
			}else {
				return toUpperCaseStr(str);
			}
		}
		return null;
	}
	
	
}
