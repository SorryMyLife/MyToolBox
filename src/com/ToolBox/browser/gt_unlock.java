package com.ToolBox.browser;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;

import com.ToolBox.util.FileTool;
import com.ToolBox.util.ImageTools;

/**
* <p>����ʱ�䣺2020��2��19�� ����2:20:07
* <p>��Ŀ���ƣ�ToolBox
* 
* <p>��˵����
*	����������⣬�ṩ��������
*<p>
*��������
*<p>���㻬����ȱ�ڼ��
* @version 1.0
* @since JDK 1.8
* �ļ����ƣ�gt_unlock.java
* */
public class gt_unlock {
	
	ImageTools it = new ImageTools();
	FileTool ft = new FileTool();
	/**<p>��ȡ������ȱ������λ��<p>image1 : ͼƬһ��·��<p>image2 : ͼƬ����·��*/
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
	/**<p>��ȡ������ȱ������λ��<p>image1 : ͼƬһ��·��<p>image2 : ͼƬ����·��*/
	public String getXY(File image1 , File image2) throws IOException {
		BufferedImage img1 = ImageIO.read(image1);
		BufferedImage img2 = ImageIO.read(image2);
		return getXY(img1, img2);
	}
	/**<p>��ȡ������ȱ������λ��<p>image1 : ͼƬһ��·��<p>image2 : ͼƬ����·��*/
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
