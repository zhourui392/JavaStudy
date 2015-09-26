package concurrent.utilclass;

import java.util.Random;

public class JoinCountDownLatchTest {
	public static void main(String[] args) throws InterruptedException {
		Thread parser1 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					int sleepInt = new Random().nextInt(2000);
					System.out.println("parser1 int : " + sleepInt);
					Thread.sleep(sleepInt);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("parser1 finish");
			}
		});
		
		Thread parser2 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					int sleepInt = new Random().nextInt(2000);
					System.out.println("parser2 int : " + sleepInt);
					Thread.sleep(sleepInt);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("parser2 finish");
			}
		});
		
		parser1.start();
		parser2.start();
		
		parser1.join();
		parser2.join();
		
		System.out.println("all parser finish");
	}
}
