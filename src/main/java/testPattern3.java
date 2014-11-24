import java.util.regex.Matcher;
import java.util.regex.Pattern;

//提取汉字
public class testPattern3 {


	public static void main(String[] args)
	{
		String input="123abc这个	中文cde123abc也要提取123ab";
		Pattern p = Pattern.compile("([\u4e00-\u9fa5]+)");
		Matcher aMatcher =p.matcher(input);
		
		while(aMatcher.find())
		{
			System.out.println(input.substring(aMatcher.start(),aMatcher.end()));
			
		}
		
		//匹配单词的开始或结束
		System.out.println("***************** 2 *********************");
		Pattern p2 = Pattern.compile("\\b");
		Matcher aMatcher2 =p2.matcher(input);
		while(aMatcher2.find())
		{
			//System.out.println(input.substring(aMatcher2.start(),aMatcher2.end()));
			System.out.println("start="+aMatcher2.start()+"  end="+aMatcher2.end());
			System.out.println(input.substring(aMatcher2.end()));
		}
		
		//匹配单词的开始或结束
		System.out.println("***************** 3 ***********************");
		Pattern p3 = Pattern.compile("\\w*");
		Matcher aMatcher3 =p3.matcher(input);
		System.out.println(input);
		while(aMatcher3.find())
		{
			//System.out.println(input.substring(aMatcher2.start(),aMatcher2.end()));
			System.out.println("start="+aMatcher3.start()+"  end="+aMatcher3.end());
			System.out.println(input.substring(aMatcher3.end()));
		}
	}
	
}
