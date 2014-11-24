package util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class KryoUtil {
	
	static Kryo kryo = new Kryo();
	
	public static byte[] getBytes(Object obj) throws Exception {
	
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Output output = new Output(baos);	   
	    kryo.writeObject(output, obj);
	    output.close();
	    baos.close();
	   byte[] bytes= baos.toByteArray();
	   
	   return bytes;
	}

	public static <T>  T rebuildFromBytes(byte[] objectBytes,Class<T> classObj ) throws Exception {
		
		ByteArrayInputStream bais = new ByteArrayInputStream(objectBytes);
		Input input = new Input(bais);
		T someObject = kryo.readObject(input, classObj);
		input.close();
		
		    
		return someObject;
	}
}
