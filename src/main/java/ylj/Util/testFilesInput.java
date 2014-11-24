package ylj.Util;

import java.io.IOException;

public class testFilesInput {

	public static void main(String[] args)
	{
		FilesInput aFilesInput=new FilesInput("gbk");
		try {
			aFilesInput.loadFromDirName("E:\\workspace\\SomeJavaCodes\\log");
			String aline=null;
			System.out.println(aFilesInput.remains());
			
			while((aline=aFilesInput.getLine())!=null){
				
				System.out.println(aline +"remains:"+aFilesInput.remains());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
