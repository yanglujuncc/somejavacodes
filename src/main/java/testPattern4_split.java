import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class testPattern4_split {
	public static void main(String[] argvs) throws ParseException
	{
		String splitSign_reg = "((#((\\d)|(\\w))*)|,|\\.|;|!|\\?|~| |¡¤|£¬|¡¢|¡£|£»|£¡|£¿|\t|\\+|¡¡)+";
		Pattern splitSign_pattern;

		
		String input="hellpo,dfdf,,djfkd,,,dfjkd";
			//String inputStr="l178";
		   splitSign_pattern= Pattern.compile(splitSign_reg);
		   Matcher matcher=splitSign_pattern.matcher(input);
		
		
		   while( matcher.find())
		   {
			   System.out.println("find start:"+matcher.start());
			   System.out.println("find   end:"+matcher.end());
			   System.out.println("find group:"+matcher.group());			   
		   }
		   
	
		  
		   
	
	}
}
