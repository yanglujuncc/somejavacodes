package util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.apache.hadoop.io.Writable;

public class WritableSerialize {

	public static byte[] serialize(Writable writable) throws IOException{
		
		ByteArrayOutputStream out=new ByteArrayOutputStream();
		DataOutputStream dataout=new DataOutputStream(out);
			
		writable.write(dataout);
		dataout.close();
		
		return out.toByteArray();
	}
	
	public static void rebuild(Writable writable,byte[] data) throws IOException{
		
		ByteArrayInputStream in=new ByteArrayInputStream(data);
		DataInputStream datain=new DataInputStream(in);
		
		writable.readFields(datain);
		datain.close();
		
	}
}
