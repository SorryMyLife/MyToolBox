//测试工具库用的

package adsf;

import com.ToolBox.generate.CrudGenerate;
import com.ToolBox.generate.jdbcGenerate;

public class afsd {
	
	public static void test() {
		String USER = "root";
		String PASS = "123";
		String projectPath = System.getProperty("user.dir");
		System.out.println(projectPath);
		new jdbcGenerate(USER, PASS, "studentmange").getFileds().forEach((m,f)->{
			CrudGenerate c=  new CrudGenerate("com.ToolBox", projectPath+"\\src", m, f);
			c.jdbcServiceImplGenerate(false);
//			System.out.println(c.toText());
			System.out.println(c.getSavefilepath());
			
		});

		
		System.out.println("Goodbye!");
	}

	public static void main(String[] args) throws Exception {
		test();

		// String max_mo = "900";
		// String host = "http://www.tiboo.cn";
		// String hd[] = {
		// "Accept:
		// text/html,application/xhtml,xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
		//
		// "Accept-Language: zh-CN,zh;q=0.9", "Cache-Control: no-cache", "Connection:
		// keep-alive",
		//
		// "Host: www.tiboo.cn",
		//
		// "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36
		// (KHTML, like Gecko) Chrome/86.0.4240.198 Safari/537.36" };
		// StringTool st = new StringTool();
		// HttpUtils hu = new HttpUtils();
		// hu.setHeaders(hd);
		// hu.toHtml(host+"/chuzu/1_0_1_10_1_0_0_" + max_mo +
		// "_0_0_0_0_1/?selpic=5").getByElements("tr").stream()
		// .forEach((a) -> {
		//// System.out.println(":::: "+a.toString());
		// String m = st.getByString(a.toString(), "<b>\\d+</b>", "b>|<|/");
		// Integer mm = 0;
		// if (!m.isEmpty()) {
		// mm = Integer.valueOf(m.replaceAll("\\s+", ""));
		// }
		//
		// if (mm != 0 && mm < Integer.valueOf(max_mo.replaceAll("\\s+", ""))) {
		// System.out.println(a.getAllText() + " -- " + mm +" -- " + host+a.getHref());
		// System.out.println(" ----------------------------------------------- ");
		// }
		// });

	}

}
