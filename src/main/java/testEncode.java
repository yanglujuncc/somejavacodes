import java.io.FileInputStream;
import java.io.ObjectInputStream;


public class testEncode {
	public static void main(String[] args) throws Exception{
		FileInputStream fis = new FileInputStream("foo.ser");
		ObjectInputStream ois = new ObjectInputStream(fis);
		
		
		System.out.println("readObject from file");
		SimpleClass mySimpleClass_=(SimpleClass)ois.readObject();
		
		ois.close();
		
		System.out.println(mySimpleClass_);
	}
}
