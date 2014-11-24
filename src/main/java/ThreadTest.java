import java.util.LinkedList;
import java.util.Queue;

public class ThreadTest {

	public Queue<Long> aQueue = new LinkedList<Long>();

	aThread a1;
	aThread a2;
	bThread b;

	public ThreadTest() {
		a1 = new aThread();
		a1.setName("consumer 1");
		a2 = new aThread();
		a2.setName("consumer 2");
		b = new bThread();
		b.setName("producter 1");
	}

	public void start() {
		a1.start();
		a2.start();
		b.start();

	}

	public class aThread extends Thread {

		@Override
		public void run() {

			while (true) {
				long Qelement = 0;

				while (true) {

					synchronized (aQueue) {	
						if (aQueue.size() > 0)
							Qelement = aQueue.poll();
						else
							Qelement = 0;
						if (Qelement == 0) {	
							
							try {
								System.out.println(aThread.currentThread().getName()
										+ ":Do wait()");
								aQueue.wait();	
								System.out.println(aThread.currentThread().getName()
										+ ":i am weaken()");
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							
						}
						else
							break;
					}	
				}
				try {
					sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(aThread.currentThread().getName()
						+ ":Getfrom Queue:" + Qelement);
			}
		}
	}

	public class bThread extends Thread {

		@Override
		public void run() {

			long i = 0;
			while (true) {

				try {
					sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				synchronized (aQueue) {
					aQueue.add(++i);
					aQueue.notifyAll();
				}
				
				System.out.println(aThread.currentThread().getName()
						+ ":PutInto Queue:" + i);

			}
		}
	}

	public static void main(String[] args) {

		ThreadTest a = new ThreadTest();
		a.start();

	}

}
