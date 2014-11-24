import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


public class testReadWriteLock {
	
	
	private final Map<Long,String> map=new HashMap<Long,String>();
	private final ReadWriteLock RWLock= new ReentrantReadWriteLock ();
	private final  Lock wlock=RWLock.writeLock();
	private final  Lock rlock=RWLock.readLock();

	
}
