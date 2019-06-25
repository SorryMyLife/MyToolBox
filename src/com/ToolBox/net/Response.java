package com.ToolBox.net;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
* <p>创建时间：2019年1月27日 下午7:16:16
* <p>项目名称：ToolBox
* 
* <p>类说明：
*获取response头部内容
* @version 1.0
* @since JDK 1.8
* 文件名称：Response.java
* */
public class Response extends HttpUtils{
	
	/**<p>获取response头部内容*/
	public String getResponseHeader(String name)
	{
		return huc.getHeaderField(name);
	}
	
	/**<p>获取response所有头部内容*/
	public String getResponseHeaders()
	{
		StringBuilder fields = new StringBuilder();
		Map<String, List<String>> m = huc.getHeaderFields();
		for(Entry<String, List<String>> e : m.entrySet())
		{
			fields.append(e.getKey() + " -- " +e.getValue()+"\n");
		}
		return fields.toString();
	}
	
	
}
