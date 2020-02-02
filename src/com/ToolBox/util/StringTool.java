package com.ToolBox.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * ����ʱ�䣺2019��1��23�� ����6:10:35
 * <p>
 * ��Ŀ���ƣ�ToolBox
 * 
 * <p>
 * ��˵���� �ַ������߼�
 * 
 * @version 1.0
 * @since JDK 1.8 �ļ����ƣ�StringTool.java
 */
public class StringTool {
	private List<String> Matchlist = null;

	/**
	 * <p>
	 * �����ַ���������ȡ
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
	 * �����ַ���������ȡ
	 */
	public List<String> getByToList(String src, String regex, String re_str) {
		getByAllString(src, regex, re_str);
		return Matchlist;
	}

	/**
	 * <p>
	 * �����ַ���������ȡ
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
	 * ��ȡjson����
	 */
	public String getByJson(String src, String key) {
		return getByString(src, "\"" + key + "\":\"(.+?\")", "\"" + key + "\":|\"|,").replaceAll("\\s+", "");
	}

	/**
	 * <p>
	 * �����ӽ���ת��
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
	 * Unicodeת�ַ���
	 */
	public String UnicodeToString(String unicode) // UnicodeתString�õģ���Ϊ����ṷ���ַǳ����ߣ����������֡�����Ȼ�õĶ���Unicode���룬���ò�תһ�£���Ȼû����
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
	 * ��һ���ַ�����ʽ��Ϊjson��ʽ
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
			for (int i = 0; i < byteArray.length; i++) {// ��Ϊ��16���ƣ����ֻ��ռ��4λ��ת�����ֽ���Ҫ����16���Ƶ��ַ�����λ����
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
				if ((byteArray[i] & 0xff) < 0x10)// 0~Fǰ�治��
					hexString.append("0");
				hexString.append(Integer.toHexString(0xFF & byteArray[i]));
			}
		}
		return hexString.toString().toLowerCase();
	}

}
