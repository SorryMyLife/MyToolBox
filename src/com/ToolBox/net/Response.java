package com.ToolBox.net;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
* <p>����ʱ�䣺2019��1��27�� ����7:16:16
* <p>��Ŀ���ƣ�ToolBox
* 
* <p>��˵����
*��ȡresponseͷ������
* @version 1.0
* @since JDK 1.8
* �ļ����ƣ�Response.java
* */
public class Response extends HttpUtils{
	
	/**<p>��ȡresponseͷ������*/
	public String getResponseHeader(String name)
	{
		return huc.getHeaderField(name);
	}
	
	/**<p>��ȡresponse����ͷ������*/
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
