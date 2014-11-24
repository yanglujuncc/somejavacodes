package Kryo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class TestKryoCollection {

	public static void testKryoStr() throws Exception {

		Kryo kryo = new Kryo();

		String var="123456";
	//	strList.add("3457");
	//	strList.add("4567");
	//	strList.add("5677");
	
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Output output = new Output(baos);
		kryo.writeObject(output, var);
		output.close();
		baos.close();
		byte[] bytes = baos.toByteArray();
		System.out.println(bytes.length);

		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		Input input = new Input(bais);
		String rebuildStrt = kryo.readObject(input, String.class);
		input.close();
		System.out.println(rebuildStrt);

	}
	public static void testKryoListStr() throws Exception {

		Kryo kryo = new Kryo();

		List<String> strList = new LinkedList<String>();
		
		strList.add("1237");
		strList.add("2347");
	//	strList.add("3457");
	//	strList.add("4567");
	//	strList.add("5677");
	
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Output output = new Output(baos);
		kryo.writeObject(output, strList);
		output.close();
		baos.close();
		byte[] bytes = baos.toByteArray();
		System.out.println(bytes.length);

		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		Input input = new Input(bais);
		LinkedList<String> rebuildStrList = kryo.readObject(input, LinkedList.class);
		input.close();
		System.out.println(rebuildStrList);

	}

	public static void testKryoListInteger() throws Exception {

		Kryo kryo = new Kryo();

		List<Integer> strList = new LinkedList<Integer>();

		strList.add(123);
		strList.add(234);
		strList.add(345);
		strList.add(456);
		strList.add(567);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Output output = new Output(baos);
		kryo.writeObject(output, strList);
		output.close();
		baos.close();
		byte[] bytes = baos.toByteArray();
		System.out.println(bytes.length);

		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		Input input = new Input(bais);
		LinkedList rebuildStrList = kryo.readObject(input, LinkedList.class);
		input.close();
		System.out.println(rebuildStrList);

	}

	public static void testKryoArrayStr() throws Exception {

		Kryo kryo = new Kryo();

		String[] strArray = new String[5];

		strArray[0] = "123";
		strArray[1] = "234";
		strArray[2] = "345";
		strArray[3] = "456";
		strArray[4] = "567";

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Output output = new Output(baos);
		kryo.writeObject(output, strArray);
		output.close();
		baos.close();
		byte[] bytes = baos.toByteArray();
		System.out.println(bytes.length);

		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		Input input = new Input(bais);
		String[] rebuildStrArray = kryo.readObject(input, String[].class);
		input.close();
		System.out.println(Arrays.toString(rebuildStrArray));

	}

	public static void testKryoArrayInteger() throws Exception {

		Kryo kryo = new Kryo();

		Integer[] intArray = new Integer[5];

		intArray[0] = 123;
		intArray[1] = 234;
		intArray[2] = 345;
		intArray[3] = 456;
		intArray[4] = 567;

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Output output = new Output(baos);
		kryo.writeObject(output, intArray);
		output.close();
		baos.close();
		byte[] bytes = baos.toByteArray();
		System.out.println(bytes.length);

		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		Input input = new Input(bais);
		Integer[] rebuildStrArray = kryo.readObject(input, Integer[].class);
		input.close();
		System.out.println(Arrays.toString(rebuildStrArray));

	}

	public static void testKryoArrayInt() throws Exception {

		Kryo kryo = new Kryo();

		int[] intArray = new int[5];

		intArray[0] = 123;
		intArray[1] = 234;
		intArray[2] = 345;
		intArray[3] = 456;
		intArray[4] = 567;

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Output output = new Output(baos);
		kryo.writeObject(output, intArray);
		output.close();
		baos.close();
		byte[] bytes = baos.toByteArray();
		System.out.println(bytes.length);

		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		Input input = new Input(bais);
		int[] rebuildStrArray = kryo.readObject(input, int[].class);
		input.close();
		System.out.println(Arrays.toString(rebuildStrArray));

	}
	public static void testKryoHashMap() throws Exception {
		Kryo kryo = new Kryo();

		HashMap<String,Integer> strMap = new HashMap<String,Integer>();
		
		strMap.put("123", 123);
		strMap.put("234", 234);
		strMap.put("345", 345);
		strMap.put("456", 456);
		strMap.put("567", 567);
		strMap.put("678", 678);
		strMap.put("789", 789);
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Output output = new Output(baos);
		kryo.writeObject(output, strMap);
		output.close();
		baos.close();
		byte[] bytes = baos.toByteArray();
		System.out.println(bytes.length);

		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		Input input = new Input(bais);
		HashMap<String,Integer> rebuildStrMap = kryo.readObject(input, HashMap.class);
		input.close();
		System.out.println(rebuildStrMap.entrySet());
		
	}
	public static void testKryoTreeMap() throws Exception {
		Kryo kryo = new Kryo();

		TreeMap<String,Integer> strMap = new TreeMap<String,Integer>();
	
		strMap.put("123", 123);
		strMap.put("234", 234);
		strMap.put("345", 345);
		strMap.put("456", 456);
		strMap.put("567", 567);
		strMap.put("678", 678);
		strMap.put("789", 789);
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Output output = new Output(baos);
		kryo.writeObject(output, strMap);
		output.close();
		baos.close();
		byte[] bytes = baos.toByteArray();
		System.out.println(bytes.length);

		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		Input input = new Input(bais);
		TreeMap<String,Integer> rebuildStrMap = kryo.readObject(input, TreeMap.class);
		input.close();
		System.out.println(rebuildStrMap.entrySet());
		
	}
	public static void main(String[] args) throws Exception {
		
		System.out.println("---------------- Str---------------------");
		testKryoStr();
		System.out.println("----------------List Str---------------------");
		testKryoListStr();
		System.out.println("----------------List Integer---------------------");
		testKryoListInteger();

		System.out.println("---------------Array str--------------------");
		testKryoArrayStr();
		System.out.println("---------------Array int--------------------");
		testKryoArrayInt();
		System.out.println("---------------Array Integer--------------------");
		testKryoArrayInteger();
		
		System.out.println("---------------HashMap Integer--------------------");
		testKryoHashMap();
		System.out.println("---------------TreeMap Integer--------------------");
		testKryoTreeMap();
	}
}
