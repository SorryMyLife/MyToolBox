//测试工具库用的

package adsf;

import com.ToolBox.net.SRequest;

public class afsd {
	
	public static void main(String[] args) throws Exception{
		String target = "https://blog.csdn.net/sh191461679/article/details/46933757";
		System.out.println(new SRequest().toHtml(target).getByElement("a").getAllTitle().toString());
	}

}
