package Synchronize;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


public class testSynchronizedContainer {
	
	public List<String> list =  Collections.synchronizedList( new LinkedList<String>() );
	
	public aThread a=new aThread();
	public bThread b=new bThread();
	
	public void  addAstring(){
		
		synchronized(list){
			list.add("2 add by addAstring");
		}
	}
	public class aThread extends Thread{
		
		public void run(){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			synchronized(list){
				System.out.println("got lock aThread" );
				try {
					System.out.println("sleep 4's aThread" );
					Thread.sleep(4000);
					System.out.println("waken aThread" );
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				list.add("1 add by aThread");
				System.out.println("add ok aThread");
				System.out.println("return  lock aThread");
			}
		}
	}
	public class bThread extends Thread{
		
		public void run(){
			try {
				
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println("wating to lock  bThread");
			list.add("1 add by bThread");
			System.out.println("add ok bThread");
		}
	}
	public void runtest(){
		a.start();
		b.start();
		
	}
	public static void main(String[] args)
	{
	
		testSynchronizedContainer atestSynchronizedContainer=new testSynchronizedContainer();
		
		atestSynchronizedContainer.runtest();
		
	}

}
