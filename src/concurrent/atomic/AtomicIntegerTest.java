package concurrent.atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 原子操作
 */
public class AtomicIntegerTest {
	private static final AtomicInteger atomicInt = new AtomicInteger(1);
	
	public static void main(String[] args) throws InterruptedException {
		for (int i = 0 ; i < 20; i++){
			new Thread(new Runnable() {
				@Override
				public void run() {
					//+1
					System.out.println(AtomicIntegerTest.atomicInt.getAndAdd(1));;
				}
			}).start();
		}
		for (int i = 0 ; i < 20; i++){
			new Thread(new Runnable() {
				@Override
				public void run() {
					System.out.println(AtomicIntegerTest.atomicInt.getAndDecrement());;
				}
			}).start();
		}

		Thread.sleep(1000);
		System.out.println("last result:"+atomicInt.get());
	}
}
