import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


public class testIterator {

	public static void main(String[] args){
		
		List<String> strList=new LinkedList<String>();
		
		strList.add("a");
		strList.add("b");
		strList.add("c");
		
		Iterator it=strList.iterator();
		
	//	System.out.println((String)it);
		while(true)
		{
			System.out.println((String)it.next());
		}
		
	}
}
