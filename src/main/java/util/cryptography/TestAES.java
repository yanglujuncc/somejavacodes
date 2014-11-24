package util.cryptography;

import java.security.SecureRandom;
import java.security.Security;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;



public class TestAES {

	/*
	 * 对称加密
	 */

	/**
	 * 解密
	 * 
	 * @param content
	 *            加密的字符串
	 * @param key
	 *            解密用的密钥
	 * @return 解密后的字符串
	 */
	public static byte[] decrypt(byte[] encryptedContent, String key) {
		try {
			byte[] raw = key.getBytes("utf-8");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding","BC");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);

			byte[] original = cipher.doFinal(encryptedContent);

			return original;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
     * 
     */
	public static byte[] encrypt(byte[] content, String key) {
		try {
			byte[] raw = key.getBytes("utf-8");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding","BC");
		
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec);

			byte[] encrypted = cipher.doFinal(content);
			
			return encrypted;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) throws Exception {

		//Security.addProvider(new com.sun.crypto.provider.SunJCE());
		Security.addProvider(new  org.bouncycastle.jce.provider.BouncyCastleProvider());
		String KEY = "1234123412341234";

		
		String rawContent="hello world";
		byte[] rawBytes=rawContent.getBytes("utf-8");
		byte[] encryptedBytes=encrypt(rawBytes, KEY);
		byte[] decryptedBytes=decrypt(encryptedBytes, KEY);
		String rebuildContent=new String(decryptedBytes,"utf-8");
		
		System.out.println(rawContent);
		System.out.println(Arrays.toString(rawBytes));
		System.out.println(Arrays.toString(encryptedBytes));
		System.out.println(Arrays.toString(decryptedBytes));
		System.out.println(rebuildContent);
		
		String passwd="123456";
		KeyGenerator kgen = KeyGenerator.getInstance("AES");  
        kgen.init(128, new SecureRandom(passwd.getBytes()));  
        SecretKey secretKey1 = kgen.generateKey();  
        System.out.println(Arrays.toString(secretKey1.getEncoded()));
        SecretKey secretKey2 = kgen.generateKey();
        System.out.println(Arrays.toString(secretKey2.getEncoded()));
		/*[107, -76, -125, 126, -73, 67, 41, 16, 94, -28, 86, -115, -38, 125, -58, 126]
		 * System.out.println(AESWithJCE.decodeNews("ENE3efalUlCEX9yujUrE2Q=="));
		 * String raw=AESWithJCE.decodeNews("ENE3efalUlCEX9yujUrE2Q==");
		 * System.out.println(AESWithJCE.encodeNews(raw));
		 */

	
		
		// AESWithJCE.decodeNews("+/dowf7lRExt56IdbSuhxA==")
	}

}
