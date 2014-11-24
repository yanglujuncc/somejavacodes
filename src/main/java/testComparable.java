import java.util.Arrays;


public class testComparable {
	public static void main(String[] args)
	{
		aComparableElement element_1=new aComparableElement(1);
		aComparableElement element_2=new aComparableElement(2);
		aComparableElement element_3=new aComparableElement(3);
		
		aComparableElement element_4=new aComparableElement(4);
		aComparableElement element_5=new aComparableElement(5);
		aComparableElement element_6=new aComparableElement(6);
		
		
		aComparableElement[] arary=new aComparableElement[6];
		arary[0]=element_6;
		arary[1]=element_5;
		arary[2]=element_4;
		
		arary[3]=element_3;
		arary[4]=element_2;
		arary[5]=element_1;
		
		System.out.println(Arrays.toString(arary));
	     Arrays.sort (arary);
	    System.out.println(Arrays.toString(arary));
	    
	   aComparableElement element_7=new aComparableElement(5);
	   int i= Arrays.binarySearch(arary, element_7);
	   System.out.println(i);
	}
	
}
