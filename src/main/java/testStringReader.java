import java.io.IOException;
import java.io.StringReader;

public class testStringReader {
	public static void main(String[] args) {
		try {
			StringReader sb = new StringReader("hello");
			char[] buffer = new char[100];

			int i = sb.read(buffer);
			System.out.println("i = "+i);
			
			i = sb.read(buffer);
			System.out.println("i = "+i);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
