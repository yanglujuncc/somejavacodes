
public class aComparableElement implements Comparable<aComparableElement>{

	int i;
	
	public aComparableElement(int j)
	{
		i=j;
	}
	@Override
	public int compareTo(aComparableElement obj) {
		// TODO Auto-generated method stub
		if(this.i>obj.i)
			return 1;
		else if(this.i<obj.i)
			return -1;
		
		return 0;
	}
	 public String toString() {
	       return (  "{" + i + "}" );
	    }
	
}
