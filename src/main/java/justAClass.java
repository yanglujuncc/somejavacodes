import java.io.Serializable;


public class justAClass implements Serializable{
	int I;
	String Str;
	long L;
	public justAClass(String str,long l,int i)
	{
		Str=str;
		L=l;
		I=i;
	}
	public justAClass(String str)
	{
		Str=str;
	}
	public void print(){
		
		System.out.println(I);
		System.out.println(Str);
	
		System.out.println(L);
		
	}
}
