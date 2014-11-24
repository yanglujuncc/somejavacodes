import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class helpDaNiu {

	public static void  case1(){
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
		tempStr=aMatcher.replaceAll(",");

		System.out.println("tempStr:"+tempStr);
		
		//替换中括号中的 ",,"  =>  ","
		Pattern aPattern2 =Pattern.compile(reg2) ;
		Matcher aMatcher2=aPattern2.matcher(tempStr);
		tempStr=aMatcher2.replaceAll(",");
		
		System.out.println("tempStr:"+tempStr);
		 
		System.out.println("ori str:"+str);
			
	}
	public static void  case2(){
		String str="0: 22L, 1: 22023338L, 2: 22023169, 3: 'laozhou8888@gmail.com', 4: 1, 5: 60, 6: 324132L, 7: 6, 8: 58, 9: 347, 10: 239, 11: '124.227.230.42', 12: '1]\\xac', 13: 0, 15: 0, 16: '124.227.230.42', 17: 6271, 18: 1359, 19: '\\xc0\\xcf\\xb2\\xbb\\xcb\\xc0', 20: '\\xd0\\xfe\\xb1\\xf9', 21: '00:30:18:AE:C0:56', 31: 14, 32: 15, 33: [57, 29], 34: 100, 35: [], 36: 90234, 37: 22000002L, 38: 1350544258, 39: 233898L, 40: 233899L, 41: [8, 7, 8, 7, 7, 9, 7, 7], 42: [4262L, 4L, 48L, 1, 3], 43: [1, 4], 44: 3, 45: 0";
		System.out.println("    str:"+str);
		
		String testCase="'xbe,,'";
		String reg2="(?<=\\[[^\\]]{0,100}),,(?=[^\\[]{0,100}\\])";  //查找中括号中的',,'
		String reg3="(?<=\\'[^\\']{0,100}),,(?=[^\\']{0,100}\\')";  //查找冒号中的',,'
		//替换所有 "," => ",,"
		String tempStr=str.replaceAll("(?<!=,),(?!=,)", ",,");              
		System.out.println("tempStr:"+tempStr);
	
		
		//替换中括号中的 ",,"  =>  ","
		Pattern aPattern2 =Pattern.compile(reg2) ;
		Matcher aMatcher2=aPattern2.matcher(tempStr);
		tempStr=aMatcher2.replaceAll(",");
		
		
		System.out.println("tempStr:"+tempStr);
		 
		System.out.println("ori str:"+str);
			
	}
	public static void  simpleCase2(){
		String str="1: 22023338L, 2: 22023169, 3: 'laozhou8888@gmail.com', 4: 1, 5: 60, 6: 324132L, 7: 6, 8: 58, 9: 347, 10: 239, 11: '124.227.230.42', 12: 'x\\x12\\n?<\\xcb@\\xee\\x82\\x80\\xbe,\\\\x01\\xa3/', 13: 0, 15: 0, 16: '124.227.230.42', 17: 6271, 18: 1359, 19: '\\xc0\\xcf\\xb2\\xbb\\xcb\\xc0', 20: '\\xd0\\xfe\\xb1\\xf9', 21: '00:30:18:AE:C0:56', 31: 14, 32: 15, 33: [57, 29], 34: 100, 35: [], 36: 90234, 37: 22000002L, 38: 1350544258, 39: 233898L, 40: 233899L, 41: [8, 7, 8, 7, 7, 9, 7, 7], 42: [4262L, 4L, 48L, 1, 3], 43: [1, 4], 44: 3, 45: 0";
		String str2="0: 22L, 1: 22023338L, 2: 22023169, 3: 'laozhou8888@gmail.com', 4: 1, 5: 60, 6: 324132L, 7: 6, 8: 58, 9: 347, 10: 239, 11: '124.227.230.42', 12: '1]\\xac', 13: 0, 15: 0, 16: '124.227.230.42', 17: 6271, 18: 1359, 19: '\\xc0\\xcf\\xb2\\xbb\\xcb,\\xc0', 20: '\\xd0\\xfe\\xb1\\xf9', 21: '00:30:18:AE:C0:56', 31: 14, 32: 15, 33: [57, 29], 34: 100, 35: [], 36: 90234, 37: 22000002L, 38: 1350544258, 39: 233898L, 40: 233899L, 41: [8, 7, 8, 7, 7, 9, 7, 7], 42: [4262L, 4L, 48L, 1, 3], 43: [1, 4], 44: 3, 45: 0";
		//替换所有 "," => ",,"
		String input=str2;
		
		String tempStr=str2.replaceAll("(?<!=,),(?!=,)", ",,");   
		System.out.println("  tempStr:"+tempStr);
		//替换中括号中的 ",,"  =>  ","
		//String reg2="(?<=\\[[^\\]]{0,100}),,(?=[^\\[]{0,100}\\])";  //查找中括号中的',,'
		//tempStr=tempStr.replaceAll(reg2,",");
		//System.out.println("  tempStr:"+tempStr);
		
		String reg3="(?<=\\'[^\\']{0,100}),,(?=[^\\']{0,100}\\')";  //查找冒号中的',,'
		tempStr=tempStr.replaceAll(reg3, ",");
		System.out.println("  tempStr:"+tempStr);
		 
		System.out.println("  ori str:"+input);
		System.out.println("rsult Str:"+tempStr);
		
		 
	}
	public static void main(String[] args){
		 simpleCase2();
	}
}
