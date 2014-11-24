
public class aRunner implements Runnable {

	String str="";
	@Override
	public void run() {
		//TODO Auto-generated method stub
		
			for (int i = 0; i < 5; i++) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("MyName="+Thread.currentThread().getName());
					//synchronized (this) {
					try {
						this.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				
				
				this.notify();
				//}
				
			}

	}
	
	public static void  main(String[] argvs)
	{
		aRunner ar=new aRunner();
		Thread ta = new Thread(ar, "A");
		ta.start();
		
		
	}
}
