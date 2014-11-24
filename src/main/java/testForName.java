
public class testForName {
		
	static {
		System.out.println(" i am int static statement !  from testForName");
	}
	
	public static void main(String[] args)
	{
		try {
			Class.forName("testForName2");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(" no thing.");
	}

}
