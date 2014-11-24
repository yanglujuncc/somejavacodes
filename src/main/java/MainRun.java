
public class MainRun {
	public static void main(String[]agvs)
	{
		String str="943	1330	3	888 692465";
		
				
		System.out.println("hh");
		String[] tokens=str.split("[\t ]");
		
		if(1.0>Double.NaN)
			System.out.println("1.0>Double.NaN");
		else
			System.out.println("1.0<=Double.NaN");
		
		if(1.0<Double.NaN)
			System.out.println("1.0<Double.NaN");
		else
			System.out.println("1.0>=Double.NaN");
		
	
		
		if(Double.NaN==Double.NaN)
			System.out.println("Double.NaN==Double.NaN");
		else
			System.out.println("Double.NaN!=Double.NaN");
		
		System.out.println(Math.abs(Double.NaN));
		for(String token:tokens)
		{
			System.out.print(token+"#");
		}
		System.out.println();
		
	}
}
