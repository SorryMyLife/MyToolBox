package com.ToolBox.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
* <p>����ʱ�䣺2020��2��19�� ����3:35:30
* <p>��Ŀ���ƣ�ToolBox
* 
* <p>��˵����
* ͼƬ��������
* @version 1.0
* @since JDK 1.8
* �ļ����ƣ�ImageTools.java
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
