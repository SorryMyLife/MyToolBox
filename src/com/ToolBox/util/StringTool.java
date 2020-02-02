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

	public static byte[] toByteArray(String hexString) {
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

	public static String toHexString(byte[] byteArray) {
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

}
