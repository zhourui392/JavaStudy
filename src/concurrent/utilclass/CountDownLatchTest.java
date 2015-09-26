package concurrent.utilclass;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class CountDownLatchTest {
	static CountDownLatch c = new CountDownLatch(2);
	
	public static void main(String[] args) throws InterruptedException {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					int sleepInt = new Random().nextInt(2000);
					Thread.sleep(sleepInt);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("1");
				c.countDown();
			}
		}).start();
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					int sleepInt = new Random().nextInt(2000);
					Thread.sleep(sleepInt);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("2");
				c.countDown();
			}
		}).start();
		
		c.await();
		System.out.println("3");
	}
}
