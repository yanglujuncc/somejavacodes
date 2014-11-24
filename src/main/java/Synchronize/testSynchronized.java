package Synchronize;
public class testSynchronized  {

	public  aClass aaaClass=new aClass();
	int i=0;
	
	public class aClass {
		synchronized void aSynchronizedFunction1(){
			System.out.println(Thread.currentThread().getName()+":got lock");
			try {
				System.out.println(Thread.currentThread().getName()+": sleep 1000 in function 1");
				Thread.sleep(1000);
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName()+":release lock");
		}
		synchronized void aSynchronizedFunction2() throws InterruptedException{
			i++;
			System.out.println(Thread.currentThread().getName()+":got lock");
			//System.out.println(Thread.currentThread().getName()+": sleep 2000 in function 2");
			if(i==2)
			{
				System.out.println(Thread.currentThread().getName()+":call wait..");
				synchronized(	Thread.currentThread()){
					
						Thread.currentThread().wait();
					
				}
			}
			try {
				System.out.println(Thread.currentThread().getName()+": sleep 2000 in function 2");
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName()+":release lock");
			
		}
		
		 void aSynchronizedFunction3(){
			 
			 synchronized(this){
				 System.out.println(Thread.currentThread().getName()+":got lock");
			//System.out.println(Thread.currentThread().getName()+": sleep 2000 in function 2");
			
			try {
				System.out.println(Thread.currentThread().getName()+": sleep 3000 in function 3");
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName()+":release lock");
			 }
		}
	}
	
	
	
	
	
	public class myrunner1 implements Runnable {
		int num = 0;
		String astr = new String ("0");
		String sstr2="";
		public void run() {
			while(true){
				System.out.println(Thread.currentThread().getName()+":waiting lock");
				aaaClass.aSynchronizedFunction1();
				
			}
		}
		
		
	}
	public class myrunner2 implements Runnable {
		int num = 0;
		String astr = new String ("0");
		String sstr2="";
		public void run() {
			while(true){
				System.out.println(Thread.currentThread().getName()+":waiting lock");
				try {
					aaaClass.aSynchronizedFunction2();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			}
		}
		
		
	}
	

	
	public void startTest(){
		myrunner1 amyrunner1=new myrunner1();
		myrunner2 amyrunner2=new myrunner2();
		Thread ta = new Thread(amyrunner1, "A");
		Thread tb = new Thread(amyrunner2, "B");
		ta.start();
		tb.start();
	}

	public static void main(String[] args) {
		testSynchronized arunner = new testSynchronized();

		arunner.startTest();
	}

}
