import java.io.UnsupportedEncodingException;
import java.util.Arrays;


public class testJavaChar {

	public static void main(String[] args) throws UnsupportedEncodingException
	{
		char x = 'ÎÒ'; 
		String str = "ºº×Ö"; 
	//	byte[] bytes1 = x.getBytes();
		String encoding_utf_8="utf-8";
		String encoding_gbk="gbk";
		String encoding_gb18030="gb18030";
		
		byte[] bytes = str.getBytes(encoding_utf_8);
		System.out.println("encoding="+encoding_utf_8+":"+Arrays.toString(bytes));
		
		 bytes = str.getBytes(encoding_gbk);
		System.out.println("encoding="+encoding_gbk+":"+Arrays.toString(bytes));
		
		 bytes = str.getBytes(encoding_gb18030);
		System.out.println("encoding="+encoding_gb18030+":"+Arrays.toString(bytes));
		
		
	}
}
