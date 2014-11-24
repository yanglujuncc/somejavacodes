import java.nio.charset.Charset;


public class testEncoding {
	public static void main(String[] args)
	{
		System.out.println(System.getProperty("file.encoding"));
		System.out.println(Charset.defaultCharset());
	}

}
