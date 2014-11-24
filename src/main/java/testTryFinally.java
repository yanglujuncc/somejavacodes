import java.io.IOException;



public class testTryFinally {
	
	
	public static void throwException() throws IOException{
		throw new IOException();
	}
	public static void main(String[] args)
	{
		
		
			
			
			try {
				System.out.println("step 1");
				int i=1;
				int j=i/0;
				throwException();
				System.out.println("step 2");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("step 3");
				e.printStackTrace();
			}finally{
				System.out.println("step 4");
			System.out.println("in finally");
		}
		System.out.println("step 2");
		System.exit(1);
	}
}
