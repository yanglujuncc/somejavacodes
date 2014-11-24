package util.cryptography;


import java.security.Security;   
  

import java.text.ParseException;

import javax.crypto.Cipher;   
import javax.crypto.spec.SecretKeySpec;   
  
import org.bouncycastle.jce.provider.BouncyCastleProvider;   
import org.bouncycastle.util.encoders.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class AESWithJCE {

	/*
	 * 对称加密
	 */
	public static String KEY = "1234567812345678"; //16位
	
	private static final Logger log = LoggerFactory.getLogger(AESWithJCE.class);
	
	public static String decodeNews(String content) {
	    return decrypt(content, KEY);
	}
	public static String encodeNews(String content) {
	    return encrypt( content, KEY);
	}
	
	static{
		Security.addProvider(new BouncyCastleProvider());
	}
	 /**
     * 解密
     *
     * @param content 加密的字符串
     * @param key     解密用的密钥
     * @return 解密后的字符串
     */
    private static String decrypt(String content, String key) {
        try {
            byte[] raw = key.getBytes();
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            
            
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding", "BC");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] encrypted = Base64.decode(content);
            try {
                byte[] original = cipher.doFinal(encrypted);
                return new String(original, "utf-8");
            } catch (Exception e) {
            	log.error(e.getMessage(),e);
            }
        } catch (Exception ex) {
        	log.error(ex.getMessage(),ex);
        }
        return null;
    }
    /*加密
     * 
     */
    private static String encrypt(String content, String key) {
        try {
            byte[] raw = key.getBytes();
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding", "BC");
          
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            
            byte[] contentBytes = content.getBytes("utf-8");
            try {
                byte[] encrypted = cipher.doFinal(contentBytes);
                byte[] encryptedBase64 =Base64.encode(encrypted);
                return new String(encryptedBase64, "utf-8");
            } catch (Exception e) {
            	log.error(e.getMessage(),e);
            }
        } catch (Exception ex) {
        	log.error(ex.getMessage(),ex);
        }
        return null;
    }
    
   
    public static void main(String[] args) throws ParseException{
        
    
      
        /*
        System.out.println(AESWithJCE.decodeNews("ENE3efalUlCEX9yujUrE2Q=="));
        String raw=AESWithJCE.decodeNews("ENE3efalUlCEX9yujUrE2Q==");             
        System.out.println(AESWithJCE.encodeNews(raw));
      	*/
        //Record:9S944MHR00162OUT,OHX3SnSNN7L/paCZvlWpUn5nzNfkfswNwO2cp+mMmoJU7uW3AyHjwJMwqs7Hwmfv,09/2Ekz8WS8o1UvumFh2HXjwStHTbMnr8pc6fFfTjog=,1400139360053
        System.out.println(AESWithJCE.decodeNews("+/dowf7lRExt56IdbSuhxA=="));
        System.out.println(AESWithJCE.decodeNews("OHX3SnSNN7L/paCZvlWpUn5nzNfkfswNwO2cp+mMmoJU7uW3AyHjwJMwqs7Hwmfv"));
        System.out.println(AESWithJCE.decodeNews("09/2Ekz8WS8o1UvumFh2HXjwStHTbMnr8pc6fFfTjog="));
        
        String raw=AESWithJCE.decodeNews("+/dowf7lRExt56IdbSuhxA==");             
        System.out.println(AESWithJCE.encodeNews(raw));
        //AESWithJCE.decodeNews("+/dowf7lRExt56IdbSuhxA==")
    }
    
}
