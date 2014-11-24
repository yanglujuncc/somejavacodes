package util;

import java.util.Map;
import java.util.Map.Entry;

public class EntropyUtil {

	//public static double Log2=Math.log(2);
	public static  double entropy(double[] pArray){
		
		double entropy=0.0;
		// b 是对数所使用的底，通常是 2, 自然常数 e，或是10。当b = 2，熵的单位是bit；当b = e，熵的单位是 nat；而当 b = 10,熵的单位是 dit。
		for(double p:pArray){			
			entropy+=(-p*Math.log(p));
		}
		
		return entropy;
		
	}

	public static double computeEntropyFromDouble(double[] values){
		double[] pArray=new double[values.length] ;
		double total=0;
		for(int i=0;i<values.length;i++){
			total+=values[i];
		}
		for(int i=0;i<values.length;i++){
			pArray[i]=values[i]/total;
		}
		
		return entropy( pArray);
	}
	public static double computeEntropyFromLong(long[] values){
		double[] doubleValues=new double[values.length] ;		
		for(int i=0;i<values.length;i++){
			doubleValues[i]=values[i];
		}		
		return computeEntropyFromDouble( doubleValues);
	}
	
	public static double computeEntropyFromPercentMap(Map<String, Double> percentMap){
		double[] doubleValues=new double[percentMap.size()];
		int i=0;
		for(Entry<String, Double> entry:percentMap.entrySet()){
			doubleValues[i++]=entry.getValue();
		}
		return entropy( doubleValues);
	}
	
	public static double computeEntropyFromInt(int[] values){
		double[] doubleValues=new double[values.length] ;
		
		for(int i=0;i<values.length;i++){
			doubleValues[i]=values[i];
		}		
		return computeEntropyFromDouble( doubleValues);
	}
	
	
	
	public static void main(String[] args){
		double[] pArray1={1};
		double[] pArray2={0.2,0.2,0.2,0.2,0.2};
		double[] pArray2_1={0.1,0.5,0.1,0.1,0.2};
		double[] pArray3={0.1,0.1,0.1,0.1,0.1,0.1,0.1,0.1,0.1,0.1};
		int[] intArray={1,1};
		double entropyValue=entropy( pArray3);
		System.out.println(entropy( pArray1));
		System.out.println(entropy( pArray2));
		System.out.println(entropy( pArray2_1));
		System.out.println(entropy( pArray3));
		System.out.println(computeEntropyFromInt( intArray));
	}
}
