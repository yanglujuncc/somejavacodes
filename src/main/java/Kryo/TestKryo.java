package Kryo;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class TestKryo {

	public static class Doc{
		
		String title;
		String athor;
		String content;
		long id;
		
		public Doc(){
			
		}
		public Doc(String title,String athor,String content,long id){
			this.title=title;
			this.athor=athor;
			this.content=content;
			this.id=id;
		
		}
		
		@Override
		public String toString(){
			return "title:"+title+" "+"athor:"+athor+" "+"content:"+content+" "+"id:"+id;
		}
		
	}
	public static class Person{
		
		Doc doc;
		
		String name;
		int old;
		
		Map<String,Integer> titleMap;
		List<String> titleList;
		
		String[] titleArray;
		public Person(){
			
		}
		public Person(String name,int old,Doc doc){
			this.name=name;
			this.old=old;
			this.doc=doc;
			/*
			this.titleMap=new TreeMap<String,Integer>();
			this.titleMap.put("ttt", 23);
			this.titleMap.put("ttt2", 5556);
			this.titleMap.put("ttt3", 88888);
			
			*/
			/*
			this.titleList=new LinkedList<String>();
			titleList.add("avb");
			titleList.add("abc");
			titleList.add("cde");
			titleList.add("efg");
			*/
			
			this.titleArray=new String[4];
			this.titleArray[0]="avb";
			this.titleArray[1]="abc";
			this.titleArray[2]="cde";
			this.titleArray[3]="efg";
			
			
		}
		@Override
		public String toString(){
			
			StringBuilder aStringBuilder=new StringBuilder();
			aStringBuilder.append("name:"+name+" old:"+old);
			if(titleMap!=null)
				aStringBuilder.append(" titleMap:"+titleMap.size());
			if(titleList!=null){
				aStringBuilder.append(" titleList:"+titleList.size());
			}
				aStringBuilder.append(" doc:"+doc);
			return aStringBuilder.toString();
		}
	}
	public static void testKryoReg() throws Exception {

		
		Kryo kryo = new Kryo();
		
		kryo.register(String.class);
		kryo.register(Map.class);
		kryo.register(List.class);
		kryo.register(Doc.class);
		
		Doc doc=new Doc("v1","v2","v3",12345);
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Output output = new Output(baos);
		Person hello = new Person("123456789",230,doc);
		kryo.writeObject(output, hello);
		output.close();
		baos.close();

		byte[] bytes = baos.toByteArray();
		System.out.println(bytes.length);
		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		Input input = new Input(bais);
		Person rebuildPerson = kryo.readObject(input, Person.class);
		input.close();
		System.out.println(rebuildPerson);
		
		
	}

	public static void testKryo() throws IOException {
		
		Doc doc=new Doc("v1","v2","v3",12345);
		
		Kryo kryo = new Kryo();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Output output = new Output(baos);
		Person hello = new Person("123456789",230,doc);
		kryo.writeObject(output, hello);
		output.close();
		baos.close();

		byte[] bytes = baos.toByteArray();
		System.out.println(bytes.length);
		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		Input input = new Input(bais);
		Person rebuildPerson = kryo.readObject(input, Person.class);
		input.close();
		System.out.println(rebuildPerson);
	}

	public static void main(String[] args) throws Exception {
		
		 System.out.println("-------------------------------------------");
		 testKryo();
		 System.out.println("---------------register--------------------");
		 testKryoReg();
	}
}
