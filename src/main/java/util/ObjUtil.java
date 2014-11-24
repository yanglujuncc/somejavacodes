package util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class ObjUtil {
	
	
	public static byte[] getBytes(Object obj) throws Exception {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oout = new ObjectOutputStream(baos);
		oout.writeObject(obj); // 保存单例对象
		oout.close();
		byte[] bytes = baos.toByteArray();

		return bytes;
	}

	public static Object rebuildFromBytes(byte[] objectBytes) throws Exception {

		ByteArrayInputStream bi = new ByteArrayInputStream(objectBytes);
		ObjectInputStream oi = new ObjectInputStream(bi);
		Object obj = oi.readObject();
		bi.close();
		oi.close();

		return obj;
	}

}
