package com.ToolBox.net;

import java.util.ArrayList;
import java.util.List;

import com.ToolBox.util.HtmlTool;
import com.ToolBox.util.StringTool;

/**
 * <p>
 * 创建时间：2019年1月23日 下午5:53:12
 * <p>
 * 项目名称：ToolBox
 * 
 * <p>
 * 类说明： 爬虫工具类
 * 
 * @version 1.0
 * @since JDK 1.8 文件名称：CrawlerUtils.java
 */
public class CrawlerUtils extends HttpUtils {

	public void printAllLink(String url_name) {
		System.out.println(getAllLink(url_name));
	}

	public void printAllHtmlLink(String url_name) {
		System.out.println(getAllHtmlLink(url_name));
	}

	public void printAllImgLink(String url_name) {
		System.out.println(getAllImgLink(url_name));
	}

	public String getAllLink(String url_name) {
		return new HtmlTool(getResponse(url_name)).getByElementValueAll("href").toString();
	}

	public String getAllHtmlLink(String url_name) {
		return new StringTool().getByAllString(new HtmlTool(getResponse(url_name)).getByElement("a").toString(),
				"\"(.+?.(shtml|html|htm))", "\"");
	}

	public String getAllImgLink(String url_name) {
		return new HtmlTool(getResponse(url_name)).getByElement("a").getAllValue("src").toString();
	}

	private List<String> LinkToList(String data) {
		List<String> list = new ArrayList<>();
		for (String s : data.split("\n")) {
			list.add(s);
		}
		return list;
	}

	public List<String> getAllLinkToList(String url_name) {
		return LinkToList(getAllLink(url_name));
	}

	public List<String> getAllHtmlLinkToList(String url_name) {
		return LinkToList(getAllHtmlLink(url_name));
	}

	public List<String> getAllImgLinkToList(String url_name) {
		return LinkToList(getAllImgLink(url_name));
	}

}
