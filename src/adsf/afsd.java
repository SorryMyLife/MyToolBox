//测试工具库用的

package adsf;

import com.ToolBox.Crawler;

public class afsd {
	
	public static void main(String[] args) throws Exception{
		//https://video2.caomin5168.com/20190709/XePZPCAu/1000kb/hls/index.m3u8
		
//		String uuu = "http://pic1.win4000.com/pic/b/b9/ab09d8943f_250_350.jpg";
//		String uuu = "http://pic1.win4000.com";
		
		Crawler hu = new Crawler();
		System.out.println(hu.toHtml("https://mm.enterdesk.com/qingchunmeinv/").getByElement("a").getAllValue("src").toString());
		System.out.println(hu.getAllImgLink("https://mm.enterdesk.com/qingchunmeinv/"));
		System.out.println("download ok !");
	}

}
//HttpUtils hu = new HttpUtils();
//hu.Download(uuu, "e:\\test\\files\\", "bbb2.jpg");
//int count = 1;
//System.out.println();
//for(String s : new m3u8(new URL("https://video2.caomin5168.com/20190709/XePZPCAu/1000kb/hls/index.m3u8")).getLink().split("\n")) {
//	hu.Download("https://video2.caomin5168.com/20190709/XePZPCAu/1000kb/hls/"+s, "E:\\test\\files\\video\\test\\" , count+"");
//	count++;
//}
//FileTool ft = new FileTool();
//File f[] = new File("E:\\test\\files\\video\\test").listFiles();
//TreeSet<Integer> list = new TreeSet<>();
//for(File fi : f) {
//	list.add(Integer.parseInt(fi.getName()));
//}
//File tmp[] = new File[list.size()];
//int c = 0;
//for(int i : list) {
//	tmp[c] = new File("E:\\test\\files\\video\\test\\"+i);
//	c++;
//}
//ft.BinaryFileMerge(tmp, new File( "e:\\test\\files\\video\\abc.mp4"));