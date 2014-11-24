
public class testString {
	public static void main(String[] args) throws InterruptedException
	{
		String str="12345";
		
		System.out.println(str.length());
		System.out.println(str.substring(0, 5));
		System.out.println(str.substring(1));
		long begin=System.nanoTime();
		Thread.sleep(1000);
		System.out.println((System.nanoTime()-begin)/1000+"us");
		
		String str3="2012-02-14T00:00:27+08:00 hz163-36 gas_124[27838]: [85481]INFO|CHAT|IMMESSAGE SenderId:1495650124 RecverId:1295760124 IP:182.242.73.37 Message:我们少个甲";
		System.out.println(str3.substring(147, 148));
	}
}
