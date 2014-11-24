package testThread;

import java.nio.channels.SelectionKey;

public class testThread extends Thread{

	@Override
	public void run() {
		long time_start=System.currentTimeMillis();
		
		int j=0;
		for(int i=0;i<1000000;i++){
			j+=i+3;
		}
		System.out.println(j);
		long time_end=System.currentTimeMillis();
		long time_cost=time_end-time_start;
		System.out.println("run_time_cost:"+time_cost);
	
	}
	public static void main(String[] args) throws InterruptedException
	{
		
		long time_start=System.currentTimeMillis();
		
		testThread atestThread=new testThread();
		atestThread.start();
		atestThread.join();
		
		long time_end=System.currentTimeMillis();
		long time_cost=time_end-time_start;
		System.out.println("thread_time_cost:"+time_cost);
	}
}
