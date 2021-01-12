package com.ToolBox.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
* <p>创建时间：2020年2月19日 下午3:35:30
* <p>项目名称：ToolBox
* 
* <p>类说明：
* 图片处理功能类
* @version 1.0
* @since JDK 1.8
* 文件名称：ImageTools.java
* */
public class ImageTools {
	
	public int[] getRGB(BufferedImage img,int i , int y) {
		int rgb[] = new int[3];
		rgb[0] = (img.getRGB(i, y) & 0xff0000 ) >> 16;
		rgb[1] = (img.getRGB(i, y) & 0xff00 ) >> 8;
		rgb[2] = (img.getRGB(i, y) & 0xff );
		return rgb;
	}
	
	public int[][] getPixel(BufferedImage img) throws IOException {
		int rgb[][] = new int[img.getWidth()*img.getHeight()][3];
		int count = 0;
		for(int i = 0;i<img.getWidth();i++) {
			for(int y = 0;y<img.getHeight();y++) {
				rgb[count]=getRGB(img, i, y);
				count++;
			}
		}
		return rgb;
	}
	
	public int[][] getPixel(File imageFile) throws IOException {
		return getPixel(ImageIO.read(imageFile));
	}
	
	public int[][] getPixel(String imagePath) throws IOException {
		File imageFile = new File(imagePath);
		if(imageFile.exists()) {
			return getPixel(imageFile);
		}
		return null;
	}
	
}
