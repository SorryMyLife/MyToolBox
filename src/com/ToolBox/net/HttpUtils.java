package com.ToolBox.net;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import com.ToolBox.util.HtmlTool;

/**
* <p>创建时间：2019年1月23日 下午5:53:52
* <p>项目名称：ToolBox
* 
* <p>类说明：
* http工具集
* @version 1.0
* @since JDK 1.8
* 文件名称：HttpUtils.java
* */
public class HttpUtils extends HRequest{
	private boolean returnResult = true;
	
	/**<p>发送get请求*/
	public String sendGet(String url_name)
	{
		String result = getResponse(url_name);
		return isReturnResult()?result:"";
	}
	
	/**<p>发送post请求*/
	public String sendPOST(String url_name , String param)
	{
		String result = getPostResponse(url_name,param);
		return isReturnResult()?result:"";
	}
	
	/**<p>下载内容<p>String url_name , String dirPath , String fileName<p>下载链接、保存路径、保存名称*/
	public void Download(String url_name , String dirPath , String fileName)
	{
		File dir = new File(dirPath);
		if(!dir.exists())
		{
			dir.mkdirs();
			Download(url_name, dirPath, fileName);
		}else
		{
			File name = new File(dirPath+"/"+fileName);
			if(!name.exists())
			{
				try {
					byte[] buff = new byte[1];
					int len = -1;
					InputStream input = Get(url_name).getInputStream();
					FileOutputStream fos = new FileOutputStream(name);
					System.err.println(url_name+" start Downlaod ! ");
					while((len = input.read(buff)) != -1)
					{
						fos.write(buff, 0, len);
					}
					fos.close();
					input.close();
					System.err.println("Downlaod ok !  -- save to : " + name);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else
			{
				System.err.println(name + " exists !");
			}
		}
	}
	
	/**<p>判断是否要返回结果*/
	public boolean isReturnResult() {
		return returnResult;
	}
	/**<p>设置是否要返回结果*/
	public void setReturnResult(boolean returnResult) {
		this.returnResult = returnResult;
	}	
	/**<p>返回HtmlTool对象*/
	public HtmlTool toHtml(String url_name) {
		return new HtmlTool(getResponse(url_name));
	}
	/**<p>返回HtmlTool对象*/
	public HtmlTool toGzipHtml(String url_name) {
		return new HtmlTool(getGzipResponse(url_name));
	}
	/**<p>返回HtmlTool对象*/
	public HtmlTool toPostHtml(String url_name , String param) {
		return new HtmlTool(getPostResponse(url_name, param));
	}
	/**<p>返回HtmlTool对象*/
	public HtmlTool toGzipPostHtml(String url_name , String param) {
		return new HtmlTool(getGzipPostResponse(url_name, param));
	}
	
}
