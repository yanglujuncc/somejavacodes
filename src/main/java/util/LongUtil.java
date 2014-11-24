package util;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class LongUtil {
	public static byte[] longToBytes(long x) {
	    ByteBuffer buffer = ByteBuffer.allocate(8);
	    buffer.putLong(x);
	    return buffer.array();
	}

	public static long bytesToLong(byte[] bytes) {
	    ByteBuffer buffer = ByteBuffer.allocate(8);
	    buffer.put(bytes);
	    buffer.flip();//need flip 
	    return buffer.getLong();
	}
	
	public static void main(String[] args){
		
		long value=1L;
		
		System.out.println(Arrays.toString(longToBytes(1L)));
	}
}
