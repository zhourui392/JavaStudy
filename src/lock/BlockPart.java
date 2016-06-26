package lock;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class BlockPart {
	private static AtomicInteger totalCount = new AtomicInteger(0);
	private static ReentrantLock lock = new ReentrantLock();
	
	public static void blockPartMethod(String threadName){
		
		Date begin = null;
		Date end = null;
		try{
			totalCount.incrementAndGet();
			lock.lock();
			begin = new Date();
			
			//do somethind
//			try {
//				int sleeptime = new Random().nextInt(1000);
//				System.out.println("  --thread time is " + sleeptime);
//				Thread.sleep(sleeptime);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
			
		}finally{
			totalCount.decrementAndGet();
			if (totalCount.get() > 0){
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			end = new Date();
			System.out.println(threadName+" block Part last "+ (end.getTime()-begin.getTime()));
			lock.unlock();
		}
	}
}
