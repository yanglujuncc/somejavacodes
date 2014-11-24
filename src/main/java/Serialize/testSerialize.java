package Serialize;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class testSerialize implements Serializable{

	public class Box implements Serializable {
		
		private int width;
		private int height;
		private String str="ºº×Ö";
		public void setWidth(int width) {
			this.width = width;
		}

		public void setHeight(int height) {
			this.height = height;
		}
		public String toString(){
			StringBuilder aStringBuilder=new StringBuilder();
			
			aStringBuilder.append("[width="+width);
			aStringBuilder.append("	height="+height);
			aStringBuilder.append("	strValue="+str+"]");
			return aStringBuilder.toString();
		}
	}

	public static void main(String[] args) throws Exception {
		testSerialize test = new testSerialize();

		testSerialize.Box myBox = test.new Box();

		myBox.setWidth(50);
		myBox.setHeight(30);

		FileOutputStream fs = new FileOutputStream("foo.ser");
		ObjectOutputStream os = new ObjectOutputStream(fs);
		System.out.println(myBox);
		os.writeObject(myBox);
		os.close();

		
		FileInputStream fis = new FileInputStream("foo.ser");
		ObjectInputStream ois = new ObjectInputStream(fis);
		
		System.out.println("readObject from file");
		Box myBox_=(Box)ois.readObject();
		System.out.println(myBox_);
		
		
	
	}
}
