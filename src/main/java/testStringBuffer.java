
public class testStringBuffer {
	public static void main(String[] args) throws InterruptedException
	{
		StringBuffer str=new StringBuffer("12345");
		
		System.out.println(str.length());
		System.out.println(str.substring(0, 5));
		System.out.println(str.substring(1));
		
		str.delete(0, 1);
		System.out.println(str);
		String str3="2012-02-14T00:00:27+08:00 hz163-36 gas_124[27838]: [85481]INFO|CHAT|IMMESSAGE SenderId:1495650124 RecverId:1295760124 IP:182.242.73.37 Message:我们少个甲";
		System.out.println(str3.substring(147, 148));
		
		
		StringBuffer stringBuffer=new StringBuffer("[17319-1335402509-16796053:大双皮奶]来强力甲+++++#134#r #efff ");
		stringBuffer.delete(0, 32);
		stringBuffer.insert(0, 'x');
		System.out.println(stringBuffer);
	}
}
