
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


public class SimpleClass  implements Serializable{
	
	public int intValue;
	public String strValue;
	
	public String toString(){
		StringBuilder aStringBuilder=new StringBuilder();
		
		aStringBuilder.append("[intValue="+intValue);
		aStringBuilder.append("	strValue="+strValue+"]");
		
		
		
		return aStringBuilder.toString();
	}
}
