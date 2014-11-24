import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class testPattenDigital {

	//((?<!(\d+))50(?!(\d+)))
	public static void main(String[] argvs) throws ParseException
	{
		String digital_reg = "((?<!(\\d+))50(?!(\\d+)))";
		Pattern digital_pattern;

		
		String input="h050ell500d0500fj50kd";
			//String inputStr="l178";
		   digital_pattern= Pattern.compile(digital_reg);
		   Matcher matcher=digital_pattern.matcher(input);
		
		
		   while( matcher.find())
		   {
			   System.out.println("find start:"+matcher.start());
			   System.out.println("find   end:"+matcher.end());
			   System.out.println("find group:"+matcher.group());			   
		   }
		   
	//((?<!([A-Za-z]+))wulin(?!([A-Za-z]+))))
		  
		  String english_reg = "((?<!([A-Za-z]+))wulin(?!([A-Za-z]+)))";
		  Pattern english_pattern;

			
		String input2="123Wulin123汉语wulin123,2wulingmen23";
				//String inputStr="l178";
		 english_pattern= Pattern.compile(english_reg,Pattern.CASE_INSENSITIVE);
		 matcher=english_pattern.matcher(input2);
			
			
			   while( matcher.find())
			   {
				   System.out.println("find start:"+matcher.start());
				   System.out.println("find   end:"+matcher.end());
				   System.out.println("find group:"+matcher.group());			   
			   }
			String str="1: 40483, 2: -6400, 14: 23, 80: 20, (81, 7, 2): 14, (90, 5): 2, (81, 7, 1): 6,[(1,2),(2,2)]";
			System.out.println("    str:"+str);
			
			String reg1="(?<=\\([^\\)]{0,100}),,(?=[^\\(]{0,100}\\))";  //查找小括号中的 ',,'
			String reg2="(?<=\\[[^\\]]{0,100}),,(?=[^\\[]{0,100}\\])";  //查找中括号中的',,'
			
			//替换所有 "," => ",,"
			String tempStr=str.replaceAll("(?<!=,),(?!=,)", ",,");              
			System.out.println("tempStr:"+tempStr);
		
			//替换小括号中的 ",,"  =>  ","
			Pattern aPattern =Pattern.compile(reg1) ;
			Matcher aMatcher=aPattern.matcher(tempStr);
			
			String temp2=tempStr.replaceAll(reg1, ",");
			System.out.println("  temp2:"+temp2);
			tempStr=aMatcher.replaceAll(",");
	
			System.out.println("tempStr:"+tempStr);
			
			/*
			//替换中括号中的 ",,"  =>  ","
			Pattern aPattern2 =Pattern.compile(reg2) ;
			Matcher aMatcher2=aPattern2.matcher(tempStr);
			tempStr=aMatcher2.replaceAll(",");
			*/
			System.out.println("tempStr:"+tempStr);
			 
			System.out.println("ori str:"+str);
				
			
		
		
	}
}
