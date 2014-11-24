import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.nio.CharBuffer;


public class testPipedReaderWriter {
	public static void main(String[]args)
	{
		  PipedReader reader = new PipedReader();
          PipedWriter writer = new PipedWriter();
          char[] charbuffer=new char[100];
          try {
			writer.connect(reader);
			
			writer.write("hello");
			int i=reader.read(charbuffer);
			System.out.println("i="+i);
			System.out.println(charbuffer);
			if(reader.ready()){
				i=reader.read(charbuffer);
				System.out.println("i="+i);
			}
			else
			{
				System.out.println("reader.ready()= false");
			}
				
			System.out.flush();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
