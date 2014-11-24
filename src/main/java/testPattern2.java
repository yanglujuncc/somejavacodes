import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class testPattern2 {
	
	public static void main(String[] argvs) throws ParseException
	{
		System.out.println("************case  *************");
		
		//    String inputPattern="hell[123]";
			String inputPattern0="hell(?=(ing))";
			String inputStr0="helling";
			//String inputStr="l178";
		   Pattern pattern_reg_adj0 = Pattern.compile(inputPattern0,Pattern.CASE_INSENSITIVE);
		   Matcher matcher_reg_adj0=pattern_reg_adj0.matcher(inputStr0);
		
		   if(matcher_reg_adj0.matches())
		   {
			   System.out.println("match:"+inputStr0);
		   }
		   else
			   System.out.println("not match:"+inputStr0);
		   while(matcher_reg_adj0.find())
		   {
			   System.out.println("find start:"+matcher_reg_adj0.start());
			   System.out.println("find   end:"+matcher_reg_adj0.end());
			   System.out.println("find group:"+matcher_reg_adj0.group());			   
		   }
		   
		   String inputPattern="";
		//	String inputPattern="hell(?=[123])";
			String inputStr="hell512hell2hell6hell378";
			//String inputStr="l178";
		   Pattern pattern_reg_adj = Pattern.compile(inputPattern,Pattern.CASE_INSENSITIVE);
		   Matcher matcher_reg_adj=pattern_reg_adj.matcher(inputStr);
		   System.out.println("************* find ************************");

		   while(matcher_reg_adj.find())
		   {
		
			   System.out.println("find start:"+matcher_reg_adj.start());
			   System.out.println("find   end:"+matcher_reg_adj.end());
			   System.out.println("find group:"+matcher_reg_adj.group());	
			   System.out.println("groupCount="+matcher_reg_adj.groupCount());
			
		   }
		  
		   
	
	}
}
