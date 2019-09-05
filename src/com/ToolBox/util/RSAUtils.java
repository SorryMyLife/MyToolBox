package com.ToolBox.util;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

/**
 * <p>
 * ����ʱ�䣺2019��9��1�� ����1:56:38
 * <p>
 * ��Ŀ���ƣ�ToolBox
 * 
 * <p>
 * ��˵���� rsa�ӽ��ܹ��ߣ�����jdk�Դ��㷨
 *
 * @version 1.0
 * @since JDK 1.8 �ļ����ƣ�RSAUtils.java
 */
public class RSAUtils {
	private KeyPair keyPair = null;
	private StringTool st = null;

	public RSAUtils(int length) {
		keyPair = getKeyPair(length);
		st = new StringTool();
	}

	public RSAUtils() {
		keyPair = getKeyPair(1024);
		st = new StringTool();
	}

	// ������Կ��
	public static KeyPair getKeyPair(int length) {
		try {
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
			keyPairGenerator.initialize(length);
			return keyPairGenerator.generateKeyPair();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static byte[] DataEncrypt(byte data[], byte publickey[]) {
		try {
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE,
					KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(publickey)));
			return cipher.doFinal(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static byte[] DataDecrypt(byte encryptData[], byte privteKey[]) {
		try {
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE,
					KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(privteKey)));
			return cipher.doFinal(encryptData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getPublicKey() {
		return keyPair != null ? st.toHexString(keyPair.getPublic().getEncoded()) : null;
	}

	public String getPrivateKey() {
		return keyPair != null ? st.toHexString(keyPair.getPrivate().getEncoded()) : null;
	}

	public String getEncrypt(String srcData , String publicKey) {
		return st.toHexString(DataEncrypt(srcData.getBytes(),st.toByteArray(publicKey)));
	}

	public String getDecrypt(String encryptData ,String privateKey) {
		return new String(DataDecrypt(st.toByteArray(encryptData), st.toByteArray(privateKey)));
	}
}
