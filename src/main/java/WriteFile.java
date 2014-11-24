import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;


public class WriteFile {
	public static void main(String[] args)
	{
		try {
		File f=new File("afile");
		
		if(!f.exists())
			f.createNewFile();
		//FileOutputStream ¶ÁÐ´µ¥Î»  =¡· ×Ö½Ú
	
		
	
		FileOutputStream fos= new FileOutputStream(f);
		
		OutputStreamWriter osw=new OutputStreamWriter(fos,"gbk");
		
		BufferedWriter bw=new BufferedWriter(osw);
		
		bw.append("hello");
		bw.flush();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
