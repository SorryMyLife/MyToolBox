//测试工具库用的

package adsf;

import java.net.URL;

import com.ToolBox.video.m3u8;

public class afsd {
	public static void main(String[] args) throws Exception {
		// https://video2.caomin5168.com/20190709/XePZPCAu/1000kb/hls/index.m3u8
		// String path = "E:\\test\\files\\imgs\\005A0PMely1fwhxxgr6j2g306o06oe1m.gif";
		// File file = new File(path);
		// byte buff[] = new byte[(int)file.length()];
		// FileInputStream fils = new FileInputStream(file);
		// fils.read(buff);
		// fils.close();
		// String en = Base64.getEncoder().encodeToString(buff);
		// System.out.println(en);
		
		String size = new m3u8(new URL("https://1451398460.rsc.cdn77.org/35sp/201907/19/1563497761/1563275415.m3u8")).getSize()+"";
		System.out.println(size.length());
		
		
		System.out.println("hello");
	}

}
// HttpUtils hu = new HttpUtils();
// hu.Download(uuu, "e:\\test\\files\\", "bbb2.jpg");
// int count = 1;
// System.out.println();
// for(String s : new m3u8(new
// URL("https://video2.caomin5168.com/20190709/XePZPCAu/1000kb/hls/index.m3u8")).getLink().split("\n"))
// {
// hu.Download("https://video2.caomin5168.com/20190709/XePZPCAu/1000kb/hls/"+s,
// "E:\\test\\files\\video\\test\\" , count+"");
// count++;
// }
// FileTool ft = new FileTool();
// File f[] = new File("E:\\test\\files\\video\\test").listFiles();
// TreeSet<Integer> list = new TreeSet<>();
// for(File fi : f) {
// list.add(Integer.parseInt(fi.getName()));
// }
// File tmp[] = new File[list.size()];
// int c = 0;
// for(int i : list) {
// tmp[c] = new File("E:\\test\\files\\video\\test\\"+i);
// c++;
// }
// ft.BinaryFileMerge(tmp, new File( "e:\\test\\files\\video\\abc.mp4"));