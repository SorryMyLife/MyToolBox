package com.ToolBox.util;

import java.util.Base64;

/**
* <p>创建时间：2019年9月2日 上午10:29:53
* <p>项目名称：ToolBox
* 
* <p>类说明：
*
* @version 1.0
* @since JDK 1.8
* 文件名称：CodeEncrypt.java
* */
public class CodeEncrypt {

	private String SrcCode;
	
	private RSAUtils rsaUtils=null;
	
	public String CodeDecrypt() {
		if(getSrcCode() != null) {
			StringBuilder sb = new StringBuilder();
			String array[] = getSrcCode().split("\n");
			int min = 33 , max=127;
			for(int i = 0;i<array.length;i++) {
				if(min >= max) {
					min=33;
				}
				String rsa[] = array[i].replaceAll("dw"+min+"wb", "---").split("---");
				String rsaData = rsa[0];
				String privateKey = rsa[1];
				String Part = rsaUtils.getDecrypt(rsaData, privateKey);
				Part = Part.substring(0,Part.length()-1).replaceAll("\\d+\\(SA|\\(", "");
				if(rsaData.hashCode() == Integer.parseInt(rsa[2].replaceAll("h", ""))) {
					sb.append(Part);
				}
				min++;
			}
			return Base64Decrypt(Base64Decrypt(sb.toString()));
		}
		return null;
	}
	
	
	private String check(String srcData , int len) {
		int l = 10 ;
		int part = len / l , head = 0;
		int min = 33 , max = 127;
		StringBuilder sb = new StringBuilder();
		for(int i = 0;i<part;i++) {
			if(l >len) {
				l = l - (l-len);
			}else {
				rsaUtils = new RSAUtils();
				String publicKey = rsaUtils.getPublicKey();
				String privateKey = rsaUtils.getPrivateKey();
				String t = srcData.substring(head, l);
				//33-126
				if(min >= max) {
					min = 33;
				}
				t = i +"(SA"+ t +(char) min;
//				System.err.println(i+" --- " +t + " -- "+t.length() + " -- tmp : " + 10 + " -- head : " + head + " -- part : " + l);
				String en = rsaUtils.getEncrypt(t, publicKey);
				sb.append(en + "dw"+min+"wb"+privateKey + "dw"+min+"wbh"+en.hashCode()+"\n");
				if(l == len) {
					break;
				}
				head = l;
				l = 10+l;
				min++;
			}
		}
		return sb.toString();
	}
	
	public String getData() {
		if(getSrcCode() != null) {
			String base64En = Base64Encrypt(Base64Encrypt(getSrcCode()));
			int len = base64En.length();
			if(len % 10 == 0) {
				return check(base64En, len);
			}else {
				StringBuilder sb1 = new StringBuilder();
				sb1.append(base64En);
				int num = 10 - (len % 10);
				for(int j = 0;j<num;j++) {
					sb1.append("(");
				}
				return check(sb1.toString(), sb1.toString().length());
			}
		}
		return null;
	}
	
	public String Base64Encrypt(String code) {
		return Base64.getEncoder().encodeToString(code.getBytes());
	}
	
	public String Base64Decrypt(String code) {
		return new String(Base64.getDecoder().decode(code));
	}
	
	public CodeEncrypt() {
		rsaUtils = new RSAUtils();
	}
	
	public CodeEncrypt(String srcCode) {
		setSrcCode(srcCode);
		rsaUtils = new RSAUtils();
	}

	public String getSrcCode() {
		return SrcCode;
	}

	public void setSrcCode(String srcCode) {
		SrcCode = srcCode;
	}
	
//	public static void main(String[] args) {
//		FileTool f = new FileTool();
//		String data = f.readFile("E:\\eclipse-works\\XieCheng\\src\\XieCheng\\Hotel_Info_Detailed.java");
//		CodeEncrypt c = new CodeEncrypt(data);
//		f.writeFile(c.getData(),"E:\\test\\ttt.txt");
//		c = new CodeEncrypt(f.readFile("E:\\test\\ttt.txt"));
//		System.out.println(c.CodeDecrypt());
//		System.out.println("ok");
//	}
//	
//	
}
