import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;


public class ReadFile {
	public  static void main(String[] argvs)
	{
		File f=new File("fileName");
		
		
		
		//FileInputStream 读写单位  =》 字节
		FileInputStream fis=null;
		
		
		try {
			fis=new FileInputStream(f);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//InputStreamReader 读写单位  =》java char 字符
		InputStreamReader isr=null;
		
		
		try {
			isr = new InputStreamReader(fis,"gbk");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//BufferedReader  读写单位 字符&行
		BufferedReader br= new BufferedReader(isr);
		
		try {
			br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
}
