
public class testQuanBanJiaoZiFu {
	public static void main(String[] args)
	{
		/*
		全角---指一个字符占用两个标准字符位置。 
		汉字字符和规定了全角的英文字符及国标GB2312-80中的图形符号和特殊字符都是全角字符。一般的系统命令是不用全角字符的，只是在作文字处理时才会使用全角字符。 
		半角---指一字符占用一个标准的字符位置。 
		通常的英文字母、数字键、符号键都是半角的，半角的显示内码都是一个字节。在系统内部，以上三种字符是作为基本代码处理的，所以用户输入命令和参数时一般都使用半角.全角占两个字节，半角占一个字节。 
		半角全角主要是针对标点符号来说的，全角标点占两个字节，半角占一个字节，而不管是半角还是全角，汉字都还是要占两个字节 
		在编程序的源代码中只能使用半角标点（不包括字符串内部的数据） 
		在不支持汉字等语言的计算机上只能使用半角标点. 
		例如：在半角状态下打的句号只是一个圆点，而在全角下就是标准的句号。
		
		*/
		String halfChar=",. ";
		String halfZhongChar="，。 ";  //半角中文状态下输入的逗号和句号是全角符号，空格是半角符号
		String quanZhongChar="，。　";//半角中文状态下输入的逗号和句号，空格是全角符号，
		
		//注 英文无顿号
		for(int i=0;i<halfChar.length();i++)
		{
			int i_1=(int)halfChar.charAt(i);
			int i_2=(int)halfZhongChar.charAt(i);
			int i_3=(int)quanZhongChar.charAt(i);
			System.out.println(i_1+"|"+i_2+"|"+i_3);
			
		}
	}

}
