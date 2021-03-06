package concurrent;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;

public class TestAQS {

	private static class Sync extends AbstractQueuedSynchronizer {
		// Report whether in locked state
		protected boolean isHeldExclusively() {
			return getState() == 1;
		}

		// Acquire the lock if state is zero
		public boolean tryAcquire(int acquires) {
			assert acquires == 1; // Otherwise unused
			if (compareAndSetState(0, 1)) {
				setExclusiveOwnerThread(Thread.currentThread());
				return true;
			}
			return false;
		}

		// Release the lock by setting state to zero
		protected boolean tryRelease(int releases) {
			assert releases == 1; // Otherwise unused
			if (getState() == 0)
				throw new IllegalMonitorStateException();
			setExclusiveOwnerThread(null);
			setState(0);
			return true;
		}

		// Provide a Condition
		Condition newCondition() {
			return new ConditionObject();
		}

		// Deserialize properly
		private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
			s.defaultReadObject();
			setState(0); // reset to unlocked state
		}
	}

	private final Sync sync = new Sync();

	public void lock() {
		sync.acquire(1);
	}

	public void tryLock(long maxWaitNS) throws InterruptedException {
		sync.tryAcquireNanos(1, maxWaitNS);
	}

	public boolean tryLock() {
		return sync.tryAcquire(1);
	}

	public void unlock() {
		sync.release(1);
	}

	public Condition newCondition() {
		return sync.newCondition();
	}

	public boolean isLocked() {
		return sync.isHeldExclusively();
	}

	public boolean hasQueuedThreads() {
		return sync.hasQueuedThreads();
	}

	public void lockInterruptibly() throws InterruptedException {
		sync.acquireInterruptibly(1);
	}

	public boolean tryLock(long timeout, TimeUnit unit) throws InterruptedException {
		return sync.tryAcquireNanos(1, unit.toNanos(timeout));
	}

	public static void main(String[] args) throws InterruptedException {

		final TestAQS aTestAQS = new TestAQS();

		Thread thread = new Thread(new Runnable() {

			public void run() {
				aTestAQS.lock();
				
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				aTestAQS.unlock();
			}

		}

		);
		thread.start();
		Thread.sleep(3000);

		
		aTestAQS.tryLock(1000000000);
		System.out.println("got lock");
	}
}
