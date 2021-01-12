package com.ToolBox.browser;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;

import com.ToolBox.util.FileTool;
import com.ToolBox.util.ImageTools;

/**
* <p>创建时间：2020年2月19日 下午2:20:07
* <p>项目名称：ToolBox
* 
* <p>类说明：
*	解决极验问题，提供基础部件
*<p>
*滑动滑块
*<p>计算滑块与缺口间距
* @version 1.0
* @since JDK 1.8
* 文件名称：gt_unlock.java
* */
public class gt_unlock {
	
	ImageTools it = new ImageTools();
	FileTool ft = new FileTool();
	/**<p>获取滑块与缺口坐标位置<p>image1 : 图片一的路径<p>image2 : 图片二的路径*/
	public String getXY(BufferedImage image1 , BufferedImage image2) {
		Integer height = image2.getHeight();
		Integer width = image2.getWidth();
		for(int x = 0;x<width;x++) {
			for(int y = 0;y<height;y++) {
				if(it.getRGB(image1, x, y)[0] - it.getRGB(image2, x, y)[0] >50) {
					return x+","+y;
				}
			}
		}
		return null;
	}
	/**<p>获取滑块与缺口坐标位置<p>image1 : 图片一的路径<p>image2 : 图片二的路径*/
	public String getXY(File image1 , File image2) throws IOException {
		BufferedImage img1 = ImageIO.read(image1);
		BufferedImage img2 = ImageIO.read(image2);
		return getXY(img1, img2);
	}
	/**<p>获取滑块与缺口坐标位置<p>image1 : 图片一的路径<p>image2 : 图片二的路径*/
	public String getXY(String image1 , String image2) throws IOException {
		File file = new File(image1);
		File file2 = new File(image2);
		if(file.exists() && file2.exists()) {
			return getXY(file, file2);
		}
		return null;
	}
	
	public boolean merge() {
		String image = "C:\\Users\\19054\\Desktop\\aaa.png";
		try {
			BufferedImage img =  ImageIO.read(new File(image));
			String html = ft.readFile("C:\\Users\\19054\\Desktop\\aaa.html");
			System.out.println(img.getHeight()+","+img.getWidth());
			System.out.println(html);
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return false;
	}
	
	public boolean move() {
		
		
		
		return false;
	}
	
	public void start() {
//		String image1 = "C:\\Users\\19054\\Desktop\\test.jpg";
//		String image2 = "C:\\Users\\19054\\Desktop\\test-1.jpg";
//		
//		try {
//			System.out.println(getXY(image1,image2));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		merge();
		
		System.out.println("hello");
	}
	
	public static void main(String[] args) {
		new gt_unlock().start();
	}
	
	
	
}
