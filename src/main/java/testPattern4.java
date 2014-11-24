import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class testPattern4 {
	public static void main(String[] args)
	{
		String input="\\xab\\xcd\\xbc\\x";
		
		Pattern p = Pattern.compile("\\\\x(?=[a-z])");
		Matcher aMatcher =p.matcher(input);
		while(aMatcher.find()){
			
			System.out.println(aMatcher.group());
			
		}
		
		String result=aMatcher.replaceAll("%");
		System.out.println(result);
	}
}
