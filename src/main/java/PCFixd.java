public class PCFixd {
	class Q {
		int n;
		boolean valueSet = false;

		synchronized void gett() {

			int i = 0;

			// step one
			try {
				Thread.sleep(2000);
				i++;
				System.out.println(Thread.currentThread().getName()
						+ "-> gett()... step=" + i);
				System.out.flush();

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// step two
			try {
				Thread.sleep(2000);
				i++;
				System.out.println(Thread.currentThread().getName()
						+ "-> gett()... step=" + i);
				System.out.flush();

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

			// step three
			try {
				Thread.sleep(2000);
				i++;

				System.out.println(Thread.currentThread().getName()
						+ "-> gett()... step=" + i);
				System.out.flush();

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				System.out.println(Thread.currentThread().getName()
						+ "-> wait(5000) ");

				System.out.flush();
				this.wait(5000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// step four
			try {
				Thread.sleep(2000);
				i++;
				System.out.println(Thread.currentThread().getName()
						+ "-> gett()... step=" + i);
				System.out.flush();

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// step five
			try {
				Thread.sleep(2000);
				i++;
				System.out.println(Thread.currentThread().getName()
						+ "-> gett()... step=" + i);
				System.out.flush();

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		synchronized void putt() {

			int i = 0;

			// step one
			try {
				Thread.sleep(2000);
				i++;
				System.out.println(Thread.currentThread().getName()
						+ "-> putt()... step=" + i);
				System.out.flush();

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// step two
			try {
				Thread.sleep(2000);
				i++;
				System.out.println(Thread.currentThread().getName()
						+ "-> putt()... step=" + i);
				System.out.flush();

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			System.out.println(Thread.currentThread().getName()
					+ "-> notify() ");

			System.out.flush();
			this.notify();

			// step three
			try {
				Thread.sleep(2000);
				i++;
				System.out.println(Thread.currentThread().getName()
						+ "-> putt()... step=" + i);
				System.out.flush();

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// step four
			try {
				Thread.sleep(2000);
				i++;
				System.out.println(Thread.currentThread().getName()
						+ "-> putt()... step=" + i);
				System.out.flush();

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// step five
			try {
				Thread.sleep(2000);
				i++;
				System.out.println(Thread.currentThread().getName()
						+ "-> putt()... step=" + i);
				System.out.flush();

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		synchronized int get() {
			System.out.println("in get() valueSet= " + valueSet);
			System.out.flush();
			if (!valueSet)
				try {
					System.out.println("get wait()... ");
					System.out.flush();
					wait();
					System.out.println("get be notify()ed ... ");
					System.out.flush();
				} catch (InterruptedException e) {
					System.out.println("InterruptedException caught");
					System.out.flush();
				}
			System.out.println("synchronized Got: " + n);
			System.out.flush();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			valueSet = false;
			notify();
			System.out.println("get notify()... ");
			System.out.flush();
			return n;
		}

		synchronized void put(int n) {
			System.out.println("in put() valueSet= " + valueSet);
			System.out.flush();
			if (valueSet)
				try {
					System.out.println("put wait()... ");
					System.out.flush();
					wait();
					System.out.println("put be notify()ed ... ");
					System.out.flush();
				} catch (InterruptedException e) {
					System.out.println("InterruptedException caught");
					System.out.flush();
				}
			this.n = n;
			valueSet = true;
			System.out.println("synchronized Put: " + n);
			System.out.flush();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			notify();
			System.out.println("put notify()... ");
			System.out.flush();
		}
	}

	class runner1 implements Runnable {
		Q q;

		runner1(Q q) {
			this.q = q;
			new Thread(this, "runner1").start();
		}

		public void run() {
			System.out.println("runner1 started");
			System.out.flush();
			q.gett();
		}
	}

	class runner2 implements Runnable {
		Q q;

		runner2(Q q) {
			this.q = q;
			new Thread(this, "runner2").start();
		}

		public void run() {
			System.out.println("runner2 started");
			System.out.flush();
			q.putt();
		}
	}

	class Producer implements Runnable {
		Q q;

		Producer(Q q) {
			this.q = q;
			new Thread(this, "Producer").start();
		}

		public void run() {
			int i = 0;
			while (true) {
				q.put(i++);
			}
		}
	}

	class Consumer implements Runnable {
		Q q;

		Consumer(Q q) {
			this.q = q;
			new Thread(this, "Consumer").start();
		}

		public void run() {
			while (true) {
				q.get();
			}
		}
	}

	public static void main(String args[]) {
		Q q = new PCFixd().new Q();
		new PCFixd().new runner1(q);
		new PCFixd().new runner2(q);

		// new PCFixd().new Consumer(q);
		// new PCFixd().new Producer(q);

		System.out.println("Press Control-C to stop.");
	}

}
